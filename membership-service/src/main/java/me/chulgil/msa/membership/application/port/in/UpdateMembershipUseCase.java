package me.chulgil.msa.membership.application.port.in;

import me.chulgil.msa.membership.domain.Membership;

public interface UpdateMembershipUseCase {

	Membership updateMembership(UpdateMembershipCommand command);
	Membership updateAxonMembership(UpdateMembershipCommand command);
}
