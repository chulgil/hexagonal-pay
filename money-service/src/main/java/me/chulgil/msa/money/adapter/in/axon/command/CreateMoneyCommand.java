package me.chulgil.msa.money.adapter.in.axon.command;

import lombok.*;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CreateMoneyCommand extends SelfValidating<CreateMoneyCommand> {

    @NotNull
    private String membershipId;

    public CreateMoneyCommand() {
        // Required by Axon to construct an empty instance to initiate Event Sourcing.
    }

    @Builder
    public CreateMoneyCommand(@NotNull String membershipId) {
        this.membershipId = membershipId;
        this.validateSelf();
    }
}
