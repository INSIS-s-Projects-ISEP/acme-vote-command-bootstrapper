package com.isep.bootstrapper.dto.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isep.bootstrapper.dto.message.VoteMessage;
import com.isep.bootstrapper.model.Vote;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VoteMapper {

    private final ObjectMapper objectMapper;

    public VoteMessage toMessage(Vote vote){
        return new VoteMessage(
            vote.getVoteId(),
            vote.getReview().getReviewId(),
            vote.getVoteType(),
            vote.getUser()
        );
    }

    public List<VoteMessage> toMessageList(List<Vote> messages){
        return(messages.stream()
            .map(this::toMessage)
            .collect(Collectors.toList())
        );
    }

    public String toJson(List<VoteMessage> messages) throws JsonProcessingException{
        Map<String, List<VoteMessage>> dataMap = new HashMap<>();
        dataMap.put("response", messages);
        return objectMapper.writeValueAsString(dataMap);
    }

}
