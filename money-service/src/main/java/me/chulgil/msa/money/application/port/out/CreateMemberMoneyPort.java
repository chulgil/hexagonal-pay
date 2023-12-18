package me.chulgil.msa.money.application.port.out;

import me.chulgil.msa.money.domain.MemberMoney;

public interface CreateMemberMoneyPort {

    void createMemberMoney(
        MemberMoney.MembershipId memberId,
        MemberMoney.MoneyAggregateIdentifier aggregateIdentifier);
}
