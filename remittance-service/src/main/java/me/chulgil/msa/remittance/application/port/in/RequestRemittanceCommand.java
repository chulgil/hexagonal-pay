package me.chulgil.msa.remittance.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chulgil.msa.common.SelfValidating;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
public class RequestRemittanceCommand extends SelfValidating<RequestRemittanceCommand> {

    @NotNull
    private String fromMembershipId; // from membership

    private String toMembershipId; // to membership

    private String toBankName;

    private String toBankAccountNumber;

    private int remittanceType; // 0: membership(내부 고객), 1: bank (외부 은행 계좌)


    // 송금요청 금액
    @NotNull
    @NotBlank
    private int amount;


    @Builder
    public RequestRemittanceCommand(String fromMembershipId,
                                    String toMembershipId,
                                    String toBankName,
                                    String toBankAccountNumber,
                                    int remittanceType,
                                    int amount) {
        this.fromMembershipId = fromMembershipId;
        this.toMembershipId = toMembershipId;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.remittanceType = remittanceType;
        this.amount = amount;

        this.validateSelf();
    }
}