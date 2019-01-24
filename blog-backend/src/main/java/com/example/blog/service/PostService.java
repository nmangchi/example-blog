package com.example.blog.service;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.blog.dao.PostRepository;
import com.example.blog.model.Post;

@Service
public class PostService {

	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Cacheable(value="posts")
	public Page<Post> findAll(final Post post, final PageRequest pageRequest) {
		Page<Post> posts = null;
		if (post != null) {
			ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
					.withStringMatcher(StringMatcher.REGEX);
			posts = postRepository.findAll(Example.of(post, exampleMatcher), pageRequest);
		} else {
			posts = postRepository.findAll(pageRequest);
		}
		return posts;
	}

	public Post save(Post post) {
		return postRepository.save(post);
	}

	public Optional<Post> findById(String id) {
		return postRepository.findById(id);
	}

	public void deleteById(String id) {
		postRepository.deleteById(id);
	}
}
