package me.chulgil.msa.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {
    @Getter private final String membershipId;
    @Getter private final String name;
    @Getter private final String email;
    @Getter private final String address;
    @Getter private final boolean isValid;
    @Getter private final boolean isCorp;

    // Membership
    // 오염이 되면 안되는 클래스. 고객 정보. 핵심 도메인

    public static Membership generateMember (
            MembershipId membershipId
            , MembershipName membershipName
            , MembershipEmail membershipEmail
            , MembershipAddress membershipAddress
            , MembershipIsValid membershipIsValid
            , MembershipIsCorp membershipIsCorp
    ){
        return new Membership(
                membershipId.value,
                membershipName.value,
                membershipEmail.value,
                membershipAddress.value,
                membershipIsValid.value,
                membershipIsCorp.value
        );
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.value = value;
        }
        String value;
    }

    @Value
    public static class MembershipName {
        public MembershipName(String value) {
            this.value = value;
        }
        String value;
    }
    @Value
    public static class MembershipEmail {
        public MembershipEmail(String value) {
            this.value = value;
        }
        String value;
    }

    @Value
    public static class MembershipAddress {
        public MembershipAddress(String value) {
            this.value = value;
        }
        String value;
    }

    @Value
    public static class MembershipIsValid {
        public MembershipIsValid(boolean value) {
            this.value = value;
        }
        boolean value;
    }

    @Value
    public static class MembershipIsCorp {
        public MembershipIsCorp(boolean value) {
            this.value = value;
        }
        boolean value;
    }
}
