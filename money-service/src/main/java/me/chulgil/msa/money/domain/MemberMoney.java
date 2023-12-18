package me.chulgil.msa.money.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    private final String memberMoneyId;

    private final String membershipId;

    private final int balance;

    public static MemberMoney generateMemberMoney(
        MemberMoneyId memberMoneyId,
        MembershipId membershipId,
        MoneyBalance balance
    ) {
        return new MemberMoney(
            memberMoneyId.getValue(),
            membershipId.getValue(),
            balance.getValue()
        );
    }

    @Value
    public static class MemberMoneyId {
        public MemberMoneyId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class MoneyBalance {
        public MoneyBalance(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class MoneyAggregateIdentifier {
        public MoneyAggregateIdentifier(String value) {
            this.value = value;
        }

        String value;
    }

}
