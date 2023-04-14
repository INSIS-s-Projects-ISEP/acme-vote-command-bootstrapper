package com.isep.bootstrapper.event;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.isep.bootstrapper.enumarate.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteCreated {
    
    @TargetAggregateIdentifier
    private Long voteId;
    private Long reviewId;
    private VoteType voteType;
    private String user;
    
}
