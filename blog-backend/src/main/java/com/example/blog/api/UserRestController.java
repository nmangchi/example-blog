package com.example.blog.api;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.AppConsts;
import com.example.blog.model.Search;
import com.example.blog.model.User;
import com.example.blog.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@PostMapping("")
	public ResponseEntity<User> postUsers(@RequestBody User user) {
		user = userService.save(user);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("")
	public ResponseEntity<Page<User>> getUsers(
			@RequestParam(required=false, defaultValue = AppConsts.Page.INIT) int page,
			@RequestParam(required=false, defaultValue = AppConsts.Page.ROW_SIZE) int size,
			Search search) {
		Sort sort = Sort.by(Direction.DESC, "wrote");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<User> users = userService.findAll(search.to(User.class), pageRequest);
		return ResponseEntity.ok(users);
	}
	
	@PutMapping("/{seq}")
	public ResponseEntity<?> putUsers(@PathVariable Integer seq, @RequestBody User user) {
		user.setSeq(seq);
		Optional<User> newUser = userService.modify(user);
		if (newUser.isPresent()) {
			return ResponseEntity.ok(newUser);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{seq}")
	public ResponseEntity<?> deleteUsers(@PathVariable Integer seq) {
		userService.delete(seq);
		return (ResponseEntity<?>) ResponseEntity.ok();
	}
	
	@GetMapping("/{seq}")
	public ResponseEntity<?> getUsersBySeq(@PathVariable Integer seq) {
		Optional<User> user = userService.findBySeq(seq);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/info")
	public ResponseEntity<?> getUsersInfoBySeq(Principal principal) {
		log.debug("############ principal : {}", principal);
		log.debug("############ principal.getName() : {}", principal.getName());
		Optional<User> user = userService.findBySeq(Integer.parseInt(principal.getName()));
		return ResponseEntity.ok(user);
	}
}
