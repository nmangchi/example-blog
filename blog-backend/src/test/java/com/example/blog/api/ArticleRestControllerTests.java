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

import com.example.blog.model.Article;
import com.example.blog.service.ArticleService;

@RunWith(SpringRunner.class)
@WebMvcTest(ArticleRestController.class)
public class ArticleRestControllerTests {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ArticleService articleService;
	
	private List<Article> articles = null;
	
	@Before
	public void before() {
		articles = new ArrayList<>();
		articles.add(Article.of("테스트 타이틀 1", "테스트 내용 1"));
		articles.add(Article.of("테스트 타이틀 2", "테스트 내용 2"));
	}
	
	@Test
	public void article_list() throws Exception {
		int page = 0;
		int size = 3;
		Sort sort = Sort.by(Direction.DESC, "_id");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		
		PageImpl<Article> pages = new PageImpl<Article>(articles, pageRequest, articles.size());
		
		given(articleService.findAll(null, pageRequest))
			.willReturn(pages);
		
		mvc.perform(get("/api/articles"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("타이틀")))
			.andExpect(content().string(containsString("내용")))
			;
	}
}
