package com.isep.bootstrapper.messaging;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.bootstrapper.dto.mapper.TemporaryVoteMapper;
import com.isep.bootstrapper.dto.message.TemporaryVoteMessage;
import com.isep.bootstrapper.event.DefinitiveVoteCreatedEvent;
import com.isep.bootstrapper.event.TemporaryVoteCreatedEvent;
import com.isep.bootstrapper.model.TemporaryVote;
import com.isep.bootstrapper.repository.TemporaryVoteRepository;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class TemporaryVoteConsumer {

    private final CommandGateway commandGateway;
    private final TemporaryVoteRepository temporaryVoteRepository;
    private final TemporaryVoteMapper temporaryVoteMapper;
    
    @RabbitListener(queues = "#{temporaryVoteCreatedQueue.name}", ackMode = "MANUAL")
    public void temporaryVoteCreated(TemporaryVoteCreatedEvent temporaryVoteCreated, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        try {
            log.info("Temporary Vote received: " + temporaryVoteCreated.getTemporaryVoteId());
            commandGateway.send(temporaryVoteCreated);
            log.info("Temporary Vote created: " + temporaryVoteCreated.getTemporaryVoteId());
            channel.basicAck(tag, false);
        }
        catch (Exception e) {
            log.error("Fail to create temporary vote: " + temporaryVoteCreated.getTemporaryVoteId());
            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queues = "#{definitiveVoteCreatedQueue.name}", ackMode = "MANUAL")
    public void definitiveVoteCreated(UUID temporaryVoteId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("Definitive Vote created received for temporary vote: " + temporaryVoteId);
        try {
            TemporaryVote temporaryVote = temporaryVoteRepository.findById(temporaryVoteId).orElseThrow();
            DefinitiveVoteCreatedEvent definitiveVoteCreatedEvent = new DefinitiveVoteCreatedEvent(temporaryVote.getTemporaryVoteId());
            commandGateway.send(definitiveVoteCreatedEvent);
            log.info("Temporary Vote deleted: " + temporaryVoteId);
            channel.basicAck(tag, false);
        }
        catch (Exception e) {
            log.error("Fail to delete temporary vote: " + temporaryVoteId);
            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queues = "#{rpcTemporaryVoteQueue.name}", ackMode = "MANUAL")
    public String rpcTemporaryVote(String instanceId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("RPC Temporary Vote Request received: " + instanceId);
        try {
            List<TemporaryVote> temporaryVotes = temporaryVoteRepository.findAll();
            List<TemporaryVoteMessage> messages = temporaryVoteMapper.toMessageList(temporaryVotes);
            String response = temporaryVoteMapper.toJson(messages);

            log.info("RPC Temporary Vote Request sent to: " + instanceId);
            channel.basicAck(tag, false);
            return response;
        }
        catch (Exception e) {
            log.error("Error to send RPC Temporary Vote Request to: " + instanceId);
            channel.basicReject(tag, true);
            return "";
        }

    }

}
