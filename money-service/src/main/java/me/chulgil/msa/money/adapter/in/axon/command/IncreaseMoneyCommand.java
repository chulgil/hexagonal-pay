package me.chulgil.msa.money.adapter.in.axon.command;

import lombok.*;
import me.chulgil.msa.common.SelfValidating;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jetbrains.annotations.NotNull;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyCommand extends SelfValidating<IncreaseMoneyCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    @NotNull
    private String membershipId;

    @NotNull
    private int amount;

    public IncreaseMoneyCommand() {
        // Required by Axon to construct an empty instance to initiate Event Sourcing.
    }

    public IncreaseMoneyCommand(@NotNull String aggregateIdentifier,
                                @NotNull String membershipId,
                                @NotNull int amount) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.membershipId = membershipId;
        this.amount = amount;
        this.validateSelf();
    }
}
