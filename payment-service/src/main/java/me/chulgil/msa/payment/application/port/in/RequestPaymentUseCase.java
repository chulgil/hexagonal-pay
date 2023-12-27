package me.chulgil.msa.payment.application.port.in;


import me.chulgil.msa.payment.domain.Payment;

public interface RequestPaymentUseCase {
    Payment requestPayment(RequestPaymentCommand command);
}
