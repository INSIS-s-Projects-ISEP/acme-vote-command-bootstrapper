package com.isep.bootstrapper.dto.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isep.bootstrapper.dto.message.TemporaryVoteMessage;
import com.isep.bootstrapper.model.TemporaryVote;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TemporaryVoteMapper {

    private final ObjectMapper objectMapper;
    
    // public TemporaryVote toEntity(TemporaryVoteRequest temporaryVoteRequest){
    //     TemporaryVote temporaryVote = new TemporaryVote();
    //     temporaryVote.setUser(temporaryVoteRequest.getUser());
    //     temporaryVote.setVoteType(temporaryVoteRequest.getVoteType());
    //     return temporaryVote;
    // }

    // public TemporaryVote toEntity(TemporaryVoteMessage temporaryVoteMessage){
    //     return new TemporaryVote(
    //         temporaryVoteMessage.getTemporaryVoteId(),
    //         temporaryVoteMessage.getUser(),
    //         temporaryVoteMessage.getVoteType()
    //     );
    // }

    public TemporaryVoteMessage toMessage(TemporaryVote temporaryVote){
        return new TemporaryVoteMessage(
            temporaryVote.getTemporaryVoteId(),
            temporaryVote.getUserr(),
            temporaryVote.getVoteType()
        );
    }

    public List<TemporaryVoteMessage> toMessageList(List<TemporaryVote> temporaryVotes){
        return (temporaryVotes.stream()
            .map(this::toMessage)
            .collect(Collectors.toList())
        );
    }

    public String toJson(List<TemporaryVoteMessage> messages) throws JsonProcessingException{
        Map<String, List<TemporaryVoteMessage>> dataMap = new HashMap<>();
        dataMap.put("response", messages);
        return objectMapper.writeValueAsString(dataMap);
    }
}
