package com.isep.bootstrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isep.bootstrapper.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    
}
