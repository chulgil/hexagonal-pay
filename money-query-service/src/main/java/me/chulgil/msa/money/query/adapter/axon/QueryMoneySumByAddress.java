package me.chulgil.msa.money.query.adapter.axon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryMoneySumByAddress {
    private String address;
}
