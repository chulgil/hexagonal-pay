package me.chulgil.msa.banking.application.port.out;

import me.chulgil.msa.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import me.chulgil.msa.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingPort {

    FirmbankingRequestJpaEntity createFirmbankingRequest(
        FirmbankingRequest.FromBankName fromBankName,
        FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
        FirmbankingRequest.ToBankName toBankName,
        FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
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
