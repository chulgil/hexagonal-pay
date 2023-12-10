package me.chulgil.msa.banking.adapter.out.external.bank;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.banking.application.port.out.RequestBankAccountInfoPort;
import me.chulgil.msa.banking.application.port.out.RequestExternalFirmbankingPort;
import me.chulgil.msa.common.ExternalSystemAdapter;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort, RequestExternalFirmbankingPort {


    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {

        // 실제로 외부 은행에 http 을 통해서
        // 실제 은행 계좌 정보를 가져오고

        // 실제 은행 계좌 -> BankAccount
        return new BankAccount(
            request.getBankName(),
            request.getBankAccountNumber(),
            true
        );
    }

    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {
        return null;
    }
}
