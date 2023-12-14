package me.chulgil.msa.remittance.application.service;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.remittance.adapter.out.persistance.RemittanceRequestJpaEntity;
import me.chulgil.msa.remittance.adapter.out.persistance.RemittanceRequestMapper;
import me.chulgil.msa.remittance.application.port.in.RequestRemittanceCommand;
import me.chulgil.msa.remittance.application.port.in.RequestRemittanceUseCase;
import me.chulgil.msa.remittance.application.port.out.RequestRemittancePort;
import me.chulgil.msa.remittance.application.port.out.banking.BankingPort;
import me.chulgil.msa.remittance.application.port.out.membership.MembershipPort;
import me.chulgil.msa.remittance.application.port.out.membership.MembershipStatus;
import me.chulgil.msa.remittance.application.port.out.money.MoneyInfo;
import me.chulgil.msa.remittance.application.port.out.money.MoneyPort;
import me.chulgil.msa.remittance.domain.RemittanceRequest;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RequestRemittancePort remittancePort;
    private final RemittanceRequestMapper mapper;
    private final MembershipPort membershipPort;
    private final MoneyPort moneyPort;
    private final BankingPort bankingPort;


    @Override
    public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

        // 0. 송금 요청 상태를 시작 상태로 기록 (persistence layer)
        RemittanceRequestJpaEntity entity = remittancePort.createHistory(command);

        // 1. from 멤버십 상태 확인 (membership-service)
        MembershipStatus membershipStatus = membershipPort.getMembershipStatus(command.getFromMembershipId());
        if (!membershipStatus.isValid()) {
            return null;
        }

        // 2. from 멤버십의 잔액이 충분한지 확인 (money-service)
        MoneyInfo moneyInfo = moneyPort.getMoneyInfo(command.getFromMembershipId());

        // 2-1. 충분하지 않으면, 충전 요청 (money-service)
        if (moneyInfo.getBalance() < command.getAmount()) {

            // 만원 단위로 올림
            // 잔고 1000원 충전 5000원  :  5000 - 1000 = 4000 / 10000 = 0.4 올림 = 1 * 10000 = 10000
            // 잔고 1000원 충전 15000원 : 15000 - 1000 = 14000 / 10000 = 1.4 올림 = 2 * 10000 = 20000
            int rechargeAmount = (int) Math.ceil((command.getAmount() - moneyInfo.getBalance()) / 10000.0) * 10000;
            boolean moneyResult = moneyPort.requestMoneyRecharging(command.getFromMembershipId(), rechargeAmount);
            if (!moneyResult) {
                return null;
            }
        }

        // 3. 송금타입(고객/은행)
        if (command.getRemittanceType() == 0) {
            // 3-1. 내부고객인 경우 고객 머니 감액, 대상 머니 증액
            boolean moneyResult = moneyPort.requestMoneyDecrease(command.getFromMembershipId(), command.getAmount());
            if (!moneyResult) {
                return null;
            }
            moneyResult = moneyPort.requestMoneyIncrease(command.getToMembershipId(), command.getAmount());
            if (!moneyResult) {
                return null;
            }
        } else if (command.getRemittanceType() == 1) {
            // 3-2. 외부고객인 경우 외부 은행 계좌 확인 (banking-service)
            boolean bankingResult = bankingPort.requestFirmbanking(
                command.getToBankName(), command.getToBankAccountNumber(), command.getAmount());
            if (!bankingResult) {
                return null;
            }
            // 법인계좌에서 -> 외부 은행 계좌 펌뱅킹 요청 (remittance-service)
            boolean moneyResult = moneyPort.requestMoneyDecrease(command.getFromMembershipId(), command.getAmount());
            if (!moneyResult) {
                return null;
            }
        }


        // 4. 송금 요청상태를 성공으로 기록 (persistence layer)
        entity.setRemittanceStatus("success");
        boolean result = remittancePort.saveHistory(entity);
        if (!result) {
            return null;
        }
        return null;
    }
}
