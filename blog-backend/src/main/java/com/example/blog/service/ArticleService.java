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

import com.example.blog.dao.ArticleRepository;
import com.example.blog.model.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Cacheable(value="articles")
	public Page<Article> findAll(final Article article, final PageRequest pageRequest) {
		Page<Article> articles = null;
		if (article != null) {
			ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
					.withStringMatcher(StringMatcher.CONTAINING);
			articles = articleRepository.findAll(Example.of(article, exampleMatcher), pageRequest);
		} else {
			articles = articleRepository.findAll(pageRequest);
		}
		return articles;
	}

	public Article save(Article article) {
		return articleRepository.save(article);
	}
	
	public Optional<Article> modify(Article article) {
		Optional<Article> oldArticle = articleRepository.findBySeq(article.getSeq());
		if (oldArticle.isPresent()) {
			return Optional.of(articleRepository.save(article));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Article> findBySeq(Integer seq) {
		return articleRepository.findBySeq(seq);
	}

	public void deleteBySeq(Integer seq) {
		articleRepository.deleteBySeq(seq);
	}
}
