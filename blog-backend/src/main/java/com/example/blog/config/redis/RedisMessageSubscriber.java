package com.example.blog.config.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisMessageSubscriber implements MessageListener {

	public static List<String> messageList = new ArrayList<>();
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		messageList.add(message.toString());
		log.debug("Message received : {}", message.toString());
		log.debug("pattern : {}", new String(pattern));
	}

}
