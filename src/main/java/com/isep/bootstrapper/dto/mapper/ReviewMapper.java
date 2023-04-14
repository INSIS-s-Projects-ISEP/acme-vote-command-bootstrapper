package com.isep.bootstrapper.dto.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isep.bootstrapper.dto.message.ReviewMessage;
import com.isep.bootstrapper.model.Review;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewMapper {
    
    private final ObjectMapper objectMapper;

    public Review toEntity(ReviewMessage reviewMessage){
        return new Review(
            reviewMessage.getIdReview(),
            reviewMessage.getApprovalStatus()
        );
    }

    public ReviewMessage toMessage(Review review){
        return new ReviewMessage(
            review.getReviewId(),
            review.getApprovalStatus()
        );
    }

    public List<ReviewMessage> toMessageList(List<Review> reviews){
        return (reviews.stream()
            .map(this::toMessage)
            .collect(Collectors.toList())
        );
    }

    public String toJson(List<ReviewMessage> messages) throws JsonProcessingException{
        Map<String, List<ReviewMessage>> dataMap = new HashMap<>();
        dataMap.put("response", messages);
        return objectMapper.writeValueAsString(dataMap);
    }
}
