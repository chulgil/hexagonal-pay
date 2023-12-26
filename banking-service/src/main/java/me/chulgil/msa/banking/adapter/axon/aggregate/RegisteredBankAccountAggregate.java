package me.chulgil.msa.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.chulgil.msa.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import me.chulgil.msa.banking.adapter.axon.event.CreateRegisteredBankAccountEvent;
import me.chulgil.msa.banking.adapter.out.external.bank.BankAccount;
import me.chulgil.msa.banking.adapter.out.external.bank.GetBankAccountRequest;
import me.chulgil.msa.banking.application.port.out.RequestBankAccountInfoPort;
import me.chulgil.msa.common.event.CheckRegisteredBankAccountCommand;
import me.chulgil.msa.common.event.CheckedRegisteredBankAccountEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.jetbrains.annotations.NotNull;

@Aggregate()
@NoArgsConstructor
public class RegisteredBankAccountAggregate {

    @AggregateIdentifier
    private String id;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    @CommandHandler
    public RegisteredBankAccountAggregate(CreateRegisteredBankAccountCommand command) {
        System.out.println("CreateRegisteredBankAccountCommand Sourcing Handler");
        apply(CreateRegisteredBankAccountEvent.builder()
                .membershipId(command.getMembershipId())
                .bankName(command.getBankName())
                .bankAccountNumber(command.getBankAccountNumber())
                .build());
    }

    @CommandHandler
    public void handle(@NotNull CheckRegisteredBankAccountCommand command,
                       RequestBankAccountInfoPort bankAccountInfoPort) {
        System.out.println("CheckRegisteredBankAccountCommand Handler");

        // command를 통해 이 어그리거트(RegisteredBankAccount)가 정상인지를 확인
        this.id = command.getAggregateIdentifier();

        BankAccount account = bankAccountInfoPort.getBankAccountInfo(GetBankAccountRequest.builder()
                .bankName(command.getBankName())
                .bankAccountNumber(command.getBankAccountNumber())
                .build());

        String firmbankingUUID = UUID.randomUUID().toString();
        apply(CheckedRegisteredBankAccountEvent.builder()
                .rechargingRequestId(command.getRechargeRequestId())
                .checkRegisteredBankAccountId(command.getCheckRegisteredBankAccountId())
                .membershipId(command.getMembershipId())
                .isChecked(account.isValid())
                .amount(command.getAmount())
                .firmbankingRequestAggregateIdentifier(firmbankingUUID)
                .fromBankName(account.getBankName())
                .fromBankAccountNumber(account.getBankAccountNumber())
                .build());
    }

    @EventSourcingHandler
    public void on(CreateRegisteredBankAccountEvent event) {
        System.out.println("CreateRegisteredBankAccountEvent Sourcing Handler");
        this.id = UUID.randomUUID().toString();
        this.membershipId = event.getMembershipId();
        this.bankName = event.getBankName();
        this.bankAccountNumber = event.getBankAccountNumber();
    }
}
