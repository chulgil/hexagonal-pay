package me.chulgil.msa.money.adapter.out.external.bank;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.money.application.port.out.RequestMoneyInfoPort;
import me.chulgil.msa.common.ExternalSystemAdapter;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyAdapter implements RequestMoneyInfoPort {

    @Override
    public Money getMoneyInfo(GetMoneyRequest request) {

        // 실제 외부 은행에 http 통신을 통해 계좌 정보를 요청하는 로직이 들어가야 함
        return Money.builder()
            .bankName(request.getBankName())
            .moneyNumber(request.getMoneyNumber())
            .isValid(true)
            .build();
    }
}
