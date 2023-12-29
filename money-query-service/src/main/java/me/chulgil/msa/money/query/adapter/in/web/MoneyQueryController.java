package me.chulgil.msa.money.query.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.WebAdapter;
import me.chulgil.msa.money.query.application.port.in.QueryMoneySumByRegionQuery;
import me.chulgil.msa.money.query.application.port.in.QueryMoneySumByRegionUseCase;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class MoneyQueryController {

    private final QueryMoneySumByRegionUseCase useCase;

    @GetMapping(path = "/money/query/get-money-sum-by-address/{address}")
    long getMoneySumByAddress(@PathVariable String address) {

        QueryMoneySumByRegionQuery query = QueryMoneySumByRegionQuery.builder()
                .address(address)
                .build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        stopWatch.stop();
        System.out.println("StopWatch: " + stopWatch.getTotalTimeMillis());

        return useCase.queryMoneySumByRegion(query)
                      .getMoneySum();
    }

}
