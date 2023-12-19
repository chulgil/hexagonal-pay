package me.chulgil.msa.banking.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.application.port.out.RequestFirmbankingPort;
import me.chulgil.msa.banking.domain.FirmbankingRequest;
import me.chulgil.msa.common.PersistenceAdapter;

import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdapter implements RequestFirmbankingPort {

    private final SpringDataFirmbankingRequestRepository repository;

    @Override
    public FirmbankingRequestJpaEntity createFirmbankingRequest(FirmbankingRequest.FromBankName fromBankingName,
                                                                FirmbankingRequest.FromBankAccountNumber fromBankingAccountNumber,
                                                                FirmbankingRequest.ToBankName toBankingName,
                                                                FirmbankingRequest.ToBankAccountNumber toBankingAccountNumber,
                                                                FirmbankingRequest.MoneyAmount moneyAmount,
                                                                FirmbankingRequest.FirmbankingStatus firmbankingStatus,
                                                                FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier) {
        return repository.save(FirmbankingRequestJpaEntity.builder()
            .fromBankName(fromBankingName.getValue())
            .fromBankAccountNumber(fromBankingAccountNumber.getValue())
            .toBankName(toBankingName.getValue())
            .toBankAccountNumber(toBankingAccountNumber.getValue())
            .moneyAmount(moneyAmount.getValue())
            .firmbankingStatus(firmbankingStatus.getValue())
            .uuid(UUID.randomUUID())
            .aggregateIdentifier(firmbankingAggregateIdentifier.getValue())
            .build());
    }

    @Override public FirmbankingRequestJpaEntity modifyFirmbankingRequest(FirmbankingRequestJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public FirmbankingRequestJpaEntity getFirmbankingRequest(FirmbankingRequest.FirmbankingAggregateIdentifier
                                                                     firmbankingAggregateIdentifier) {
        List<FirmbankingRequestJpaEntity> entities =
            repository.findByAggregateIdentifier(firmbankingAggregateIdentifier.getValue());
        if (entities.size() >= 1) {
            return entities.get(0);
        }
        return null;
    }
}
