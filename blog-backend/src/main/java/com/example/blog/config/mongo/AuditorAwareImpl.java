package com.example.blog.config.mongo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.blog.model.User;
import com.example.blog.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<Integer> {

	@Autowired
	private UserService userService;
	
	@Override
	public Optional<Integer> getCurrentAuditor() {
		log.debug("###### getCurrentAuditor");
		Optional<String> principal = Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal)
				.map(String.class::cast);
		
		log.debug("###### loginId : {}", principal.get());
		Optional<User> user = userService.findByLoginId(principal.get());
		log.debug("##### user : {}", user.get());
		return Optional.ofNullable(user.get().getSeq());
	}

}
