package me.chulgil.msa.money.aggreagation.adapter.in.web;


import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.WebAdapter;
import me.chulgil.msa.money.aggreagation.application.port.in.GetMoneySumByAddressCommand;
import me.chulgil.msa.money.aggreagation.application.port.in.GetMoneySumByAddressUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetMoneySumController {

    private final GetMoneySumByAddressUseCase getMoneySumByAddressUseCase;

    @PostMapping(path = "/money/aggregation/get-money-sum-by-address")
    int getMoneySumByAddress(@RequestBody GetMoneySumByAddressRequest request) {

        return getMoneySumByAddressUseCase.getMoneySumByAddress(
                GetMoneySumByAddressCommand.builder()
                        .address(request.getAddress())
                        .build()
        );
    }
}
