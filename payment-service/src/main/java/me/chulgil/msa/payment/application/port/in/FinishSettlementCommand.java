package me.chulgil.msa.payment.application.port.in;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinishSettlementCommand {
    private String paymentId;
}
