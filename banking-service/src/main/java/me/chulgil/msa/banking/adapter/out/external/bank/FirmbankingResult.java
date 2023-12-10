package me.chulgil.msa.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder @AllArgsConstructor
public class FirmbankingResult {
    private int resultCode; // 0: 성공, 1: 실패
}
