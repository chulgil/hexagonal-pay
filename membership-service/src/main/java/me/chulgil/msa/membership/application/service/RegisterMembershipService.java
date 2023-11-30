package me.chulgil.msa.membership.application.service;

import me.chulgil.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import me.chulgil.msa.membership.adapter.out.persistence.MembershipMapper;
import me.chulgil.msa.membership.application.port.in.RegisterMembershipCommand;
import me.chulgil.msa.membership.application.port.in.RegisterMembershipUseCase;
import me.chulgil.msa.membership.application.port.out.RegisterMembershipPort;
import me.chulgil.msa.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;
    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
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
