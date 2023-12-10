package me.chulgil.msa.banking.adapter.out.persistence;

import me.chulgil.msa.banking.domain.FirmbankingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FirmbankingRequestMapper {
    public FirmbankingRequest mapToDomainEntity(FirmbankingRequestJpaEntity entity, UUID uuid) {
        return null;
    }
}
