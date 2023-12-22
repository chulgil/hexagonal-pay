package me.chulgil.msa.banking.application.port.in;


import me.chulgil.msa.banking.domain.RegisteredBankAccount;
public interface RegisterBankAccountUseCase {
    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);

    void registerBankAccountByEvent(RegisterBankAccountCommand command);
}
