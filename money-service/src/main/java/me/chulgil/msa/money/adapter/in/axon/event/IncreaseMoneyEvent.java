package me.chulgil.msa.money.adapter.in.axon.event;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyEvent extends SelfValidating<IncreaseMoneyEvent> {

    @NotNull
    private final String aggregateIdentifier;

    @NotNull
    private final String targetMembershipId;

    @NotNull
    private final int amount;

    @Builder
    public IncreaseMoneyEvent(@NotNull String aggregateIdentifier,
                              @NotNull String targetMembershipId,
                              @NotNull int amount) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;
        this.validateSelf();
    }
}
