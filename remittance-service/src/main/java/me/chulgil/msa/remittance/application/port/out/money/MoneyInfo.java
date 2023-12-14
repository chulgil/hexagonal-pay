package me.chulgil.msa.remittance.application.port.out.money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MoneyInfo {
    private String membershipId;
    private int balance;
}
