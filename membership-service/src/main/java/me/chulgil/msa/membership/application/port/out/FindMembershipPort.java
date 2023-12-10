package me.chulgil.msa.membership.application.port.out;

import me.chulgil.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import me.chulgil.msa.membership.domain.Membership;

public interface FindMembershipPort {
    MembershipJpaEntity findMembership(Membership.MembershipId membershipId);
}
