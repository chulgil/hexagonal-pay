package me.chulgil.msa.money.application.port.out;

import java.util.List;
import me.chulgil.msa.money.adapter.out.persistence.MemberMoneyJpaEntity;

public interface GetMemberMoneyListPort {

    List<MemberMoneyJpaEntity> getMemberMoney(List<String> membershipIds);
}
