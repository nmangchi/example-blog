package com.example.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private PostRepository postRepository;
	
	@Cacheable(value="posts")
	public Page<Post> findAll(final Post post, final PageRequest pageRequest) {
		Page<Post> posts = null;
		if (post != null) {
			ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
					.withStringMatcher(StringMatcher.CONTAINING);
			posts = postRepository.findAll(Example.of(post, exampleMatcher), pageRequest);
		} else {
			posts = postRepository.findAll(pageRequest);
		}
		return posts;
	}

	public Post save(Post post) {
		return postRepository.save(post);
	}
	
	public Optional<Post> modify(Post post) {
		Optional<Post> oldPost = postRepository.findById(post.getSeq());
		if (oldPost.isPresent()) {
			return Optional.of(postRepository.save(post));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Post> findById(Integer id) {
		return postRepository.findById(id);
	}

	public void deleteById(Integer id) {
		postRepository.deleteById(id);
	}
}
