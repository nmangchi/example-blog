package com.example.blog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	Optional<Post> findBySeq(Integer seq);
	Optional<Post> findByTitle(String title);
	Optional<Post> findByContents(String contents);
	void deleteBySeq(Integer seq);
}
