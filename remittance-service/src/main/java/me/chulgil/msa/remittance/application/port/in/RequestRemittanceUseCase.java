package me.chulgil.msa.remittance.application.port.in;

import me.chulgil.msa.remittance.domain.RemittanceRequest;

public interface RequestRemittanceUseCase {

    RemittanceRequest requestRemittance(RequestRemittanceCommand command);
}
