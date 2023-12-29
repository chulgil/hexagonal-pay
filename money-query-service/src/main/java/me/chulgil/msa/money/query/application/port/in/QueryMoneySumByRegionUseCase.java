package me.chulgil.msa.money.query.application.port.in;

import me.chulgil.msa.money.query.domain.MoneySumByRegion;

public interface QueryMoneySumByRegionUseCase {
    MoneySumByRegion queryMoneySumByRegion (QueryMoneySumByRegionQuery query);
}
