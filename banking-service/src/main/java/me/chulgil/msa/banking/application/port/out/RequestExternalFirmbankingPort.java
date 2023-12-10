package me.chulgil.msa.banking.application.port.out;

import me.chulgil.msa.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import me.chulgil.msa.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {
    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}
