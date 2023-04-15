package com.isep.bootstrapper.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.isep.bootstrapper.enumarate.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryVote {

    @Id
    private UUID temporaryVoteId;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;
    private String userr;

}
