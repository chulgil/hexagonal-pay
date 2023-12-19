package me.chulgil.msa.banking.adapter.axon.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.chulgil.msa.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import me.chulgil.msa.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import me.chulgil.msa.banking.adapter.axon.event.FirmbankingRequestCreatedEvent;
import me.chulgil.msa.banking.adapter.axon.event.FirmbankingRequestUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
@Getter
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

//    @CommandHandler
//    public FirmbankingRequestAggregate(me.chulgil.msa.common.event.RequestFirmbankingCommand command,
//                                       RequestFirmbankingPort firmbankingPort,
//                                       RequestExternalFirmbankingPort externalFirmbankingPort) {
//        System.out.println("FirmbankingRequestAggregate Handler");
//        id = command.getAggregateIdentifier();
//
//        // from -> to
//        // 펌뱅킹 수행!
//        firmbankingPort.createFirmbankingRequest(
//            new FirmbankingRequest.FromBankName(command.getToBankName()),
//            new FirmbankingRequest.FromBankAccountNumber(command.getToBankAccountNumber()),
//            new FirmbankingRequest.ToBankName("fastcampus-bank"),
//            new FirmbankingRequest.ToBankAccountNumber("123-333-9999"),
//            new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
//            new FirmbankingRequest.FirmbankingStatus(0),
//            new FirmbankingRequest.FirmbankingAggregateIdentifier(id)
//        );
//
//        // 외부 펌뱅킹 요청
//        externalFirmbankingPort.requestFirmbanking(
//            new ExternalFirmbankingRequest.FromBankName(command.getFromBankName()),
//            new ExternalFirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
//            new ExternalFirmbankingRequest.ToBankName(command.getToBankName()),
//            new ExternalFirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
//            new ExternalFirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
//            new ExternalFirmbankingRequest.FirmbankingAggregateIdentifier(id)
//        );
//    }

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
        System.out.println("FirmbankingRequestCreatedEvent Handler");

        this.id = UUID.randomUUID()
            .toString();
        this.fromBankName = event.getFromBankName();
        this.fromBankAccountNumber = event.getFromBankAccountNumber();
        this.toBankName = event.getToBankName();
        this.toBankAccountNumber = event.getToBankAccountNumber();
        this.moneyAmount = event.getMoneyAmount();
        this.firmbankingStatus = 0;
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestUpdateEvent event) {
        System.out.println("FirmbankingRequestUpdateEvent Sourcing Handler");
        firmbankingStatus = event.getFirmbankingStatus();
    }
}
