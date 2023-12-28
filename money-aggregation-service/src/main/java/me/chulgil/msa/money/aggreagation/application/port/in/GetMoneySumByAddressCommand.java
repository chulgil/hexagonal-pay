package me.chulgil.msa.money.aggreagation.application.port.in;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetMoneySumByAddressCommand extends SelfValidating<GetMoneySumByAddressCommand> {

    @NotNull
    private final String address;

    @Builder
    public GetMoneySumByAddressCommand(@NotNull String address) {
        this.address = address;
        this.validateSelf();
    }

}
