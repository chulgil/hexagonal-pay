package me.chulgil.msa.money.application.port.out;

public interface GetRegisteredBankAccountPort {

    RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId);
}
