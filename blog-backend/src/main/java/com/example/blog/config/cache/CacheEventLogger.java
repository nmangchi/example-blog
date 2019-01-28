package com.example.blog.config.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
		log.info("### cache key : {}, old : {}, new : {}", event.getKey(), event.getOldValue(), event.getNewValue());
	}

}
