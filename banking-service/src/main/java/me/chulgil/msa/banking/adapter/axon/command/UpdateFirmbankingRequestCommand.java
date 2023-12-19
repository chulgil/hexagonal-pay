package me.chulgil.msa.banking.adapter.axon.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.chulgil.msa.common.SelfValidating;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UpdateFirmbankingRequestCommand extends SelfValidating<UpdateFirmbankingRequestCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    @NotNull
    private int firmbankingStatus;

    @Builder
    public UpdateFirmbankingRequestCommand(@NotNull String aggregateIdentifier,
                                           @NotNull int firmbankingStatus) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.firmbankingStatus = firmbankingStatus;
        this.validateSelf();
    }
}
