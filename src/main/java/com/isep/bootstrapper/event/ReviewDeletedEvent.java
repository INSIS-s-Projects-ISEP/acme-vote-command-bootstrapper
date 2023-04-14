package com.isep.bootstrapper.event;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDeletedEvent {
    
    @TargetAggregateIdentifier
    private Long reviewId;
    
}
