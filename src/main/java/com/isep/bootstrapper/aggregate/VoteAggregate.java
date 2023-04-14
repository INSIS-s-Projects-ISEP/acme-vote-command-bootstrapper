package com.isep.bootstrapper.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.isep.bootstrapper.enumarate.VoteType;
import com.isep.bootstrapper.event.VoteCreated;
import com.isep.bootstrapper.event.VoteDeleted;

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
    private Long voteId;
    private Long reviewId;
    private VoteType voteType;
    private String user;

    @CommandHandler
    public VoteAggregate(VoteCreated event){
        AggregateLifecycle.apply(event);
    }
    
    @EventSourcingHandler
    public void on(VoteCreated event){
        this.voteId = event.getVoteId();
        this.reviewId = event.getReviewId();
        this.voteType = event.getVoteType();
        this.user = event.getUser();
    }
    
    @CommandHandler
    public void handle(VoteDeleted event){
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VoteDeleted event){
        AggregateLifecycle.markDeleted();
    }

}
