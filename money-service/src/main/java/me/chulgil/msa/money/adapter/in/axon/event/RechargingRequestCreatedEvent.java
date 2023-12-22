package me.chulgil.msa.money.adapter.in.axon.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * "충전" 동작을 요청이 생성되었다는 "이벤트"
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RechargingRequestCreatedEvent {

    private String rechargingRequestId;

    private String membershipId;

    private int amount;

    private String registeredBankAccountAggregateIdentifier;

    private String bankName;
    private String bankAccountNumber;
}
