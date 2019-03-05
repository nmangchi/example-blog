package com.example.blog.api;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.blog.model.Post;
import com.example.blog.service.PostService;

@RunWith(SpringRunner.class)
@WebMvcTest(PostRestController.class)
public class PostRestControllerTests {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PostService postService;
	
	private List<Post> posts = null;
	
	@Before
	public void before() {
		posts = new ArrayList<>();
		posts.add(Post.of("테스트 타이틀 1", "테스트 내용 1"));
		posts.add(Post.of("테스트 타이틀 2", "테스트 내용 2"));
	}
	
	@Test
	public void post_list() throws Exception {
		int page = 0;
		int size = 3;
		Sort sort = Sort.by(Direction.DESC, "_id");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		
		PageImpl<Post> pages = new PageImpl<Post>(posts, pageRequest, posts.size());
		
		given(postService.findAll(null, pageRequest))
			.willReturn(pages);
		
		mvc.perform(get("/api/posts"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("타이틀")))
			.andExpect(content().string(containsString("내용")))
			;
	}
}
