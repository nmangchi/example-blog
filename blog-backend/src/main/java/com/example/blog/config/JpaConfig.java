package com.example.blog.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.blog.model.CustomUser;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class JpaConfig {

	@Bean
	public AuditorAware<Integer> auditorProvider() {
		return () -> {
			Optional<CustomUser> customUser = Optional
					.ofNullable((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return Optional.ofNullable(customUser.get().getSeq());
		};
	}
}
