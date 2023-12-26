package me.chulgil.msa.banking.adapter.axon.command;

import lombok.*;
import me.chulgil.msa.common.SelfValidating;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class UpdateFirmbankingRequestCommand extends SelfValidating<UpdateFirmbankingRequestCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private int firmbankingStatus;

    @Builder
    public UpdateFirmbankingRequestCommand(@NotNull String aggregateIdentifier,
                                           int firmbankingStatus) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.firmbankingStatus = firmbankingStatus;
        this.validateSelf();
    }
}
