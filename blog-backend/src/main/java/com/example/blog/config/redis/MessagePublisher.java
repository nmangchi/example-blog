package com.example.blog.config.redis;

public interface MessagePublisher {
	void publish(String message);
}
