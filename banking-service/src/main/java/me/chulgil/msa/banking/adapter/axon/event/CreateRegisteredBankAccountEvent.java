package me.chulgil.msa.banking.adapter.axon.event;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateRegisteredBankAccountEvent {

        private String membershipId;
        private String bankName;
        private String bankAccountNumber;
}
