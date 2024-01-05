package me.chulgil.msa.settlement.adapter.out.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.settlement.port.out.GetRegisteredBankAccountPort;
import me.chulgil.msa.settlement.port.out.RegisteredBankAccountAggregateIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceAdapter implements GetRegisteredBankAccountPort {

    private final CommonHttpClient httpClient;
    private final String bankingServiceUrl;

    public BankingServiceAdapter(CommonHttpClient httpClient,
                                 @Value("${service.banking.url}") String membershipServiceUrl) {
        this.httpClient = httpClient;
        this.bankingServiceUrl = membershipServiceUrl;
    }

    @Override
    public RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId) {
        String url = String.join("/", bankingServiceUrl, "banking/account", membershipId);
        try {
            String jsonResponse = httpClient.sendGetRequest(url)
                                            .body();
            RegisteredBankAccount registeredBankAccount = convertJsonToRegisteredBankAccount(jsonResponse);
            return new RegisteredBankAccountAggregateIdentifier(registeredBankAccount.getRegisteredBankAccountId(),
                                                                registeredBankAccount.getAggregateIdentifier(),
                                                                registeredBankAccount.getBankName(),
                                                                registeredBankAccount.getBankName(),
                                                                registeredBankAccount.getBankAccountNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RegisteredBankAccount convertJsonToRegisteredBankAccount(String jsonResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonResponse, RegisteredBankAccount.class);
    }

    @Override
    public void requestFirmbanking(String bankName, String bankAccountNumber, int moneyAmount) {
        String url = String.join("/", bankingServiceUrl, "banking/firmbanking/request");
        try {
            createAndSendFirmBankingRequest(bankName, bankAccountNumber, moneyAmount, url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createAndSendFirmBankingRequest(String bankName, String bankAccountNumber, int moneyAmount, String url)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RequestFirmbankingRequest request = new RequestFirmbankingRequest("hexagonalpay", "111-222-333", bankName,
                                                                          bankAccountNumber, moneyAmount);
        String requestJson = mapper.writeValueAsString(request);
        httpClient.sendPostRequest(url, requestJson);
    }
}
