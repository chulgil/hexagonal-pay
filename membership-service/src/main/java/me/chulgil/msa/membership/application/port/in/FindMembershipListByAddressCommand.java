package me.chulgil.msa.membership.application.port.in;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.chulgil.msa.common.SelfValidating;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FindMembershipListByAddressCommand extends SelfValidating<FindMembershipListByAddressCommand> {

    @NotNull
    private String addressName; // 관악구, 서초구, 강남구

    @Builder
    public FindMembershipListByAddressCommand(@NotNull String addressName) {
        this.addressName = addressName;
        this.validateSelf();
    }
}
