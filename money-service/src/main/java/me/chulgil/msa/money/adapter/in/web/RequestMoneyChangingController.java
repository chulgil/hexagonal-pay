package me.chulgil.msa.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.WebAdapter;
import me.chulgil.msa.money.application.port.in.IncreaseMoneyRequestCommand;
import me.chulgil.msa.money.application.port.in.IncreaseMoneyRequestUserCase;
import me.chulgil.msa.money.domain.MoneyChangingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUserCase useCase;

    @PostMapping(path = "/banking/account/register")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
            .targetMembershipId(request.getTargetMembershipId())
            .amount(request.getAmount())
            .build();

        MoneyChangingRequest moneyChangingRequest = useCase.increaseMoneyRequest(command);
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail();
        return resultDetail;
    }
}
