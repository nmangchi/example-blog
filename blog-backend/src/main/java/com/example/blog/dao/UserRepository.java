package com.example.blog.dao;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.blog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, ObjectId> {

	@Query("select u from User u where u.id = ?1")
	Optional<User> findByUserId(String userId);
}
