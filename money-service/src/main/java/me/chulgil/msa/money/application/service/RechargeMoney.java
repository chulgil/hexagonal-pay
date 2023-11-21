package me.chulgil.msa.money.application.service;


import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.money.application.port.in.RechargeMoneyCommand;
import me.chulgil.msa.money.application.port.in.RechargeMoneyUseCase;
import me.chulgil.msa.money.application.port.out.RechargeMoneyPort;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class RechargeMoney implements RechargeMoneyUseCase {

	private final RechargeMoneyPort rport;

	@Override
	public void rechargeMoney(RechargeMoneyCommand command) {

	}
}




