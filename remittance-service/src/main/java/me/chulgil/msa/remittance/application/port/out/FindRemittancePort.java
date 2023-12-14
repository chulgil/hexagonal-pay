package me.chulgil.msa.remittance.application.port.out;

import me.chulgil.msa.remittance.adapter.out.persistance.RemittanceRequestJpaEntity;
import me.chulgil.msa.remittance.application.port.in.FindRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {

    List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);
}
