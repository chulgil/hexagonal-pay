package me.chulgil.msa.membership.adapter.out.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.PersistenceAdapter;
import me.chulgil.msa.membership.application.port.out.FindMembershipPort;
import me.chulgil.msa.membership.application.port.out.ModifyMembershipPort;
import me.chulgil.msa.membership.application.port.out.RegisterMembershipPort;
import me.chulgil.msa.membership.domain.Membership;
import me.chulgil.msa.membership.domain.Membership.MembershipAddress;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository repository;

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        return repository.getById(Long.parseLong(membershipId.getValue()));
    }

    @Override
    public List<MembershipJpaEntity> findMembershipListByAddress(MembershipAddress membershipAddress) {
        // 관악구, 서초구, 강남구 중 하나
        String address = membershipAddress.getValue();
        return repository.findByAddress(address);
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId,
                                                Membership.MembershipName membershipName,
                                                Membership.MembershipEmail membershipEmail,
                                                Membership.MembershipAddress membershipAddress,
                                                Membership.MembershipIsValid membershipIsValid,
                                                Membership.MembershipIsCorp membershipIsCorp) {


        MembershipJpaEntity entity = repository.getById(Long.parseLong(membershipId.getValue()));
        entity.setName(membershipName.getValue());
        entity.setEmail(membershipEmail.getValue());
        entity.setAddress(membershipAddress.getValue());
        entity.setValid(membershipIsValid.isValue());
        entity.setCorp(membershipIsCorp.isValue());

        return repository.save(entity);
    }

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName,
                                                Membership.MembershipEmail membershipEmail,
                                                Membership.MembershipAddress membershipAddress,
                                                Membership.MembershipIsValid membershipIsValid,
                                                Membership.MembershipIsCorp membershipIsCorp) {
        return repository.save(MembershipJpaEntity.builder()
            .name(membershipName.getValue())
            .email(membershipEmail.getValue())
            .address(membershipAddress.getValue())
            .isValid(membershipIsValid.isValue())
            .isCorp(membershipIsCorp.isValue())
            .build());
    }
}
