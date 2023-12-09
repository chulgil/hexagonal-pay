package me.chulgil.msa.money.application.port.out;

import me.chulgil.msa.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.chulgil.msa.money.domain.MemberMoney;
import me.chulgil.msa.money.domain.MoneyChangingRequest;
import me.chulgil.msa.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;

public interface IncreaseMoneyPort {

    MoneyChangingRequestJpaEntity createMoneyChangingRequest(
        MoneyChangingRequest.TargetMembershipId targetMembershipId,
        MoneyChangingRequest.MoneyChangingType moneyChangingType,
        MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
        MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
        MoneyChangingRequest.Uuid uuid
    );

    MemberMoneyJpaEntity increaseMemberMoney(
        MemberMoney.MembershipId membershipId,
        int increaseMoneyAmount
    );
}
