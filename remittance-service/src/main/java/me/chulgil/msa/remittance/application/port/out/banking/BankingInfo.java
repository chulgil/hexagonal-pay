package me.chulgil.msa.remittance.application.port.out.banking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BankingInfo {
    private String bankName;
    private String bankAccountNumber;
    private int balance;
}