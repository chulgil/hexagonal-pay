package me.chulgil.msa.membership.application.service;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import me.chulgil.msa.membership.adapter.out.persistence.MembershipMapper;
import me.chulgil.msa.membership.application.port.in.ModifyMembershipCommand;
import me.chulgil.msa.membership.application.port.in.ModifyMembershipUseCase;
import me.chulgil.msa.membership.application.port.out.ModifyMembershipPort;
import me.chulgil.msa.membership.domain.Membership;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {

        // biz logic -> port, adapter -> DB(external system)
        MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
            new Membership.MembershipId(command.getMembershipId()),
            new Membership.MembershipName(command.getName()),
            new Membership.MembershipEmail(command.getEmail()),
            new Membership.MembershipAddress(command.getAddress()),
            new Membership.MembershipIsValid(command.isValid()),
            new Membership.MembershipIsCorp(command.isCorp())
        );
        // entity -> Membership
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }

}
