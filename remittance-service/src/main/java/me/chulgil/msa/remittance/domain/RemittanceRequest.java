package me.chulgil.msa.remittance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * 송금 요청에 대한 상태 클래스
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RemittanceRequest {

    private final String remittanceRequestId;
    private final String remittanceFromMembershipId;
    private final String toBankName;
    private final String toBankAccountNumber;
    private int remittanceType; // 0: membership(내부 고객), 1: bank (외부 은행 계좌)
    // 송금요청 금액
    private int amount;
    private String remittanceStatus;


    @Value
    public static class RemittanceRequestId {
        public RemittanceRequestId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class RemittanceFromMembershipId {
        public RemittanceFromMembershipId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class ToBankName {
        public ToBankName(String value) {
            this.toBankName = value;
        }

        String toBankName;
    }

    @Value
    public static class ToBankAccountNumber {
        public ToBankAccountNumber(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class RemittanceType {
        public RemittanceType(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class Amount {
        public Amount(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class RemittanceStatus {
        public RemittanceStatus(String value) {
            this.value = value;
        }

        String value;
    }

    public static RemittanceRequest create(RemittanceRequest.RemittanceRequestId remittanceRequestId,
                                           RemittanceRequest.RemittanceFromMembershipId remittanceFromMembershipId,
                                           RemittanceRequest.ToBankName toBankName,
                                           RemittanceRequest.ToBankAccountNumber toBankAccountNumber,
                                           RemittanceRequest.RemittanceType remittanceType,
                                           RemittanceRequest.Amount amount,
                                           RemittanceRequest.RemittanceStatus remittanceStatus) {
        return new RemittanceRequest(
            remittanceRequestId.getValue(),
            remittanceFromMembershipId.getValue(),
            toBankName.getToBankName(),
            toBankAccountNumber.getValue(),
            remittanceType.getValue(),
            amount.getValue(),
            remittanceStatus.getValue()
        );
    }


}
