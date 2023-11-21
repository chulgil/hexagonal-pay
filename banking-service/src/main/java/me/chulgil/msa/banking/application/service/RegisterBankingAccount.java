package me.chulgil.msa.banking.application.service;

import me.chulgil.msa.banking.application.port.in.RegisterBankingAccountCommand;
import me.chulgil.msa.banking.application.port.in.RegisterBankingAccountUseCase;
import me.chulgil.msa.banking.application.port.out.RegisterBankingAccountPort;
import me.chulgil.msa.banking.domain.BankingAccountRegisterInfo;
import me.chulgil.msa.common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class RegisterBankingAccount implements RegisterBankingAccountUseCase {

	private final RegisterBankingAccountPort rport;

	@Override
	public void registerBankingAccount(RegisterBankingAccountCommand command) {
		rport.registerBankingAccount(new BankingAccountRegisterInfo.BankingAccountRegisterInfoId(command.getEmail()));
	}
}




