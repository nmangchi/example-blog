package com.example.blog.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.blog.dao.PostRepository;
import com.example.blog.dao.PostRepositoryIntegrationTests;
import com.example.blog.model.Post;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTests {

	@Autowired
	private PostService postService;
	
	@MockBean
	private PostRepository postRepository;
	
	private static final List<Post> posts = PostRepositoryIntegrationTests.dummyPosts;
	
	@Test
	public void find_all_with_post() {
		int page = 0;
		int size = 3;
		Sort sort = Sort.by(Direction.DESC, "wrote");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
				.withStringMatcher(StringMatcher.CONTAINING);
		
		Post post = new Post("용기를", null);
		
		List<Post> filtered = posts.stream()
				.filter(p -> p.getTitle().contains(post.getTitle()))
				.collect(Collectors.toList());
		
		PageImpl<Post> pages = new PageImpl<Post>(filtered, pageRequest, filtered.size());
		
		given(postRepository.findAll(Example.of(post, exampleMatcher), pageRequest))
			.willReturn(pages);
		
		Page<Post> posts = postService.findAll(post, pageRequest);
		assertEquals(filtered.size(), posts.getTotalElements());
	}
}
