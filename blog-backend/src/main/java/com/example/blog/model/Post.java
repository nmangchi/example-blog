package com.example.blog.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String title;
	private String contents;
	@CreatedDate
	private LocalDateTime wrote;
	@LastModifiedDate
	private LocalDateTime modified;
	@CreatedBy
	private Integer writer;
	@LastModifiedBy
	private Integer modifier;
	
	protected Post() {}
	
	public Post(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
	
	public static Post of(String title, String contents) {
		return new Post(title, contents);
	}
}
