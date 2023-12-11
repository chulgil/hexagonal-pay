package me.chulgil.msa.money.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder @AllArgsConstructor
public class GetMoneyRequest {
    private String bankName;
    private String moneyNumber;
}
