package me.chulgil.msa.membership.application.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import me.chulgil.msa.membership.adapter.out.persistence.MembershipMapper;
import me.chulgil.msa.membership.application.port.in.FindMembershipCommand;
import me.chulgil.msa.membership.application.port.in.FindMembershipListByAddressCommand;
import me.chulgil.msa.membership.application.port.in.FindMembershipUseCase;
import me.chulgil.msa.membership.application.port.out.FindMembershipPort;
import me.chulgil.msa.membership.domain.Membership;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;

    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {
        MembershipJpaEntity entity = findMembershipPort.findMembership(
                new Membership.MembershipId(command.getMembershipId()));
        return membershipMapper.mapToDomainEntity(entity);
    }

    @Override
    public List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command) {
        List<MembershipJpaEntity> membershipJpaEntities = findMembershipPort.findMembershipListByAddress(
                new Membership.MembershipAddress(command.getAddressName()));
        List<Membership> memberships = new ArrayList<>();

        for (MembershipJpaEntity membershipJpaEntity : membershipJpaEntities) {
            memberships.add(membershipMapper.mapToDomainEntity(membershipJpaEntity));
        }
        return memberships;
    }
}