package com.example.blog.api;

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
import com.example.blog.model.Post;
import com.example.blog.model.Search;
import com.example.blog.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

	@Autowired
	private PostService postService;

	@PostMapping("")
	public ResponseEntity<Post> postPosts(@RequestBody Post post) {
		post = postService.save(post);
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("")
	public ResponseEntity<Page<Post>> getPosts(
			@RequestParam(required=false, defaultValue = AppConsts.Page.INIT) int page,
			@RequestParam(required=false, defaultValue = AppConsts.Page.ROW_SIZE) int size,
			Search search) {
		Sort sort = Sort.by(Direction.DESC, "wrote");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Post> posts = postService.findAll(search.to(Post.class), pageRequest);
		return ResponseEntity.ok(posts);
	}

	@PutMapping("/{seq}")
	public ResponseEntity<?> putPosts(@PathVariable Integer seq, @RequestBody Post post) {
		post.setSeq(seq);
		Optional<Post> newPost = postService.modify(post);
		if (newPost.isPresent()) {
			return ResponseEntity.ok(newPost);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{seq}")
	public ResponseEntity<?> deletePosts(@PathVariable Integer seq) {
		postService.deleteBySeq(seq);
		return (ResponseEntity<?>) ResponseEntity.ok();
	}
	
	@GetMapping("/{seq}")
	public ResponseEntity<?> getPostsBySeq(@PathVariable Integer seq) {
		Optional<Post> post = postService.findBySeq(seq);
		return ResponseEntity.ok(post);
	}
}
