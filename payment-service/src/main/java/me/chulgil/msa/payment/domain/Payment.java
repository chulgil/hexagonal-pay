package me.chulgil.msa.payment.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    private Long paymentId;
    private String requestMembershipId;
    private int requestPrice;
    private String franchiseId;
    private String franchiseFeeRate;
    private int paymentStatus;
    private Date approvedAt;

    public static Payment generate(PaymentId paymentId,
                                   RequestMembershipId requestMembershipId,
                                   RequestPrice requestPrice,
                                   FranchiseId franchiseId,
                                   FranchiseFeeRate franchiseFeeRate,
                                   PaymentStatus paymentStatus,
                                   ApprovedAt approvedAt) {
        return new Payment(paymentId.getValue(), requestMembershipId.getValue(), requestPrice.getValue(),
                           franchiseId.getValue(), franchiseFeeRate.getValue(), paymentStatus.getValue(),
                           approvedAt.getValue());
    }

    @Value
    public static class PaymentId {

        public PaymentId(long value) {
            this.value = value;
        }

        long value;
    }

    @Value
    public static class RequestMembershipId {

        public RequestMembershipId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class RequestPrice {

        public RequestPrice(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class FranchiseId {

        public FranchiseId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class FranchiseFeeRate {

        public FranchiseFeeRate(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class PaymentStatus {

        public PaymentStatus(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class ApprovedAt {

        public ApprovedAt(Date value) {
            this.value = value;
        }

        Date value;
    }


}
