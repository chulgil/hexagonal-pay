package me.chulgil.msa.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.application.port.in.RegisterBankAccountCommand;
import me.chulgil.msa.banking.application.port.in.RegisterBankAccountUseCase;
import me.chulgil.msa.banking.domain.RegisteredBankAccount;
import me.chulgil.msa.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase useCase;
    @PostMapping(path = "/banking/account/register")
    RegisteredBankAccount registerBankAccount(@RequestBody RegisterBankAccountRequest request) {

        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
            .membershipId(request.getMembershipId())
            .bankName(request.getBankName())
            .bankAccountNumber(request.getBankAccountNumber())
            .isValid(request.isValid())
            .build();

        return useCase.registerBankAccount(command);
    }
}
