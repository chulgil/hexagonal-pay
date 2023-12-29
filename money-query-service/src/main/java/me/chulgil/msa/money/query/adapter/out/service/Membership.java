package me.chulgil.msa.money.query.adapter.out.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Membership {

    private String membershipId;
    private String name;
    private String email;
    private String address;
    private boolean isValid;
    private boolean isCorp;
}
