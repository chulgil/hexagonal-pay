package me.chulgil.msa.remittance.application.service;


import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.remittance.application.port.in.RemittanceMoneyCommand;
import me.chulgil.msa.remittance.application.port.in.RemittanceMoneyUseCase;
import me.chulgil.msa.remittance.application.port.out.RemittanceMoneyPort;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class RemittanceMoney implements RemittanceMoneyUseCase {

	private final RemittanceMoneyPort rport;

	@Override
	public void remittanceMoney(RemittanceMoneyCommand command) {

	}
}




