package me.chulgil.msa.money.query.application.port.in;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode(callSuper = false)
public class QueryMoneySumByRegionQuery extends SelfValidating<QueryMoneySumByRegionQuery> {
    @NotNull
    private final String address;

    @Builder
    public QueryMoneySumByRegionQuery(@NotNull String address) {
        this.address = address;
        this.validateSelf();
    }
}
