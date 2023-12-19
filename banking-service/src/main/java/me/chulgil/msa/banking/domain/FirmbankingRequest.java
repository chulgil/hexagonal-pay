package me.chulgil.msa.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FirmbankingRequest {
    private final String firmbankingRequestId;
    private final String fromBankName;
    private final String fromBankAccountNumber;
    private final String toBankName;
    private final String toBankAccountNumber;
    private final int moneyAmount;
    private final int firmbankingStatus;
    private final UUID uuid;
    private final String firmbankingAggregateIdentifier;

    public static FirmbankingRequest generateFirmbankingRequest(
        FirmbankingRequestId firmbankingRequestId,
        FromBankName fromBankingName,
        FromBankAccountNumber fromBankingAccountNumber,
        ToBankName toBankingName,
        ToBankAccountNumber toBankingAccountNumber,
        MoneyAmount moneyAmount,
        FirmbankingStatus firmbankingStatus,
        UUID uuid,
        FirmbankingAggregateIdentifier firmbankingAggregateIdentifier) {
        return new FirmbankingRequest(
            firmbankingRequestId.getValue(),
            fromBankingName.getValue(),
            fromBankingAccountNumber.getValue(),
            toBankingName.getValue(),
            toBankingAccountNumber.getValue(),
            moneyAmount.getValue(),
            firmbankingStatus.getValue(),
            uuid,
            firmbankingAggregateIdentifier.getValue()
        );
    }

    @Value
    public static class FirmbankingRequestId {
        public FirmbankingRequestId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class FromBankName {
        public FromBankName(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class FromBankAccountNumber {
        public FromBankAccountNumber(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class ToBankName {
        public ToBankName(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class ToBankAccountNumber {
        public ToBankAccountNumber(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class MoneyAmount {
        public MoneyAmount(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class FirmbankingStatus {
        public FirmbankingStatus(int value) {
            this.value = value;
        }

        int value;
    }

    @Value
    public static class FirmbankingAggregateIdentifier {
        public FirmbankingAggregateIdentifier(String value) {
            this.value = value;
        }

        String value;
    }
}
