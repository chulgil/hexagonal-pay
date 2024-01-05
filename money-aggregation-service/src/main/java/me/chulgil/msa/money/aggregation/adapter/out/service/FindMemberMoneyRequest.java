package me.chulgil.msa.money.aggregation.adapter.out.service;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class FindMemberMoneyRequest {
    private List<String> membershipIds;
}
