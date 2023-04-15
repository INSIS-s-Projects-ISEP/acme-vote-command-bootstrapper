package com.isep.bootstrapper.messaging;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.bootstrapper.dto.mapper.ReviewMapper;
import com.isep.bootstrapper.dto.message.ReviewMessage;
import com.isep.bootstrapper.event.ReviewCreatedEvent;
import com.isep.bootstrapper.event.ReviewDeletedEvent;
import com.isep.bootstrapper.event.ReviewUpdatedEvent;
import com.isep.bootstrapper.model.Review;
import com.isep.bootstrapper.repository.ReviewRepository;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ReviewConsumer {

    private final CommandGateway commandGateway;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @RabbitListener(queues = "#{reviewCreatedQueue.name}", ackMode = "MANUAL")
    public void reviewCreated(ReviewCreatedEvent reviewCreatedEvent, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        
        try {
            log.info("Review received: " + reviewCreatedEvent.getReviewId());
            commandGateway.send(reviewCreatedEvent);
            log.info("Review created: " + reviewCreatedEvent.getReviewId());
            channel.basicAck(tag, false);
        }
        catch (Exception e) {
            log.error("Fail to create review: " + reviewCreatedEvent.getReviewId());
            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queues = "#{reviewUpdatedQueue.name}", ackMode = "MANUAL")
    public void reviewUpdated(ReviewUpdatedEvent reviewUpdatedEvent, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        
        log.info("Review received: " + reviewUpdatedEvent.getReviewId());
        try {
            commandGateway.send(reviewUpdatedEvent);
            log.info("Review updated: " + reviewUpdatedEvent.getReviewId());
            channel.basicAck(tag, false);
        }
        catch (Exception e) {
            log.error("Fail to update review: " + reviewUpdatedEvent.getReviewId());
            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queues = "#{reviewDeletedQueue.name}", ackMode = "MANUAL")
    public void reviewDeleted(UUID reviewId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("Review deleted received: " + reviewId);
        try {
            Review review = reviewRepository.findById(reviewId).orElseThrow();
            ReviewDeletedEvent reviewDeletedEvent = new ReviewDeletedEvent(review.getReviewId());
            commandGateway.send(reviewDeletedEvent);
            log.info("Review deleted: " + reviewId);
            channel.basicAck(tag, false);
        }
        catch (Exception e) {
            log.error("Fail to delete review: " + reviewId);
            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queues = "#{rpcReviewQueue.name}", ackMode = "MANUAL")
    public String rpcReview(String instanceId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("RPC Review Request received: " + instanceId);
        try {
            List<Review> reviews = reviewRepository.findAll();
            List<ReviewMessage> messages = reviewMapper.toMessageList(reviews);
            String response = reviewMapper.toJson(messages);

            log.info("RPC Review Request sent to: " + instanceId);
            channel.basicAck(tag, false);
            return response;
        }
        catch (Exception e) {
            log.error("Error to send RPC Review Request to: " + instanceId);
            channel.basicReject(tag, true);
            return "";
        }

    }

}
