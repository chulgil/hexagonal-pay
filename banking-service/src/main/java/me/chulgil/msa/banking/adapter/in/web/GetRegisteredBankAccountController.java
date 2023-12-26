package me.chulgil.msa.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.application.port.in.GetRegisteredBankAccountCommand;
import me.chulgil.msa.banking.application.port.in.GetRegisteredBankAccountUseCase;
import me.chulgil.msa.banking.domain.RegisteredBankAccount;
import me.chulgil.msa.common.WebAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetRegisteredBankAccountController {

    private final GetRegisteredBankAccountUseCase useCase;

    @GetMapping(path ="/banking/account/{membershipId}")
    RegisteredBankAccount getRegisteredBankAccount(@PathVariable String membershipId) {
        GetRegisteredBankAccountCommand command = GetRegisteredBankAccountCommand.builder()
            .membershipId(membershipId)
            .build();
        return useCase.getRegisteredBankAccount(command);
    }
}
