package me.chulgil.msa.remittance.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.PersistenceAdapter;
import me.chulgil.msa.remittance.application.port.in.FindRemittanceCommand;
import me.chulgil.msa.remittance.application.port.in.RequestRemittanceCommand;
import me.chulgil.msa.remittance.application.port.out.FindRemittancePort;
import me.chulgil.msa.remittance.application.port.out.RequestRemittancePort;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class RemittanceRequestPersistenceAdapter implements FindRemittancePort, RequestRemittancePort {

    private final SpringDataRemittanceRequestRepository repository;


    @Override
    public List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command) {
        // using JPA
        return null;
    }

    @Override
    public RemittanceRequestJpaEntity createHistory(RequestRemittanceCommand command) {
        return repository.save(RemittanceRequestJpaEntity.builder()
                .fromMembershipId(command.getFromMembershipId())
                .toMembershipId(command.getToMembershipId())
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .amount(command.getAmount())
                .remittanceType(command.getRemittanceType())
                .build());
    }

    @Override
    public boolean saveHistory(RemittanceRequestJpaEntity entity) {
        repository.save(entity);
        return true;
    }
}
