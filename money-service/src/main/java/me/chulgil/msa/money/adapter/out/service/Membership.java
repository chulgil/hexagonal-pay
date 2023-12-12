package me.chulgil.msa.money.adapter.out.service;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 뱅킹서비스를 위한 Membership 정보
 * 주의 : 도메인의 Membership과 다름
 */
@Data
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
