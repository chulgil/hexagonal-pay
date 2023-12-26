package me.chulgil.msa.money.adapter.in.axon.event;

import lombok.*;

/**
 * "충전" 동작을 요청이 생성되었다는 "이벤트"
 */
@Data
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
