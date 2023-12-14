package me.chulgil.msa.remittance.adapter.out.service.banking;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.CommonHttpClient;
import me.chulgil.msa.common.ExternalSystemAdapter;
import me.chulgil.msa.remittance.application.port.out.banking.BankingInfo;
import me.chulgil.msa.remittance.application.port.out.banking.BankingPort;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {

    private final CommonHttpClient httpClient;

    @Value("${service.banking.url}")
    private String bankingServiceEndpoint;

    @Override
    public BankingInfo getMembershipBankingInfo(String bankName,
                                                String bankAccountNumber) {
        return null;
    }

    @Override
    public boolean requestFirmbanking(String bankName,
                                      String bankAccountNumber,
                                      int amount) {
        return false;
    }
}
