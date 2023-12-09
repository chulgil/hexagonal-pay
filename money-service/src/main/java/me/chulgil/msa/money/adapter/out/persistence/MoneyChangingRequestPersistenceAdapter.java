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
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(
        MoneyChangingRequest.TargetMembershipId targetMembershipId,
        MoneyChangingRequest.MoneyChangingType moneyChangingType,
        MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
        MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
        MoneyChangingRequest.Uuid uuid
    ) {

        return new MoneyChangingRequestJpaEntity(
            targetMembershipId.getValue(),
            moneyChangingType.getValue(),
            changingMoneyAmount.getValue(),
            new Timestamp(System.currentTimeMillis()),
            moneyChangingStatus.getValue(),
            uuid.getValue()
        );
    }


    @Override
    public MemberMoneyJpaEntity increaseMemberMoney(
        MemberMoney.MembershipId membershipId,
        int increaseMoneyAmount
    ) {
        MemberMoneyJpaEntity entity = null;
        try {
            List<MemberMoneyJpaEntity> entityList = moneyRepository.findByMembershipId(Long.parseLong(membershipId.getValue()));
            entity = entityList.get(0);
            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
            return moneyRepository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
