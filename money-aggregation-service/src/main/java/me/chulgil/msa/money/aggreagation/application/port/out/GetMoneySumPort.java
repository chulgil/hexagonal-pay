package me.chulgil.msa.money.aggreagation.application.port.out;

import java.util.List;

public interface GetMoneySumPort {

    List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds);
}
