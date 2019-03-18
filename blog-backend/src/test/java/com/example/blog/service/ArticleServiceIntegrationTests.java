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

import com.example.blog.dao.ArticleRepositoryIntegrationTests;
import com.example.blog.model.Article;

@RunWith(SpringRunner.class)
@SpringBootTest
@IfProfileValue(name="test-groups", value="integration")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArticleServiceIntegrationTests {

	@Autowired
	private ArticleService articleService;
	
	private static final List<Article> articles = ArticleRepositoryIntegrationTests.dummyArticles;
	
	@Test
	@Transactional
	@Rollback
	@WithMockUser(value="1", authorities= {"USER"})
	public void A_save_articles() {
		for (Article article : articles) {
			articleService.save(article);
		}
	}
	
	@Test
	public void find_all_with_article() {
		int page = 0;
		int size = 3;
		Sort sort = Sort.by(Direction.DESC, "wrote");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		
		Article article = Article.of("용기를", null);
		Page<Article> articles = articleService.findAll(article, pageRequest);
		
		assertNotEquals(0, articles.getTotalElements());
		assertThat(articles.getContent().get(0).getTitle(), containsString(article.getTitle()));
	}
}
