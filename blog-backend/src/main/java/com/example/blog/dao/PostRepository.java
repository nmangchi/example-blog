package com.example.blog.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.model.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	Optional<Post> findByTitle(String keyword);
	
	Optional<Post> findByContents(String keyword);
}
