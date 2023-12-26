package me.chulgil.msa.banking.adapter.axon.event;


import lombok.*;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FirmbankingRequestUpdateEvent {

    private int firmbankingStatus;
}
