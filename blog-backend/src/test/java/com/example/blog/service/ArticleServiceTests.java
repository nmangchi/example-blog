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

import com.example.blog.dao.ArticleRepository;
import com.example.blog.dao.ArticleRepositoryIntegrationTests;
import com.example.blog.model.Article;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTests {

	@Autowired
	private ArticleService articleService;
	
	@MockBean
	private ArticleRepository articleRepository;
	
	private static final List<Article> articles = ArticleRepositoryIntegrationTests.dummyArticles;
	
	@Test
	public void find_all_with_article() {
		int page = 0;
		int size = 3;
		Sort sort = Sort.by(Direction.DESC, "wrote");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
				.withStringMatcher(StringMatcher.CONTAINING);
		
		Article article = new Article("용기를", null);
		
		List<Article> filtered = articles.stream()
				.filter(p -> p.getTitle().contains(article.getTitle()))
				.collect(Collectors.toList());
		
		PageImpl<Article> pages = new PageImpl<Article>(filtered, pageRequest, filtered.size());
		
		given(articleRepository.findAll(Example.of(article, exampleMatcher), pageRequest))
			.willReturn(pages);
		
		Page<Article> articles = articleService.findAll(article, pageRequest);
		assertEquals(filtered.size(), articles.getTotalElements());
	}
}
