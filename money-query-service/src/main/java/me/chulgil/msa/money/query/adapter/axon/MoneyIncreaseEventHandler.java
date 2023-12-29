package me.chulgil.msa.money.query.adapter.axon;


import me.chulgil.msa.common.event.RequestFirmbankingFinishedEvent;
import me.chulgil.msa.money.query.application.port.out.GetMemberAddressInfoPort;
import me.chulgil.msa.money.query.application.port.out.InsertMoneyIncreaseEventByAddress;
import me.chulgil.msa.money.query.application.port.out.MemberAddressInfo;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class MoneyIncreaseEventHandler {

    @EventHandler
    public void handler(RequestFirmbankingFinishedEvent event, GetMemberAddressInfoPort getMemberAddressInfoPort,
                        InsertMoneyIncreaseEventByAddress insertMoneyIncreaseEventByAddress) {
        System.out.println("Money Increase Event Received: " + event.toString());

        MemberAddressInfo addressInfo = getMemberAddressInfoPort.getMemberAddressInfo(event.getMembershipId());

        String address = addressInfo.getAddress(); // 강남구
        int moneyIncrease = event.getMoneyAmount(); // 1000원 증가

        // Dynamodb Insert!
        insertMoneyIncreaseEventByAddress.insertMoneyIncreaseEventByAddress(address, moneyIncrease);
    }

}
