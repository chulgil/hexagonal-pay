package me.chulgil.msa.banking.application.port.in;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmbankingCommand extends SelfValidating<RequestFirmbankingCommand> {

    @NotNull
    private final String firmbankingAggregateIdentifier;

    @NotNull
    private final int firmbankingStatus;
}