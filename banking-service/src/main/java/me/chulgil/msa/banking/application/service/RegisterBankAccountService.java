package me.chulgil.msa.banking.application.service;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.adapter.out.external.bank.BankAccount;
import me.chulgil.msa.banking.adapter.out.external.bank.GetBankAccountRequest;
import me.chulgil.msa.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import me.chulgil.msa.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import me.chulgil.msa.banking.application.port.out.RequestBankAccountInfoPort;
import me.chulgil.msa.banking.application.port.in.RegisterBankAccountCommand;
import me.chulgil.msa.banking.application.port.in.RegisterBankAccountUseCase;
import me.chulgil.msa.banking.application.port.out.RegisterBankAccountPort;
import me.chulgil.msa.banking.domain.RegisteredBankAccount;
import me.chulgil.msa.common.UseCase;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

    private final RegisterBankAccountPort registerPort;
    private final RequestBankAccountInfoPort requestPort;
    private final RegisteredBankAccountMapper mapper;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        // 은행 계좌 등록
        // 1. 등록된 계좌인지 확인 : 외부 은행에 등록된 계좌인지 확인
        // Biz Logic -> External Service
        // Port -> Adapter -> External Service
        BankAccount accountInfo = requestPort.getBankAccountInfo(GetBankAccountRequest.builder()
            .bankName(command.getBankName())
            .bankAccountNumber(command.getBankAccountNumber())
            .build());

        if(accountInfo.isValid()) {
            // 2. 등록된 계좌라면 등록
            RegisteredBankAccountJpaEntity account = registerPort.createAccount(
                new RegisteredBankAccount.MembershipId(command.getMembershipId()),
                new RegisteredBankAccount.BankName(command.getBankName()),
                new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                new RegisteredBankAccount.LinkStatusIsValid(command.isValid())
            );
            return mapper.mapToDomainEntity(account);
        } else {
            // 3. 등록된 계좌가 아니라면 예외 처리
            throw new RuntimeException("등록된 계좌가 아닙니다.");
        }

    }
}
