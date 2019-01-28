package com.example.blog.api;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.blog.model.Post;
import com.example.blog.model.Search;
import com.example.blog.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostRestController {

	private final PostService postService;

	public PostRestController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("")
	public ResponseEntity<?> postPosts(Principal principal, @RequestBody Post post) {
		log.debug("####### principal : {}", principal);
		log.debug("####### principal.getName() : {}", principal.getName());
		log.debug("####### context auth : {}", SecurityContextHolder.getContext().getAuthentication());
		
		post = postService.save(post);
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("")
	public ResponseEntity<?> getPosts(
			Principal principal,
			@RequestParam(required=false, defaultValue = AppConsts.Page.INIT) int page,
			@RequestParam(required=false, defaultValue = AppConsts.Page.ROW_SIZE) int size,
			Search search) {
		Sort sort = Sort.by(Direction.DESC, "wrote");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Post> posts = postService.findAll(search.to(Post.class), pageRequest);
		return ResponseEntity.ok(posts);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> putPosts(@PathVariable String id, @RequestBody Post post) {
		Optional<Post> newPost = postService.modify(post);
		if (newPost.isPresent()) {
			return ResponseEntity.ok(newPost);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePosts(@PathVariable Integer id) {
		postService.deleteById(id);
		return (ResponseEntity<?>) ResponseEntity.ok();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPostsById(@PathVariable Integer id) {
		Optional<Post> post = postService.findById(id);
		return ResponseEntity.ok(post);
	}
}
