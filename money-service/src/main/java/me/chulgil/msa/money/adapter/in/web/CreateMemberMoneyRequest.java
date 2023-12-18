package me.chulgil.msa.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMemberMoneyRequest {
    // 어떤 고객의 지갑 정보를 만들 것 인지만 필요
    private String targetMembershipId;
}
