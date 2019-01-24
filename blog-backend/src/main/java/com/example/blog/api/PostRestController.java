package com.example.blog.api;

import java.util.Optional;

import org.bson.types.ObjectId;
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
import com.example.blog.model.Post;
import com.example.blog.model.Search;
import com.example.blog.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

	private final PostService postService;

	public PostRestController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("")
	public ResponseEntity<?> postPosts(@RequestBody Post post) {
		post = postService.save(post);
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("")
	public ResponseEntity<?> getPosts(
			@RequestParam(required=false, defaultValue = AppConsts.Page.INIT) int page,
			@RequestParam(required=false, defaultValue = AppConsts.Page.ROW_SIZE) int size,
			Search search) {
		Sort sort = Sort.by(Direction.DESC, "_id");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Post> posts = postService.findAll(search.to(Post.class), pageRequest);
		return ResponseEntity.ok(posts);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> putPosts(@PathVariable ObjectId id, @RequestBody Post post) {
		Optional<Post> oldPost = postService.findById(id);
		if (oldPost.isPresent()) {
			post.set_id(id);
			post = postService.save(post);
			return ResponseEntity.ok(post);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePosts(@PathVariable ObjectId id) {
		postService.deleteById(id);
		return (ResponseEntity<?>) ResponseEntity.ok();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPostsById(@PathVariable ObjectId id) {
		Optional<Post> post = postService.findById(id);
		return ResponseEntity.ok(post);
	}
}
