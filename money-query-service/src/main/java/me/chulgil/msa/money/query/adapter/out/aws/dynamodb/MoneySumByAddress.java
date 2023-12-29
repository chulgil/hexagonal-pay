package me.chulgil.msa.money.query.adapter.out.aws.dynamodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MoneySumByAddress {

    private String PK;
    private String SK;
    private int balance;
}
