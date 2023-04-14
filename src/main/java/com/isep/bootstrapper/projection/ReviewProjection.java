package com.isep.bootstrapper.projection;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.isep.bootstrapper.event.ReviewCreatedEvent;
import com.isep.bootstrapper.event.ReviewDeletedEvent;
import com.isep.bootstrapper.event.ReviewUpdatedEvent;
import com.isep.bootstrapper.model.Review;
import com.isep.bootstrapper.repository.ReviewRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewProjection {

    private final ReviewRepository reviewRepository;
    
    @EventHandler
    public void on(ReviewCreatedEvent event){
        reviewRepository.save(new Review(
            event.getReviewId(),
            event.getApprovalStatus()
        ));
    }
    
    @EventHandler
    public void on(ReviewUpdatedEvent event){

        Review review = reviewRepository.findById(event.getReviewId()).orElseThrow();
        review.setReviewId(event.getReviewId());
        review.setApprovalStatus(event.getApprovalStatus());
        reviewRepository.save(review);

    }
    
    @EventHandler
    public void on(ReviewDeletedEvent event){
        reviewRepository.deleteById(event.getReviewId());
    }
    
}
