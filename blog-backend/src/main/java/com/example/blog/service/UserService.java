package com.example.blog.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blog.dao.UserRepository;
import com.example.blog.model.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		log.debug("### loadUserByUsername loginId : {}", loginId);
		Optional<User> user = userRepository.findByLoginId(loginId);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("Invalid login id");
		}
		
		return new org.springframework.security.core.userdetails.User(user.get().getLoginId(), user.get().getLoginPassword(), getAuthority());
	}

	private Collection<? extends GrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	public Optional<User> findByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
}
