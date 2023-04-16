package com.isep.bootstrapper.aggregate;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.isep.bootstrapper.enumarate.VoteType;
import com.isep.bootstrapper.event.DefinitiveVoteCreatedEvent;
import com.isep.bootstrapper.event.TemporaryVoteCreatedEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Aggregate
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryVoteAggregate {
    
    @AggregateIdentifier
    private UUID temporaryVoteId;
    private VoteType voteType;
    private String user;

    @CommandHandler
    public TemporaryVoteAggregate(TemporaryVoteCreatedEvent event){
        AggregateLifecycle.apply(event);
    }
    
    @EventSourcingHandler
    public void on(TemporaryVoteCreatedEvent event){
        this.temporaryVoteId = event.getTemporaryVoteId();
        this.voteType = event.getVoteType();
        this.user = event.getUser();
    }

    @CommandHandler
    public void handle(DefinitiveVoteCreatedEvent event){
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(DefinitiveVoteCreatedEvent event){
        AggregateLifecycle.markDeleted();
    }

}
