package me.chulgil.msa.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;
import lombok.NoArgsConstructor;
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
    public RegisteredBankAccountAggregate(@NotNull CreateRegisteredBankAccountCommand command) {
        apply(CreateRegisteredBankAccountEvent.builder()
                .membershipId(command.getMembershipId())
                .bankName(command.getBankName())
                .bankAccountNumber(command.getBankAccountNumber())
                .build());
    }

    @EventSourcingHandler
    public void on(CreateRegisteredBankAccountEvent event) {
        System.out.println("CreateRegisteredBankAccountEvent Sourcing Handler");
        id = UUID.randomUUID()
                 .toString();
        membershipId = event.getMembershipId();
        bankName = event.getBankName();
        bankAccountNumber = event.getBankAccountNumber();
    }

    @CommandHandler
    public void handle(@NotNull CheckRegisteredBankAccountCommand command,
                       RequestBankAccountInfoPort bankAccountInfoPort) {
        System.out.println("CheckBankAccountCommand Handler");
        // command를 통해 이 어그리거트(RegisteredBankAccount)가 정상인지를 확인

        BankAccount account = bankAccountInfoPort.getBankAccountInfo(GetBankAccountRequest.builder()
                .bankName(command.getBankName())
                .bankAccountNumber(command.getBankAccountNumber())
                .build());

        apply(CheckedRegisteredBankAccountEvent.builder()
                .rechargingRequestId(command.getRechargeRequestId())
                .checkRegisteredBankAccountId(command.getCheckRegisteredBankAccountId())
                .membershipId(command.getMembershipId())
                .isChecked(account.isValid())
                .amount(command.getAmount())
                .firmbankingRequestAggregateIdentifier(UUID.randomUUID()
                                                           .toString())
                .fromBankName(account.getBankName())
                .fromBankAccountNumber(account.getBankAccountNumber())
                .build());
    }
}
