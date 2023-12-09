package me.chulgil.msa.money.adapter.out.persistence;

import me.chulgil.msa.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MoneyChangingRequestMapper {

    public MoneyChangingRequest mapToDomainEntity(MoneyChangingRequestJpaEntity entity) {
        return MoneyChangingRequest.generateMoneyChangingRequest(
            new MoneyChangingRequest.MoneyChangingRequestId(entity.getMoneyChangingRequestId()+""),
            new MoneyChangingRequest.TargetMembershipId(entity.getTargetMembershipId()),
            new MoneyChangingRequest.MoneyChangingType(entity.getMoneyChangingType()),
            new MoneyChangingRequest.ChangingMoneyAmount(entity.getMoneyAmount()),
            new MoneyChangingRequest.MoneyChangingStatus(entity.getChangingMoneyStatus()),
            entity.getUuid()
        );
    }

}
