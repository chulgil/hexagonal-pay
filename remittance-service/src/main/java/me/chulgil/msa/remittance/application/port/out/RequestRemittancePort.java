package me.chulgil.msa.remittance.application.port.out;

import me.chulgil.msa.remittance.adapter.out.persistance.RemittanceRequestJpaEntity;
import me.chulgil.msa.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

    RemittanceRequestJpaEntity createHistory(RequestRemittanceCommand command);
    boolean saveHistory(RemittanceRequestJpaEntity entity);
}
