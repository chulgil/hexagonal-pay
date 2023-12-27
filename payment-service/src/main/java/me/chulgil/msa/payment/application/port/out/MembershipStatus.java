package me.chulgil.msa.payment.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipStatus {
    private String membershipId;
    private boolean isValid;
}
