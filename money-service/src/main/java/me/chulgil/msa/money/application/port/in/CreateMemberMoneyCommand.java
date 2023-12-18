package me.chulgil.msa.money.application.port.in;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;


@Getter
@EqualsAndHashCode(callSuper = false)
public class CreateMemberMoneyCommand extends SelfValidating<CreateMemberMoneyCommand> {

    @NotNull
    private final String targetMemebershipId;

    @Builder
    public CreateMemberMoneyCommand(@NotNull String targetMembershipId) {
        this.targetMemebershipId = targetMembershipId;
        this.validateSelf();
    }

}
