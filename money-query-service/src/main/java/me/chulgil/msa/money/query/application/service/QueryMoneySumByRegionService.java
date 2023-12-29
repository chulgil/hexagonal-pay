package me.chulgil.msa.money.query.application.service;

import java.util.concurrent.ExecutionException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.money.query.adapter.axon.QueryMoneySumByAddress;
import me.chulgil.msa.money.query.application.port.in.QueryMoneySumByRegionQuery;
import me.chulgil.msa.money.query.application.port.in.QueryMoneySumByRegionUseCase;
import me.chulgil.msa.money.query.domain.MoneySumByRegion;
import org.axonframework.queryhandling.QueryGateway;

@UseCase
@RequiredArgsConstructor
@Transactional
public class QueryMoneySumByRegionService implements QueryMoneySumByRegionUseCase {

    private final QueryGateway queryGateway;

    @Override
    public MoneySumByRegion queryMoneySumByRegion(QueryMoneySumByRegionQuery query) {
        try {
            return queryGateway.query(QueryMoneySumByAddress.builder()
                                              .address(query.getAddress())
                                              .build(), MoneySumByRegion.class).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
