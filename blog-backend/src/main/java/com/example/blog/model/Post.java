package com.example.blog.model;

import java.io.Serializable;

import javax.persistence.Id;

import org.bson.types.ObjectId;

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
	private ObjectId _id;
	private String title;
	private String contents;
	private Integer userSeq;
	
	protected Post() {}
	
	public Post(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
	
	public static Post of(String title, String contents) {
		return new Post(title, contents);
	}
}
