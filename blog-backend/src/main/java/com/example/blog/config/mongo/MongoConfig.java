package com.example.blog.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.example.blog.model.User;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

	@Bean
	public AuditorAware<User> auditorProvidor() {
		return new AuditorAwareImpl();
	}
}
