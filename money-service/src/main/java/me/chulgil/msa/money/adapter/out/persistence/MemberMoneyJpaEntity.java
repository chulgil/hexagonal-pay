package me.chulgil.msa.money.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member_money")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyJpaEntity {

    @Id
    @GeneratedValue
    private Long memberMoneyId;

    private Long membershipId;

    private int balance;

    @Builder
    public MemberMoneyJpaEntity(Long membershipId, int balance) {
        this.membershipId = membershipId;
        this.balance = balance;
    }

}
