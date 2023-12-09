package me.chulgil.msa.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {
    private final String moneyChangingRequestId;

    // 어떤 고객의 증액/감액 요청을 했는지 멤버 정보
    private final String targetMembershipId;

    // 그 요청이 증액/감액 요청인지
    private final int changingType; // enum 0:증액, 1: 감액

    private final int changingMoneyAmount; // 증액/감액 금액

    private final int changingMoneyStatus; // enum 0:요청, 1: 성공, 2: 실패

    private final String uuid;

    private final Date createdAt;


    // Membership
    // 오염이 되면 안되는 클래스. 고객 정보. 핵심 도메인

    public static MoneyChangingRequest generateMoneyChangingRequest(
        MoneyChangingRequestId moneyChangingRequestId,
        TargetMembershipId targetMembershipId,
        MoneyChangingType moneyChangingType,
        ChangingMoneyAmount changingMoneyAmount,
        MoneyChangingStatus moneyChangingStatus,
        String uuid
    ) {
        return new MoneyChangingRequest(
            moneyChangingRequestId.getValue(),
            targetMembershipId.getValue(),
            moneyChangingType.getValue(),
            changingMoneyAmount.getValue(),
            moneyChangingStatus.getValue(),
            uuid,
            new Date()
        );
    }

    @Value
    public static class MoneyChangingRequestId {
        public MoneyChangingRequestId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class TargetMembershipId {
        public TargetMembershipId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class MoneyChangingType {
        public MoneyChangingType(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class ChangingMoneyAmount {
        public ChangingMoneyAmount(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class MoneyChangingStatus {
        public MoneyChangingStatus(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class Uuid {
        public Uuid(String uuid) {
            this.value = uuid;
        }

        String value;
    }

}
