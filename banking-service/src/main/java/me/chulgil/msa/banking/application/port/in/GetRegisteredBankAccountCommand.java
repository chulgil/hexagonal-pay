package me.chulgil.msa.banking.application.port.in;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.chulgil.msa.common.SelfValidating;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetRegisteredBankAccountCommand extends SelfValidating<GetRegisteredBankAccountCommand> {

    private final String membershipId;

    @Builder
    public GetRegisteredBankAccountCommand(String membershipId) {
        this.membershipId = membershipId;
        this.validateSelf();
    }
}
