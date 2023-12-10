package me.chulgil.msa.banking.application.port.in;

import me.chulgil.msa.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingUseCase {
    FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command);
}
