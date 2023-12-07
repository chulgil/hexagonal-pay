package me.chulgil.msa.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {
    private final String registeredBankAccountId;
    private final String membershipId;
    private final String bankName; // enum
    private final String bankAccountNumber;
    private final boolean linkedStatusIsValid;  // 상태가 정상 인지

    // Membership
    // 오염이 되면 안되는 클래스. 고객 정보. 핵심 도메인

    public static RegisteredBankAccount generateAccount(RegisteredBankAccountId id,
                                                        MembershipId membershipId,
                                                        BankName name,
                                                        BankAccountNumber account,
                                                        LinkStatusIsValid isValid) {
        return new RegisteredBankAccount(id.registeredBankAccountId,
            membershipId.value,
            name.value,
            account.value,
            isValid.value);
    }

    @Value
    public static class RegisteredBankAccountId {
        public RegisteredBankAccountId(String value) {
            this.registeredBankAccountId = value;
        }

        String registeredBankAccountId;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class BankName {
        public BankName(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class BankAccountNumber {
        public BankAccountNumber(String value) {
            this.value = value;
        }

        String value;
    }

    @Value
    public static class LinkStatusIsValid {
        public LinkStatusIsValid(boolean value) {
            this.value = value;
        }

        boolean value;
    }

}
