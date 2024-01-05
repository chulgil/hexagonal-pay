package me.chulgil.msa.payment.application.port.in;


import java.util.List;
import me.chulgil.msa.payment.domain.Payment;

public interface RequestPaymentUseCase {
    Payment requestPayment(RequestPaymentCommand command);

    List<Payment> getNormalStatusPayments();

    void finishPayment(FinishSettlementCommand command);
}
