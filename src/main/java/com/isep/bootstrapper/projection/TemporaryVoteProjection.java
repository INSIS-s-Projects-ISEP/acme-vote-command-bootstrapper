package com.isep.bootstrapper.projection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isep.bootstrapper.model.TemporaryVote;

@Repository
public interface TemporaryVoteProjection extends JpaRepository<TemporaryVote, Long> {
    
}
