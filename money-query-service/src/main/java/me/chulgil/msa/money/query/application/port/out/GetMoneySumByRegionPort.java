package me.chulgil.msa.money.query.application.port.out;

import java.util.Date;
import me.chulgil.msa.money.query.domain.MoneySumByRegion.MoneySum;

public interface GetMoneySumByRegionPort {

    MoneySum getMoneySumByRegionPort(String regionName, Date startDate);
}
