package me.chulgil.msa.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {

    private String moneyChangingRequestId;

    private MoneyChangingType moneyChangingType;

    private MoneyChangingResultStatus moneyChangingResultStatus;

    private int amount;
    
}

enum MoneyChangingType {
    INCREASE,
    DECREASE
}

enum MoneyChangingResultStatus {
    SUCCEEDED, // 요청됨
    FAILED, // 실패됨
    FAILED_NOT_ENOUGH_MONEY, // 실패됨
    FAILED_NOT_EXIST_MEMBERSHIP, // 실패됨

}
