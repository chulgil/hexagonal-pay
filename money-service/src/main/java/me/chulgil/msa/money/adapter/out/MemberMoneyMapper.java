package me.chulgil.msa.money.adapter.out;

import me.chulgil.msa.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.chulgil.msa.money.domain.MemberMoney;
import org.springframework.stereotype.Component;

@Component
public class MemberMoneyMapper {

    public MemberMoney mapToDomainEntity(MemberMoneyJpaEntity entity) {
        return MemberMoney.generateMemberMoney(
                new MemberMoney.MemberMoneyId(entity.getId()+""),
                new MemberMoney.MembershipId(entity.getMembershipId()+""),
                new MemberMoney.MoneyBalance(entity.getBalance())
        );
    }

}
