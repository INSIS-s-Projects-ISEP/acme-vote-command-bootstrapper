package com.isep.bootstrapper.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
        Jackson2JsonMessageConverter jackson2JsonMessageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationListener(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    // Review Created
    @Bean
    public FanoutExchange reviewCreatedExchange() {
        return new FanoutExchange("review.review-created");
    }
    
    @Bean
    public Queue reviewCreatedQueue(String intanceId) {
        return new Queue("review.review-created.vote-command-bootstrapper." + intanceId, true, true, true);
    }

    @Bean
    public Binding bindingReviewCreatedtoReviewCreated(FanoutExchange reviewCreatedExchange,
            Queue reviewCreatedQueue) {
        return BindingBuilder.bind(reviewCreatedQueue).to(reviewCreatedExchange);
    }

    // Review Updated
    @Bean
    public FanoutExchange reviewUpdatedExchange() {
        return new FanoutExchange("review.review-updated");
    }
    
    @Bean
    public Queue reviewUpdatedQueue(String intanceId) {
        return new Queue("review.review-updated.vote-command-bootstrapper." + intanceId, true, true, true);
    }

    @Bean
    public Binding bindingReviewUpdatedtoReviewUpdated(FanoutExchange reviewUpdatedExchange,
            Queue reviewUpdatedQueue) {
        return BindingBuilder.bind(reviewUpdatedQueue).to(reviewUpdatedExchange);
    }

    // Review Deleted
    @Bean
    public FanoutExchange reviewDeletedExchange() {
        return new FanoutExchange("review.review-deleted");
    }
    
    @Bean
    public Queue reviewDeletedQueue(String intanceId) {
        return new Queue("review.review-deleted.vote-command-bootstrapper." + intanceId, true, true, true);
    }

    @Bean
    public Binding bindingReviewDeletedtoReviewDeleted(FanoutExchange reviewDeletedExchange,
            Queue reviewDeletedQueue) {
        return BindingBuilder.bind(reviewDeletedQueue).to(reviewDeletedExchange);
    }

    // Vote Created
    @Bean
    public FanoutExchange voteCreatedExchange() {
        return new FanoutExchange("vote.vote-created");
    }

    @Bean
    public Queue voteCreatedQueue(String instanceId){
        return new Queue("vote.vote-created.vote-command-bootstrapper." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingVoteCreatedToVoteCreated(FanoutExchange voteCreatedExchange, Queue voteCreatedQueue) {
        return BindingBuilder.bind(voteCreatedQueue).to(voteCreatedExchange);
    }

    @Bean
    public FanoutExchange voteDeletedExchange() {
        return new FanoutExchange("vote.vote-deleted");
    }

    @Bean
    public Queue voteDeletedQueue(String instanceId) {
        return new Queue("vote.vote-deleted.vote-command-bootstrapper." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingvoteDeletedtovoteDeleted(FanoutExchange voteDeletedExchange,
            Queue voteDeletedQueue) {
        return BindingBuilder.bind(voteDeletedQueue).to(voteDeletedExchange);
    }

    // SAGA
    // Fanout Exchange and Queues to receive created temporary votes
    @Bean
    public FanoutExchange temporaryVoteCreatedExchange() {
        return new FanoutExchange("temporary-vote.temporary-vote-created");
    }

    @Bean
    public Queue temporaryVoteCreatedQueue(String instanceId) {
        return new Queue("temporary-vote.temporary-vote-created.vote-command-bootstrapper", true, false, false);
    }

    @Bean
    public Binding bindingTemporaryVoteCreatedToTemporaryVoteCreated(FanoutExchange temporaryVoteCreatedExchange,
            Queue temporaryVoteCreatedQueue) {
        return BindingBuilder.bind(temporaryVoteCreatedQueue).to(temporaryVoteCreatedExchange);
    }

    // Exchange and a queue to receive a definite vote
    @Bean
    public FanoutExchange definitiveVoteCreatedExchange() {
        return new FanoutExchange("vote.definitive-vote-created");
    }

    @Bean
    public Queue definitiveVoteCreatedQueue(String instanceId) {
        return new Queue("vote.definitive-vote-created.vote-command-bootstrapper." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingDefinitiveVoteCreatedToDefinitiveVoteCreated(FanoutExchange definitiveVoteCreatedExchange,
            Queue definitiveVoteCreatedQueue) {
        return BindingBuilder.bind(definitiveVoteCreatedQueue).to(definitiveVoteCreatedExchange);
    }

    // Bootstrapper
    // Review
    @Bean
    public FanoutExchange rpcReviewExchange(){
        return new FanoutExchange("rpc.review.vote-command-bootstrapper");
    }

    @Bean
    public Queue rpcReviewQueue(){
        return new Queue("rpc.review.vote-command-bootstrapper", true, false, false);
    }

    @Bean
    public Binding bindRpcReview(FanoutExchange rpcReviewExchange, Queue rpcReviewQueue){
        return BindingBuilder.bind(rpcReviewQueue).to(rpcReviewExchange);
    }

    // Vote
    @Bean
    public FanoutExchange rpcVoteExchange(){
        return new FanoutExchange("rpc.vote.vote-command-bootstrapper");
    }

    @Bean
    public Queue rpcVoteQueue(){
        return new Queue("rpc.vote.vote-command-bootstrapper", true, false, false);
    }

    @Bean
    public Binding bindRpcVote(FanoutExchange rpcVoteExchange, Queue rpcVoteQueue){
        return BindingBuilder.bind(rpcVoteQueue).to(rpcVoteExchange);
    }

    // Temporary Vote
    @Bean
    public FanoutExchange rpcTemporaryVoteExchange(){
        return new FanoutExchange("rpc.temporary-vote.vote-command-bootstrapper");
    }

    @Bean
    public Queue rpcTemporaryVoteQueue(){
        return new Queue("rpc.temporary-vote.vote-command-bootstrapper", true, false, false);
    }

    @Bean
    public Binding bindRpcTemporaryVote(FanoutExchange rpcTemporaryVoteExchange, Queue rpcTemporaryVoteQueue){
        return BindingBuilder.bind(rpcTemporaryVoteQueue).to(rpcTemporaryVoteExchange);
    }
}