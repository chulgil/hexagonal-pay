package me.chulgil.msa.money.adapter.in.axon.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.chulgil.msa.common.SelfValidating;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RechargingMoneyRequestCreateCommand extends SelfValidating<RechargingMoneyRequestCreateCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier; // RechargingMoneyRequest의 aggregateIdentifier

    @NotNull
    private String rechargingRequestId; // 충전 요청 ID

    @NotNull
    private String membershipId; // 고객의 ID

    @NotNull
    private int amount; // 충전 요청 금액

    @Builder
    public RechargingMoneyRequestCreateCommand(@NotNull String aggregateIdentifier,
                                               @NotNull String rechargingRequestId,
                                               @NotNull String membershipId,
                                               @NotNull int amount) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.rechargingRequestId = rechargingRequestId;
        this.membershipId = membershipId;
        this.amount = amount;
        this.validateSelf();
    }
}
