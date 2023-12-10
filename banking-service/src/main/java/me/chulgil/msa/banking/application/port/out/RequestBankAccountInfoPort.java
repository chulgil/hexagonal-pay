package me.chulgil.msa.banking.application.port.out;

import me.chulgil.msa.banking.adapter.out.external.bank.BankAccount;
import me.chulgil.msa.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
    BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
