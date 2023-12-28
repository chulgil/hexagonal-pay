package me.chulgil.msa.money.aggreagation.adapter.out.service;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMemberMoneyRequest {

    private List<String> membershipIds;
}
