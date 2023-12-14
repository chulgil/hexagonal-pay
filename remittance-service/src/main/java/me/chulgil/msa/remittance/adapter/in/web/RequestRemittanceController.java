package me.chulgil.msa.remittance.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.WebAdapter;
import me.chulgil.msa.remittance.application.port.in.RequestRemittanceCommand;
import me.chulgil.msa.remittance.application.port.in.RequestRemittanceUseCase;
import me.chulgil.msa.remittance.domain.RemittanceRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestRemittanceController {

    private final RequestRemittanceUseCase requestRemittanceUseCase;

    @PostMapping(path = "/remittance/request")
    RemittanceRequest requestRemittance(@RequestBody RequestRemittanceInfo request) {

        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .fromMembershipId(request.getFromMembershipId())
                .toMembershipId(request.getToMembershipId())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .amount(request.getAmount())
                .remittanceType(request.getRemittanceType())
                .build();

        return requestRemittanceUseCase.requestRemittance(command);
    }
}
