package com.isep.bootstrapper.messaging;

import java.io.IOException;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.bootstrapper.dto.mapper.ReviewMapper;
import com.isep.bootstrapper.dto.message.ReviewMessage;
import com.isep.bootstrapper.model.Review;
import com.isep.bootstrapper.repository.ReviewRepository;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ReviewConsumer {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

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
