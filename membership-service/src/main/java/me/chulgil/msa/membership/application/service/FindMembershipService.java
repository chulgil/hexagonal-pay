package me.chulgil.msa.membership.application.service;

import me.chulgil.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import me.chulgil.msa.membership.adapter.out.persistence.MembershipMapper;
import me.chulgil.msa.membership.application.port.in.FindMembershipCommand;
import me.chulgil.msa.membership.application.port.in.FindMembershipUseCase;
import me.chulgil.msa.membership.application.port.out.FindMembershipPort;
import me.chulgil.msa.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;

    private final MembershipMapper membershipMapper;
    @Override
    public Membership findMembership(FindMembershipCommand command) {
        MembershipJpaEntity entity = findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));
        return membershipMapper.mapToDomainEntity(entity);
    }
}