package me.chulgil.msa.money.query.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressInfo {

    String membershipId;
    String address;
}
