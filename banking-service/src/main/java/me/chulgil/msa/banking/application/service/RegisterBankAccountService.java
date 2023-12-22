package me.chulgil.msa.banking.application.service;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import me.chulgil.msa.banking.adapter.out.external.bank.BankAccount;
import me.chulgil.msa.banking.adapter.out.external.bank.GetBankAccountRequest;
import me.chulgil.msa.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import me.chulgil.msa.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import me.chulgil.msa.banking.application.port.in.GetRegisteredBankAccountCommand;
import me.chulgil.msa.banking.application.port.in.GetRegisteredBankAccountUseCase;
import me.chulgil.msa.banking.application.port.in.RegisterBankAccountCommand;
import me.chulgil.msa.banking.application.port.in.RegisterBankAccountUseCase;
import me.chulgil.msa.banking.application.port.out.*;
import me.chulgil.msa.banking.domain.RegisteredBankAccount;
import me.chulgil.msa.common.UseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase, GetRegisteredBankAccountUseCase {

    private final GetMembershipPort getMembershipPort;
    private final RegisterBankAccountPort registerPort;
    private final RequestBankAccountInfoPort requestPort;
    private final GetRegisteredBankAccountPort getPort;
    private final RegisteredBankAccountMapper mapper;
    private final CommandGateway commandGateway;

    @Override public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {


        // 정상 멤버쉽인지 확인
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getMembershipId());

        // 은행 계좌 등록
        // 1. 등록된 계좌인지 확인 : 외부 은행에 등록된 계좌인지 확인
        BankAccount accountInfo = requestPort.getBankAccountInfo(GetBankAccountRequest.builder()
            .bankName(command.getBankName())
            .bankAccountNumber(command.getBankAccountNumber())
            .build());

        if (accountInfo.isValid()) {
            // 2. 등록된 계좌라면 등록
            RegisteredBankAccountJpaEntity account = registerPort.createRegisteredBankAccount(
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

    @Override public void registerBankAccountByEvent(RegisterBankAccountCommand command) {
        commandGateway.send(CreateRegisteredBankAccountCommand.builder()
                          .bankAccountNumber(command.getBankAccountNumber())
                          .bankName(command.getBankName())
                          .membershipId(command.getMembershipId())
                          .build())
                      .whenComplete((event, throwable) -> {
                          if (throwable != null) {
                              System.out.println("throwable = " + throwable);
                          } else {
                              // 정상적으로 이벤트 소싱
                              registerPort.createRegisteredBankAccount(
                                  new RegisteredBankAccount.MembershipId(command.getMembershipId()),
                                  new RegisteredBankAccount.BankName(command.getBankName()),
                                  new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                                  new RegisteredBankAccount.LinkStatusIsValid(command.isValid()));
                          }
                      });
    }

    @Override public RegisteredBankAccount getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {

        return mapper.mapToDomainEntity(getPort.getRegisteredBankAccount(command));

    }
}
