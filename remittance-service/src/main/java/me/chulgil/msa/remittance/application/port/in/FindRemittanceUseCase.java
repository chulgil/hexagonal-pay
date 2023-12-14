package me.chulgil.msa.remittance.application.port.in;

import me.chulgil.msa.remittance.domain.RemittanceRequest;

import java.util.List;

public interface FindRemittanceUseCase {

    List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command);
}
