package me.chulgil.msa.payment.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisteredBankAccountAggregateIdentifier {

    private String registeredBankAccountId;
    private String aggregateIdentifier;
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
}
