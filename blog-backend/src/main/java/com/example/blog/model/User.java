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

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_USER")
	@GeneratedValue(generator="SEQ_USER", strategy=GenerationType.SEQUENCE)
	private Integer seq;
	private String id;
	private String password;
	private String name;
	private String nickname;
	
	protected User() {}
	
	public User(String id, String name, String nickname) {
		this.id = id;
		this.name = name;
		this.nickname = nickname;
	}
	
}
