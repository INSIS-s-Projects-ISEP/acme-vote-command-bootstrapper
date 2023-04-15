package com.isep.bootstrapper.aggregate;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.isep.bootstrapper.enumarate.ApprovalStatus;
import com.isep.bootstrapper.event.ReviewCreatedEvent;
import com.isep.bootstrapper.event.ReviewDeletedEvent;
import com.isep.bootstrapper.event.ReviewUpdatedEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Aggregate
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAggregate {
    
    @AggregateIdentifier
    private UUID reviewId;
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @CommandHandler
    public ReviewAggregate(ReviewCreatedEvent event) {
        AggregateLifecycle.apply(event);
    }
    
    @EventSourcingHandler
    public void on(ReviewCreatedEvent event){
        this.reviewId = event.getReviewId();
        this.approvalStatus = event.getApprovalStatus();
    }
    
    @CommandHandler
    public void handle(ReviewUpdatedEvent event){
        AggregateLifecycle.apply(event);
    }
    
    @EventSourcingHandler
    public void on(ReviewUpdatedEvent event){
        this.reviewId = event.getReviewId();
        this.approvalStatus = event.getApprovalStatus();
    }

    @CommandHandler
    public void handle(ReviewDeletedEvent event){
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ReviewDeletedEvent event){
        AggregateLifecycle.markDeleted();
    }
    
}
