package com.example.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.Topic;

public class RedisMessagePublisher implements MessagePublisher {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	private Topic topic;
	
	public RedisMessagePublisher() {
	}
	
	public RedisMessagePublisher(RedisTemplate<Object, Object> redisTemplate, ChannelTopic topic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}
	
	public RedisMessagePublisher(RedisTemplate<Object, Object> redisTemplate, PatternTopic topic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}

	@Override
	public void publish(String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}
}
