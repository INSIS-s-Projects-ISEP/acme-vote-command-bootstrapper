package com.isep.bootstrapper.dto.message;

import java.util.UUID;

import com.isep.bootstrapper.enumarate.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryVoteMessage {

    private UUID temporaryVoteId;
    private String user;
    private VoteType voteType;
    
}