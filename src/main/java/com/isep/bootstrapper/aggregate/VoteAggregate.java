package com.isep.bootstrapper.aggregate;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.isep.bootstrapper.enumarate.VoteType;
import com.isep.bootstrapper.event.VoteCreatedEvent;
import com.isep.bootstrapper.event.VoteDeletedEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Aggregate
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteAggregate {

    @AggregateIdentifier
    private UUID voteId;
    private UUID reviewId;
    private VoteType voteType;
    private String user;

    @CommandHandler
    public VoteAggregate(VoteCreatedEvent event){
        AggregateLifecycle.apply(event);
    }
    
    @EventSourcingHandler
    public void on(VoteCreatedEvent event){
        this.voteId = event.getVoteId();
        this.reviewId = event.getReviewId();
        this.voteType = event.getVoteType();
        this.user = event.getUser();
    }
    
    @CommandHandler
    public void handle(VoteDeletedEvent event){
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VoteDeletedEvent event){
        AggregateLifecycle.markDeleted();
    }

}
