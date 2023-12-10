package me.chulgil.msa.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.application.port.in.RequestFirmbankingCommand;
import me.chulgil.msa.banking.application.port.in.RequestFirmbankingUseCase;
import me.chulgil.msa.banking.domain.FirmbankingRequest;
import me.chulgil.msa.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {
    private final RequestFirmbankingUseCase requestFirmbankingUseCase;

    @PostMapping(path = "/banking/firmbanking/request")
    FirmbankingRequest requestFirmbanking(@RequestBody RequestFirmbankingCommand command) {
        return requestFirmbankingUseCase.requestFirmbanking(command);
    }
}
