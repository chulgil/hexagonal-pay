package me.chulgil.msa.payment.application.port.out;

import me.chulgil.msa.payment.domain.Payment;

public interface CreatePaymentPort {

    Payment createPayment(String requestMembershipId,
                          String requestPrice,
                          String franchiseId,
                          String franchiseFeeRate);
}
