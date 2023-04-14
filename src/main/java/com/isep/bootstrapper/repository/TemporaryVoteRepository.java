package com.isep.bootstrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isep.bootstrapper.model.TemporaryVote;

@Repository
public interface TemporaryVoteRepository extends JpaRepository<TemporaryVote, Long> {
    
}
