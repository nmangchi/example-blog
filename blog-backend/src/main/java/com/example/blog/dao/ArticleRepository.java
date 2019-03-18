package com.example.blog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	Optional<Article> findBySeq(Integer seq);
	Optional<Article> findByTitle(String title);
	Optional<Article> findByContents(String contents);
	void deleteBySeq(Integer seq);
}
