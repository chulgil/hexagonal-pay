package me.chulgil.msa.money.adapter.in.axon.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberMoneyCreateEvent {
    private String membershipId;
}
