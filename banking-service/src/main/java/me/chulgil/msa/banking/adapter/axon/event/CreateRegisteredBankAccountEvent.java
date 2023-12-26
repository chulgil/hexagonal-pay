package me.chulgil.msa.banking.adapter.axon.event;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegisteredBankAccountEvent {

        private String membershipId;
        private String bankName;
        private String bankAccountNumber;
}
