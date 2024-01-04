package me.chulgil.msa.banking.adapter.out.persistence;

import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "registered_bank_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredBankAccountJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registeredBankAccountId;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    private boolean linkStatusIsValid;

    private String aggregateIdentifier;

    @Builder
    public RegisteredBankAccountJpaEntity(String membershipId,
                                          String bankName,
                                          String bankAccountNumber,
                                          boolean linkStatusIsValid,
                                          String aggregateIdentifier) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.linkStatusIsValid = linkStatusIsValid;
        this.aggregateIdentifier = aggregateIdentifier;
    }
}