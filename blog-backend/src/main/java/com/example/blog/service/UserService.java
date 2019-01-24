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
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		log.debug("### loadUserByUsername userid : {}", userId);
		Optional<User> user = userRepository.findByUserId(userId);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("Invalid userid");
		}
		
		return new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getPassword(), getAuthority());
	}

	private Collection<? extends GrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

}
