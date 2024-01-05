package me.chulgil.msa.payment.adapter.in.web.out.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.PersistenceAdapter;
import me.chulgil.msa.payment.application.port.out.CreatePaymentPort;
import me.chulgil.msa.payment.domain.Payment;

@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements CreatePaymentPort {

    private final SpringDataPaymentRepository paymentRepository;
    private final PaymentMapper mapper;

    @Override
    public Payment createPayment(String requestMembershipId,
                                 String requestPrice,
                                 String franchiseId,
                                 String franchiseFeeRate) {
        PaymentJpaEntity jpaEntity = paymentRepository.save(
                PaymentJpaEntity.builder()
                        .requestMembershipId(requestMembershipId)
                        .requestPrice(Integer.parseInt(requestPrice))
                        .franchiseId(franchiseId)
                        .franchiseFeeRate(franchiseFeeRate)
                        .paymentStatus(0) // 0: 승인, 1: 실패, 2: 정산 완료.
                        .build());
        return mapper.mapToDomainEntity(jpaEntity);
    }

    @Override
    public List<Payment> getNormalStatusPayments() {
        List<Payment> payments = new ArrayList<>();
        List<PaymentJpaEntity> paymentJpaEntities = paymentRepository.findByPaymentStatus(0);
        if (paymentJpaEntities != null) {
            for (PaymentJpaEntity paymentJpaEntity : paymentJpaEntities) {
                payments.add(mapper.mapToDomainEntity(paymentJpaEntity));
            }
            return payments;
        }
        return null;
    }

    @Override
    public void changePaymentRequestStatus(String paymentId, int status) {
        Optional<PaymentJpaEntity> paymentJpaEntity = paymentRepository.findById(Long.parseLong(paymentId));
        if (paymentJpaEntity.isPresent()) {
            paymentJpaEntity.get().setPaymentStatus(status);
            paymentRepository.save(paymentJpaEntity.get());
        }
    }
}
