package me.chulgil.msa.membership.application.port.in;


import me.chulgil.msa.common.SelfValidating;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMembershipEventCommand extends SelfValidating<UpdateMembershipEventCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;
    private String name;

    private String address;

    private String email;

    private boolean isValid;
}
