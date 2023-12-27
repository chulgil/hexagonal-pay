package me.chulgil.msa.payment.adapter.in.web.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPaymentRepository  extends JpaRepository<PaymentJpaEntity, Long> {
}
