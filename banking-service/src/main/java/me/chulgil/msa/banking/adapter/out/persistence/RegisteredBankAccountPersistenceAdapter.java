package me.chulgil.msa.banking.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.application.port.in.GetRegisteredBankAccountCommand;
import me.chulgil.msa.banking.application.port.out.GetRegisteredBankAccountPort;
import me.chulgil.msa.banking.application.port.out.RegisterBankAccountPort;
import me.chulgil.msa.banking.domain.RegisteredBankAccount;
import me.chulgil.msa.common.PersistenceAdapter;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, GetRegisteredBankAccountPort {

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

    @Override public RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        List<RegisteredBankAccountJpaEntity> entity = repository.findByMembershipoId(command.getMembershipId());
        if (!entity.isEmpty()) {
            return entity.get(0);
        }
        return null;
    }
}
