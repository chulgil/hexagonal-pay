package me.chulgil.msa.membership.application.port.out;

import java.util.List;
import me.chulgil.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import me.chulgil.msa.membership.domain.Membership;
import me.chulgil.msa.membership.domain.Membership.MembershipAddress;

public interface FindMembershipPort {
    MembershipJpaEntity findMembership(Membership.MembershipId membershipId);

    List<MembershipJpaEntity> findMembershipListByAddress(MembershipAddress membershipAddress);
}
