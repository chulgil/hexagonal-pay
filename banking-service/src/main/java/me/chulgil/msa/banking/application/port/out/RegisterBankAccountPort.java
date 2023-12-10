package me.chulgil.msa.banking.application.port.out;

import me.chulgil.msa.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import me.chulgil.msa.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountPort {

    RegisteredBankAccountJpaEntity createRegisteredBankAccount(
        RegisteredBankAccount.MembershipId membershipId,
        RegisteredBankAccount.BankName bankName,
        RegisteredBankAccount.BankAccountNumber bankAccountNumber,
        RegisteredBankAccount.LinkStatusIsValid linkStatusIsValid
    );
}
