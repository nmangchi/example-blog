package com.example.blog.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.blog.dao.PostRepositoryIntegrationTests;
import com.example.blog.model.Post;

@RunWith(SpringRunner.class)
@SpringBootTest
@IfProfileValue(name="test-groups", value="integration")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostServiceIntegrationTests {

	@Autowired
	private PostService postService;
	
	private static final List<Post> posts = PostRepositoryIntegrationTests.dummyPosts;
	
	@Test
	@Transactional
	@Rollback
	@WithMockUser(value="1", authorities= {"USER"})
	public void A_save_posts() {
		for (Post post : posts) {
			postService.save(post);
		}
	}
	
	@Test
	public void find_all_with_post() {
		int page = 0;
		int size = 3;
		Sort sort = Sort.by(Direction.DESC, "wrote");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		
		Post post = Post.of("용기를", null);
		Page<Post> posts = postService.findAll(post, pageRequest);
		
		assertNotEquals(0, posts.getTotalElements());
		assertThat(posts.getContent().get(0).getTitle(), containsString(post.getTitle()));
	}
}
