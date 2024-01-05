package me.chulgil.msa.payment.adapter.in.web.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataPaymentRepository  extends JpaRepository<PaymentJpaEntity, Long> {

    @Query("SELECT e  FROM PaymentJpaEntity e WHERE e.paymentStatus = :paymentStatus")
    List<PaymentJpaEntity> findByPaymentStatus(@Param("paymentStatus") int paymentStatus);
}
