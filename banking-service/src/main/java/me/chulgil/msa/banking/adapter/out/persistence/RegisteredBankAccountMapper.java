package me.chulgil.msa.banking.adapter.out.persistence;

import me.chulgil.msa.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisteredBankAccountMapper {

    public RegisteredBankAccount mapToDomainEntity(RegisteredBankAccountJpaEntity entity) {
        return RegisteredBankAccount.generateRegisteredBankAccount(
            new RegisteredBankAccount.RegisteredBankAccountId(entity.getRegisteredBankAccountId() + ""),
            new RegisteredBankAccount.MembershipId(entity.getMembershipId()),
            new RegisteredBankAccount.BankName(entity.getBankName()),
            new RegisteredBankAccount.BankAccountNumber(entity.getBankAccountNumber()),
            new RegisteredBankAccount.LinkStatusIsValid(entity.isLinkStatusIsValid())
        );
    }
}
