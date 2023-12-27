package me.chulgil.msa.payment.adapter.in.web.out.persistence;

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
}
