package me.chulgil.msa.membership.application.port.in;

import java.util.List;
import me.chulgil.msa.membership.domain.Membership;

public interface FindMembershipUseCase {
	Membership findMembership(FindMembershipCommand command);

    List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command);
}
