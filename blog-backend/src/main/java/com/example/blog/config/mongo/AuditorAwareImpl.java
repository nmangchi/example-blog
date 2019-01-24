package com.example.blog.config.mongo;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.blog.model.User;

public class AuditorAwareImpl implements AuditorAware<User> {

	@Override
	public Optional<User> getCurrentAuditor() {
		Optional<Object> map2 = Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal);
		System.out.println("######## " + map2);
		Optional<User> map = map2
				.map(User.class::cast);
		return map;
	}

}
