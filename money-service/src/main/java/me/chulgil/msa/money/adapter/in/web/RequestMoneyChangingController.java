package me.chulgil.msa.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.WebAdapter;
import me.chulgil.msa.money.application.port.in.IncreaseMoneyRequestCommand;
import me.chulgil.msa.money.application.port.in.IncreaseMoneyRequestUseCase;
import me.chulgil.msa.money.domain.MoneyChangingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseUseCase;

    @PostMapping(path = "/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
            .targetMembershipId(request.getTargetMembershipId())
            .amount(request.getAmount())
            .build();

        MoneyChangingRequest moneyChangingRequest = increaseUseCase.increaseMoneyRequest(command);
        return new MoneyChangingResultDetail(
            moneyChangingRequest.getMoneyChangingRequestId(),
            0,
            0,
            moneyChangingRequest.getChangingMoneyAmount()
        );
    }

    @PostMapping(path = "/money/increase/async")
    MoneyChangingResultDetail increaseAsyncMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
            .targetMembershipId(request.getTargetMembershipId())
            .amount(request.getAmount())
            .build();

        MoneyChangingRequest moneyChangingRequest = increaseUseCase.increaseMoneyRequestAsync(command);
        return new MoneyChangingResultDetail(
            moneyChangingRequest.getMoneyChangingRequestId(),
            0,
            0,
            moneyChangingRequest.getChangingMoneyAmount()
        );
    }

    @PostMapping(path = "/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest request) {
        return null;
    }
}
