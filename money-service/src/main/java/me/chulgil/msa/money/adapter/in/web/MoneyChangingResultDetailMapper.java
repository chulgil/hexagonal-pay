package me.chulgil.msa.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.chulgil.msa.money.domain.MoneyChangingRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetailMapper {

    private String targetMembershipId;

    // 무조건 증액 요청 (충전)
    private int amount;

    public MoneyChangingResultDetail mapToMoneyChangingResultDetail(MoneyChangingRequest moneyChangingRequest) {
        return null;
    }

}
