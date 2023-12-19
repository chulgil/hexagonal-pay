package me.chulgil.msa.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.application.port.in.RequestFirmbankingCommand;
import me.chulgil.msa.banking.application.port.in.RequestFirmbankingUseCase;
import me.chulgil.msa.banking.application.port.in.UpdateFirmbankingCommand;
import me.chulgil.msa.banking.application.port.in.UpdateFirmbankingUseCase;
import me.chulgil.msa.banking.domain.FirmbankingRequest;
import me.chulgil.msa.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {
    private final RequestFirmbankingUseCase requestFirmbankingUseCase;
    private final UpdateFirmbankingUseCase updateFirmbankingUseCase;

    @PostMapping(path = "/banking/firmbanking/request")
    FirmbankingRequest requestFirmbanking(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();
        return requestFirmbankingUseCase.requestFirmbanking(command);
    }

    @PostMapping(path = "/banking/firmbanking/request-eda")
    void requestFirmbankingByEvent(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();
        requestFirmbankingUseCase.requestFirmbankingByEvent(command);
    }

    @PutMapping(path = "/banking/firmbanking/update-eda")
    void updateFirmbankingByEvent(@RequestBody UpdateFirmbankingRequest request) {
        UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder()
                .firmbankingAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
                .firmbankingStatus(request.getStatus())
                .build();
        updateFirmbankingUseCase.updateFirmbankingByEvent(command);
    }

}
