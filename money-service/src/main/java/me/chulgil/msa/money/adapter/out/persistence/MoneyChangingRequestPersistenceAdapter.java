package me.chulgil.msa.money.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.PersistenceAdapter;
import me.chulgil.msa.money.application.port.out.IncreaseMoneyPort;
import me.chulgil.msa.money.domain.MemberMoney;
import me.chulgil.msa.money.domain.MoneyChangingRequest;

import java.sql.Timestamp;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort {

    private final SpringDataMoneyChangingRequestRepository moneyChangingRequestRepository;
    private final SpringDataMemberMoneyRepository moneyRepository;

    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(MoneyChangingRequest.TargetMembershipId targetMembershipId,
                                                                    MoneyChangingRequest.MoneyChangingType moneyChangingType,
                                                                    MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
                                                                    MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
                                                                    MoneyChangingRequest.Uuid uuid) {

        return moneyChangingRequestRepository.save(MoneyChangingRequestJpaEntity.builder()
            .targetMembershipId(targetMembershipId.getValue())
            .moneyChangingType(moneyChangingType.getValue())
            .moneyAmount(changingMoneyAmount.getValue())
            .changingMoneyStatus(moneyChangingStatus.getValue())
            .moneyChangingType(moneyChangingType.getValue())
            .timeStamp(new Timestamp(System.currentTimeMillis()))
            .uuid(uuid.getValue())
            .build());
    }


    @Override
    public MemberMoneyJpaEntity increaseMemberMoney(MemberMoney.MembershipId membershipId,
                                                    int increaseMoneyAmount) {
        MemberMoneyJpaEntity entity = null;
        try {
            List<MemberMoneyJpaEntity> entityList = moneyRepository.findByMembershipId(
                Long.parseLong(membershipId.getValue()));
            entity = entityList.get(0);
            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
        } catch (Exception e) {
            entity = MemberMoneyJpaEntity.builder()
                .membershipId(Long.parseLong(membershipId.getValue()))
                .balance(increaseMoneyAmount)
                .build();
        }
        return moneyRepository.save(entity);
    }
}
