package me.chulgil.msa.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.chulgil.msa.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import me.chulgil.msa.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import me.chulgil.msa.banking.adapter.axon.event.FirmbankingRequestCreatedEvent;
import me.chulgil.msa.banking.adapter.axon.event.FirmbankingRequestUpdateEvent;
import me.chulgil.msa.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import me.chulgil.msa.banking.adapter.out.external.bank.FirmbankingResult;
import me.chulgil.msa.banking.application.port.out.RequestExternalFirmbankingPort;
import me.chulgil.msa.banking.application.port.out.RequestFirmbankingPort;
import me.chulgil.msa.banking.domain.FirmbankingRequest;
import me.chulgil.msa.common.event.RequestFirmbankingCommand;
import me.chulgil.msa.common.event.RequestFirmbankingFinishedEvent;
import me.chulgil.msa.common.event.RollbackFirmbankingFinishedEvent;
import me.chulgil.msa.common.event.RollbackFirmbankingRequestCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;

@Aggregate()
@Data
@NoArgsConstructor
public class FirmbankingRequestAggregate {

    @AggregateIdentifier
    private String id;

    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount;
    private int firmbankingStatus;

    @CommandHandler
    public FirmbankingRequestAggregate(CreateFirmbankingRequestCommand command) {
        System.out.println("CreateFirmbankingRequestCommand Handler");
        apply(FirmbankingRequestCreatedEvent.builder()
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .moneyAmount(command.getMoneyAmount())
                .build());
    }

    @CommandHandler
    public FirmbankingRequestAggregate(RequestFirmbankingCommand command,
                                       RequestFirmbankingPort firmbankingPort,
                                       RequestExternalFirmbankingPort externalFirmbankingPort) {
        System.out.println("FirmbankingRequestAggregate Handler");
        id = command.getAggregateIdentifier();

        // from -> to
        // 펌뱅킹 수행!
        firmbankingPort.createFirmbankingRequest(
            new FirmbankingRequest.FromBankName(command.getToBankName()),
            new FirmbankingRequest.FromBankAccountNumber(command.getToBankAccountNumber()),
            new FirmbankingRequest.ToBankName("test2"),
            new FirmbankingRequest.ToBankAccountNumber("test2"),
            new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
            new FirmbankingRequest.FirmbankingStatus(0),
            new FirmbankingRequest.FirmbankingAggregateIdentifier(id)
        );

        // 외부 펌뱅킹 요청
        FirmbankingResult firmbankingResult = externalFirmbankingPort.requestExternalFirmbanking(
            new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount()
            ));

        int resultCode = firmbankingResult.getResultCode();

        // 0. 성공, 1. 실패
        apply(new RequestFirmbankingFinishedEvent(
            command.getRequestFirmbankingId(),
            command.getRechargeRequestId(),
            command.getMembershipId(),
            command.getToBankName(),
            command.getToBankAccountNumber(),
            command.getMoneyAmount(),
            resultCode,
            id
        ));
    }

    @CommandHandler
    public FirmbankingRequestAggregate(@NotNull RollbackFirmbankingRequestCommand command,
                                       RequestFirmbankingPort firmbankingPort,
                                       RequestExternalFirmbankingPort externalFirmbankingPort) {
        System.out.println("RollbackFirmbankingRequestCommand Handler");
        id = UUID.randomUUID().toString();

        // rollback 수행 (-> 법인 계좌 -> 고객 계좌 펌뱅킹)
        firmbankingPort.createFirmbankingRequest(
            new FirmbankingRequest.FromBankName("test1"),
            new FirmbankingRequest.FromBankAccountNumber("test1"),
            new FirmbankingRequest.ToBankName(command.getBankName()),
            new FirmbankingRequest.ToBankAccountNumber(command.getBankAccountNumber()),
            new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
            new FirmbankingRequest.FirmbankingStatus(0),
            new FirmbankingRequest.FirmbankingAggregateIdentifier(id));

        // firmbanking!
        FirmbankingResult result = externalFirmbankingPort.requestExternalFirmbanking(
            new ExternalFirmbankingRequest(
                "test1",
                "test1",
                command.getBankName(),
                command.getBankAccountNumber(),
                command.getMoneyAmount()
            ));

        int res = result.getResultCode();

        apply(new RollbackFirmbankingFinishedEvent(
            command.getRollbackFirmbankingId(),
            command.getMembershipId(),
            id)
        );
    }

    @CommandHandler
    public String handle(UpdateFirmbankingRequestCommand command) {
        System.out.println("UpdateFirmbankingRequestCommand Handler");
        this.id = command.getAggregateIdentifier();
        apply(FirmbankingRequestUpdateEvent.builder()
                .firmbankingStatus(command.getFirmbankingStatus())
                .build());

        return this.id;
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestCreatedEvent event) {
        System.out.println("FirmbankingRequestCreatedEvent Sourcing Handler");
        this.id = UUID.randomUUID().toString();
        this.fromBankName = event.getFromBankName();
        this.fromBankAccountNumber = event.getFromBankAccountNumber();
        this.toBankName = event.getToBankName();
        this.toBankAccountNumber = event.getToBankAccountNumber();
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestUpdateEvent event) {
        System.out.println("FirmbankingRequestUpdateEvent Sourcing Handler");
        firmbankingStatus = event.getFirmbankingStatus();
    }
}
