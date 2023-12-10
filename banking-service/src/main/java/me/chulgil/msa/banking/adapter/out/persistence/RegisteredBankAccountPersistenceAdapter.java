package me.chulgil.msa.banking.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.application.port.out.RegisterBankAccountPort;
import me.chulgil.msa.banking.domain.RegisteredBankAccount;
import me.chulgil.msa.common.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository repository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(RegisteredBankAccount.MembershipId membershipId,
                                                                      RegisteredBankAccount.BankName bankName,
                                                                      RegisteredBankAccount.BankAccountNumber bankAccountNumber,
                                                                      RegisteredBankAccount.LinkStatusIsValid linkStatusIsValid) {
        return repository.save(RegisteredBankAccountJpaEntity.builder()
            .membershipId(membershipId.getValue())
            .bankName(bankName.getValue())
            .bankAccountNumber(bankAccountNumber.getValue())
            .linkStatusIsValid(linkStatusIsValid.isValue())
            .build());
    }
}
