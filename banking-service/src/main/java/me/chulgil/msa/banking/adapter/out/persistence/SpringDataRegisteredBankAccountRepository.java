package me.chulgil.msa.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataRegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountJpaEntity, Long> {

    @Query("SELECT e  FROM RegisteredBankAccountJpaEntity e WHERE e.membershipId = :membershipId")
    List<RegisteredBankAccountJpaEntity> findByMembershipoId(@Param("membershipId") String membershipId);
}
