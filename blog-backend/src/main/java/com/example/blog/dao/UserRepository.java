package com.example.blog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findBySeq(Integer seq);
	Optional<User> findByLoginId(String loginId);
	void deleteBySeq(Integer seq);
}
