package com.isep.bootstrapper.projection;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.isep.bootstrapper.event.DefinitiveVoteCreatedEvent;
import com.isep.bootstrapper.event.TemporaryVoteCreatedEvent;
import com.isep.bootstrapper.model.TemporaryVote;
import com.isep.bootstrapper.repository.TemporaryVoteRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TemporaryVoteProjection {

    private final TemporaryVoteRepository temporaryVoteRepository;
    
    @EventHandler
    public void on(TemporaryVoteCreatedEvent event){
        temporaryVoteRepository.save(new TemporaryVote(
            event.getTemporaryVoteId(),
            event.getVoteType(),
            event.getUser()
        ));
    }

    @EventHandler
    public void on(DefinitiveVoteCreatedEvent event){
        temporaryVoteRepository.deleteById(event.getTemporaryVoteId());
    }
}
