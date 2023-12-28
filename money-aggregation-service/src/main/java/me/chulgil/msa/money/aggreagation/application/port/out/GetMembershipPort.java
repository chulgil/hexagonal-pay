package me.chulgil.msa.money.aggreagation.application.port.out;

import java.util.List;

public interface GetMembershipPort {

    List<String> getMembershipByAddress(String address);
}
