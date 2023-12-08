package me.chulgil.msa.membership.application.port.in;


import me.chulgil.msa.membership.domain.Membership;

public interface ModifyMembershipUseCase {
    Membership modifyMembership(ModifyMembershipCommand command);
}
