package me.chulgil.msa.money.application.port.out;

import me.chulgil.msa.money.adapter.out.external.bank.Money;
import me.chulgil.msa.money.adapter.out.external.bank.GetMoneyRequest;

public interface RequestMoneyInfoPort {
    Money getMoneyInfo(GetMoneyRequest request);
}
