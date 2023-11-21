package me.chulgil.msa.membership.application.port.in;


import me.chulgil.msa.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateMembershipCommand extends SelfValidating<UpdateMembershipCommand> {
    @NotNull
    @TargetAggregateIdentifier
    private String membershipId;

    private String name;

    private String address;

    private String email;

    private boolean isValid;
}
