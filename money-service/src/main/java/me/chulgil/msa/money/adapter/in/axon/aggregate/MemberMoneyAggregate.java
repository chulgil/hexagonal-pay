package me.chulgil.msa.money.adapter.in.axon.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.chulgil.msa.money.adapter.in.axon.command.CreateMoneyCommand;
import me.chulgil.msa.money.adapter.in.axon.command.IncreaseMoneyCommand;
import me.chulgil.msa.money.adapter.in.axon.event.CreateMoneyEvent;
import me.chulgil.msa.money.adapter.in.axon.event.IncreaseMoneyEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Table(name = "member_money")
@Aggregate()
@Getter
@NoArgsConstructor
public class MemberMoneyAggregate {

    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;


    @CommandHandler
    public MemberMoneyAggregate(@NotNull CreateMoneyCommand command) {
        System.out.println("CreateMoneyCommand Handler");
        // store event
        apply(new CreateMoneyEvent(command.getMembershipId()));
    }

    @EventSourcingHandler
    public void on(CreateMoneyEvent event) {
        System.out.println("MemberMoneyCreateEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        this.membershipId = Long.parseLong(event.getMembershipId());
        this.balance = 0;
    }

    @CommandHandler
    public String handle(@NotNull IncreaseMoneyCommand command) {
        System.out.println("IncreaseMoneyCommand Handler");
        this.id = command.getAggregateIdentifier();

        // store event
        apply(new IncreaseMoneyEvent(id, command.getMembershipId(), command.getAmount()));
        return id;
    }

    @EventSourcingHandler
    public void on(IncreaseMoneyEvent event) {
        System.out.println("IncreaseMoneyEvent Sourcing Handler");
        this.id = event.getAggregateIdentifier();
        this.membershipId = Long.parseLong(event.getTargetMembershipId());
        this.balance = event.getAmount();
    }
}
