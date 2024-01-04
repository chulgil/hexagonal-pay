package me.chulgil.msa.money.adapter.out.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "member_money")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberMoneyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long membershipId;

    @Setter
    private int balance;

    private String aggregateIdentifier;

}
