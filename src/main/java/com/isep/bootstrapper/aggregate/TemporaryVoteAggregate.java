package com.isep.bootstrapper.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.isep.bootstrapper.enumarate.VoteType;
import com.isep.bootstrapper.event.DefinitiveVoteCreated;
import com.isep.bootstrapper.event.TemporaryVoteCreated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryVoteAggregate {
    
    @TargetAggregateIdentifier
    private Long temporaryVoteId;
    private VoteType voteType;
    private String user;

    @CommandHandler
    public TemporaryVoteAggregate(TemporaryVoteCreated event){
        AggregateLifecycle.apply(event);
    }
    
    @EventSourcingHandler
    public void on(TemporaryVoteCreated event){
        this.temporaryVoteId = event.getTemporaryVoteId();
        this.voteType = event.getVoteType();
        this.user = event.getUser();
    }

    @CommandHandler
    public void handle(DefinitiveVoteCreated event){
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(DefinitiveVoteCreated event){
        AggregateLifecycle.markDeleted();
    }

}
