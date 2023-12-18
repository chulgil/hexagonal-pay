package me.chulgil.msa.money.application.port.in;

import me.chulgil.msa.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.chulgil.msa.money.domain.MemberMoney;

public interface GetMemberMoneyPort {

    MemberMoneyJpaEntity getMemberMoney(MemberMoney.MembershipId membershipId);
}
