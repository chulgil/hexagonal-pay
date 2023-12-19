package me.chulgil.msa.banking.adapter.axon.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FirmbankingRequestUpdateEvent {

    private int firmbankingStatus;
}
