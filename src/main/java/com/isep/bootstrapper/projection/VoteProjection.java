package com.isep.bootstrapper.projection;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.isep.bootstrapper.event.VoteCreatedEvent;
import com.isep.bootstrapper.event.VoteDeletedEvent;
import com.isep.bootstrapper.model.Review;
import com.isep.bootstrapper.model.Vote;
import com.isep.bootstrapper.repository.ReviewRepository;
import com.isep.bootstrapper.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VoteProjection {
    
    private final ReviewRepository reviewRepository;
    private final VoteRepository voteRepository;

    @EventHandler
    public void on(VoteCreatedEvent event){
        Review review = reviewRepository.findById(event.getReviewId()).orElseThrow();
        voteRepository.save(new Vote(
            event.getReviewId(),
            review,
            event.getVoteType(),
            event.getUser()
        ));
    }

    @EventHandler
    public void on(VoteDeletedEvent event){
        voteRepository.deleteById(event.getVoteId());
    }
}
