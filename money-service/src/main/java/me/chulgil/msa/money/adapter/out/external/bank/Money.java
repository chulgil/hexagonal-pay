package me.chulgil.msa.money.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Money {
    private String bankName;
    private String moneyNumber;
    private boolean isValid;
}
