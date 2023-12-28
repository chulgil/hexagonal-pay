package me.chulgil.msa.money.aggreagation.application.port.in;

public interface GetMoneySumByAddressUseCase {
    int getMoneySumByAddress(GetMoneySumByAddressCommand command);
}
