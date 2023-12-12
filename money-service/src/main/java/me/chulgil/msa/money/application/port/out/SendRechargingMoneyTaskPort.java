package me.chulgil.msa.money.application.port.out;

import me.chulgil.msa.common.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort {
    void sendRechargingMoneyTask(RechargingMoneyTask task);
}
