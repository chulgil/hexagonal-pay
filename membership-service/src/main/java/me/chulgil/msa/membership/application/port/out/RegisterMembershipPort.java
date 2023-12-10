package me.chulgil.msa.membership.application.port.out;

import me.chulgil.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import me.chulgil.msa.membership.domain.Membership;

public interface RegisterMembershipPort {
    MembershipJpaEntity createMembership(Membership.MembershipName membershipName,
                                         Membership.MembershipEmail membershipEmail,
                                         Membership.MembershipAddress membershipAddress,
                                         Membership.MembershipIsValid membershipIsValid,
                                         Membership.MembershipIsCorp membershipIsCorp);

}
