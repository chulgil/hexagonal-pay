package me.chulgil.msa.money.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataMemberMoneyRepository extends JpaRepository<MemberMoneyJpaEntity, Long> {

    @Query("select m from MemberMoneyJpaEntity m where m.membershipId = :membershipId")
    List<MemberMoneyJpaEntity> findByMembershipId(@Param("membershipId") Long membershipId);
}
