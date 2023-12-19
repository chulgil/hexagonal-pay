package me.chulgil.msa.banking.application.port.out;

import me.chulgil.msa.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import me.chulgil.msa.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingPort {

    FirmbankingRequestJpaEntity createFirmbankingRequest(
        FirmbankingRequest.FromBankName fromBankingName,
        FirmbankingRequest.FromBankAccountNumber fromBankingAccountNumber,
        FirmbankingRequest.ToBankName toBankingName,
        FirmbankingRequest.ToBankAccountNumber toBankingAccountNumber,
        FirmbankingRequest.MoneyAmount moneyAmount,
        FirmbankingRequest.FirmbankingStatus firmbankingStatus,
        FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
    );

    FirmbankingRequestJpaEntity modifyFirmbankingRequest(
        FirmbankingRequestJpaEntity entity
    );

    FirmbankingRequestJpaEntity getFirmbankingRequest(
        FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
    );

}
