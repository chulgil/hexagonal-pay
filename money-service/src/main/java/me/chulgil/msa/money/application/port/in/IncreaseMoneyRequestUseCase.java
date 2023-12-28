package me.chulgil.msa.money.application.port.in;


import java.util.List;
import me.chulgil.msa.money.domain.MemberMoney;
import me.chulgil.msa.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUseCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);

    void increaseMoneyRequestByEventWithSaga(IncreaseMoneyRequestCommand command);
    void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);

    List<MemberMoney> getMemberMoneyListByMembershipIds(GetMemberMoneyListByMembershipIdsCommand command);
}
