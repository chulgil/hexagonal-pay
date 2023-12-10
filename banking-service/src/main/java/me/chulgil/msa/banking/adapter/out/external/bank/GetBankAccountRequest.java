package me.chulgil.msa.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder @AllArgsConstructor
public class GetBankAccountRequest {
    private String bankName;
    private String bankAccountNumber;
}
