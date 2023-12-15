package me.chulgil.msa.money.adapter.in.axon.aggregate;

import lombok.Getter;
import me.chulgil.msa.money.adapter.in.axon.command.CreateMoneyCommand;
import me.chulgil.msa.money.adapter.in.axon.event.MemberMoneyCreateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Table(name = "member_money")
@Aggregate()
@Getter
public class MemberMoneyAggregate {

    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;


    @CommandHandler
    public MemberMoneyAggregate(@NotNull CreateMoneyCommand command) {
        System.out.println("CreateMoneyCommand Handler");
        // store event
        apply(new MemberMoneyCreateEvent(command.getMembershipId()));
    }


}
