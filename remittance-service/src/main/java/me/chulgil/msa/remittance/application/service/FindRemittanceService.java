package me.chulgil.msa.remittance.application.service;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.remittance.adapter.out.persistance.RemittanceRequestMapper;
import me.chulgil.msa.remittance.application.port.in.FindRemittanceCommand;
import me.chulgil.msa.remittance.application.port.in.FindRemittanceUseCase;
import me.chulgil.msa.remittance.application.port.out.FindRemittancePort;
import me.chulgil.msa.remittance.domain.RemittanceRequest;

import javax.transaction.Transactional;
import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {

    private final FindRemittancePort port;
    private final RemittanceRequestMapper mapper;

    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        port.findRemittanceHistory(command);
        return null;
    }
}
