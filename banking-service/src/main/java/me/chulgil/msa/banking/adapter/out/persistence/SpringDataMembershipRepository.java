package me.chulgil.msa.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMembershipRepository extends JpaRepository<RegisteredBankAccountJpaEntity, Long> {
}
