package com.isep.bootstrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isep.bootstrapper.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
}
