package me.chulgil.msa.money.application.port.in;


import me.chulgil.msa.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUserCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
}
