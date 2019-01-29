package com.example.blog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@Getter
@Setter
@Entity
public class Post extends Auditable<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_POST", allocationSize=1)
	@GeneratedValue(generator="SEQ_POST", strategy=GenerationType.SEQUENCE)
	private Integer seq;
	private String title;
	private String contents;
	
	protected Post() {}
	
	public Post(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
	
	public static Post of(String title, String contents) {
		return new Post(title, contents);
	}
}
