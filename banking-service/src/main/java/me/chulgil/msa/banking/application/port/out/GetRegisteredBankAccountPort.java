package me.chulgil.msa.banking.application.port.out;

import me.chulgil.msa.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import me.chulgil.msa.banking.application.port.in.GetRegisteredBankAccountCommand;

public interface GetRegisteredBankAccountPort {
    RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command);
}
