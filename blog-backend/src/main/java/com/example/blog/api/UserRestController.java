package com.example.blog.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.model.User;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@GetMapping("")
	public ResponseEntity<User> users() {
		return ResponseEntity.ok(new User("tester", "테스터", "테스터닉"));
	}
}
