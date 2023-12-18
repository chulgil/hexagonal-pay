package me.chulgil.msa.money.adapter.out.persistence;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "member_money")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberMoneyJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long membershipId;

    @Setter
    private int balance;

    private String aggregateIdentifier;

}
