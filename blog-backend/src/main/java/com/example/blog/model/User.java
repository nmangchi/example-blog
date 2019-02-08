package com.example.blog.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@Getter
@Setter
@Entity
public class User extends Auditable<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_USER", allocationSize=1)
	@GeneratedValue(generator="SEQ_USER", strategy=GenerationType.SEQUENCE)
//	@JsonIgnore
	private Integer seq;
	@Column(name="login_id")
//	@JsonIgnore
	private String loginId;
	@Column(name="login_password")
	@JsonIgnore
	private String loginPassword;
	private String name;
	private String nickname;
	
	protected User() {}
	
	public User(String loginId, String name, String nickname) {
		this.loginId = loginId;
		this.name = name;
		this.nickname = nickname;
	}
	
}
