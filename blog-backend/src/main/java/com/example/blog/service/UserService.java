package com.example.blog.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blog.dao.UserRepository;
import com.example.blog.model.CustomUser;
import com.example.blog.model.User;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByLoginId(loginId);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("Invalid login id");
		}
		
		return new CustomUser(user.get().getSeq(), user.get().getLoginId(), user.get().getLoginPassword(), getAuthority());
	}

	private Collection<? extends GrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

	public Optional<User> modify(User user) {
		Optional<User> oldUser = userRepository.findBySeq(user.getSeq());
		if (oldUser.isPresent()) {
			return Optional.of(userRepository.save(user));
		} else {
			return Optional.empty();
		}
	}
	
	public void delete(Integer seq) {
		userRepository.deleteBySeq(seq);
	}

	public Optional<User> findBySeq(Integer seq) {
		return userRepository.findBySeq(seq);
	}
	
	public Page<User> findAll(final User user, final PageRequest pageRequest) {
		Page<User> users = null;
		if (user != null) {
			ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
					.withStringMatcher(StringMatcher.CONTAINING);
			users = userRepository.findAll(Example.of(user, exampleMatcher), pageRequest);
		} else {
			users = userRepository.findAll(pageRequest);
		}
		return users;
	}
}
