package me.chulgil.msa.membership.application.saga;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentCancelledEvent  {
    private String orderId;
    private String paymentId;

}
