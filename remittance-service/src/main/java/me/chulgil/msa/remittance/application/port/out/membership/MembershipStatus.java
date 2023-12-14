package me.chulgil.msa.remittance.application.port.out.membership;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipStatus {
    private String membershipId;
    private boolean isValid;
}
