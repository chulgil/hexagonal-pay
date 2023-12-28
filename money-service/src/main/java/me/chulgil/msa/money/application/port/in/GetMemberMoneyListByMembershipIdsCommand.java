package me.chulgil.msa.money.application.port.in;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class GetMemberMoneyListByMembershipIdsCommand extends
        SelfValidating<GetMemberMoneyListByMembershipIdsCommand> {

    @NotNull
    private final List<String> membershipIds;

    public GetMemberMoneyListByMembershipIdsCommand(@NotNull List<String> membershipIds) {
        this.membershipIds = membershipIds;
        this.validateSelf();
    }

}
