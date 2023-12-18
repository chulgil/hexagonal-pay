package me.chulgil.msa.money.adapter.in.axon.event;

import lombok.*;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CreateMoneyEvent extends SelfValidating<CreateMoneyEvent> {

    @NotNull
    private String membershipId;

    public CreateMoneyEvent() {
        // Required by Axon to construct an empty instance to initiate Event Sourcing.
    }

    @Builder
    public CreateMoneyEvent(@NotNull String targetMembershipId) {
        this.membershipId = targetMembershipId;
        this.validateSelf();
    }
}
