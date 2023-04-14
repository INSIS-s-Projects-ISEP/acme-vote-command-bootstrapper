package com.isep.bootstrapper.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.isep.bootstrapper.enumarate.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "fk_review", nullable = false)
    private Review review;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;
    private String user;

}
