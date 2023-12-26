package me.chulgil.msa.banking.adapter.axon.command;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegisteredBankAccountCommand {

        private String membershipId;
        private String bankName;
        private String bankAccountNumber;
}
