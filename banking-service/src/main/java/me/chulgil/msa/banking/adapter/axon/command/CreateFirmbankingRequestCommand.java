package me.chulgil.msa.banking.adapter.axon.command;

import lombok.*;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateFirmbankingRequestCommand {

    private String fromBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;

    private int moneyAmount;

}
