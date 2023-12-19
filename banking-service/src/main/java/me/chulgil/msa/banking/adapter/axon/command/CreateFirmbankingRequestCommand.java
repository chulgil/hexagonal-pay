package me.chulgil.msa.banking.adapter.axon.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CreateFirmbankingRequestCommand extends SelfValidating<CreateFirmbankingRequestCommand> {

    @NotNull
    private String fromBankName;

    @NotNull
    private String fromBankAccountNumber;

    @NotNull
    private String toBankName;

    @NotNull
    private String toBankAccountNumber;

    @NotNull
    private int moneyAmount;

    @Builder
    public CreateFirmbankingRequestCommand(@NotNull String fromBankName,
                                           @NotNull String fromBankAccountNumber,
                                           @NotNull String toBankName,
                                           @NotNull String toBankAccountNumber,
                                           @NotNull int moneyAmount) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.validateSelf();
    }
}
