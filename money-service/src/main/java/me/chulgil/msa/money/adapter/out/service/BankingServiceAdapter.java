package me.chulgil.msa.money.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.money.application.port.out.GetRegisteredBankAccountPort;
import me.chulgil.msa.money.application.port.out.RegisteredBankAccountAggregateIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceAdapter implements GetRegisteredBankAccountPort {

    private final CommonHttpClient httpClient;
    private final String bankingServiceUrl;

    public BankingServiceAdapter(CommonHttpClient httpClient,
                                 @Value("${service.banking.url}") String bankingServiceUrl) {
        this.httpClient = httpClient;
        this.bankingServiceUrl = bankingServiceUrl;
    }

    @Override
    public RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId) {
        String url = String.join("/", bankingServiceUrl, "banking/account", membershipId);
        try {
            String jsonResponse = httpClient.sendGetRequest(url)
                                            .body();
            // json RegisteredBankAccount

            ObjectMapper mapper = new ObjectMapper();
            RegisteredBankAccount bankAccount = mapper.readValue(jsonResponse, RegisteredBankAccount.class);
            return RegisteredBankAccountAggregateIdentifier.builder()
                    .aggregateIdentifier(bankAccount.getAggregateIdentifier())
                    .registeredBankAccountId(bankAccount.getRegisteredBankAccountId())
                    .membershipId(bankAccount.getMembershipId())
                    .bankName(bankAccount.getBankName())
                    .bankAccountNumber(bankAccount.getBankAccountNumber())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
