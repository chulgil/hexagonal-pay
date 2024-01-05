package me.chulgil.msa.money.aggregation.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberMoney {

    private String memberMoneyId;
    private String membershipId;
    private int balance;
}
