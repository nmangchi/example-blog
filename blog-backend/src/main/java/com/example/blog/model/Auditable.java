package com.example.blog.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {
	@CreatedDate
	@Column(updatable=false)
	private LocalDateTime wrote;
	@LastModifiedDate
	private LocalDateTime modified;
	@CreatedBy
	@Column(name="writer_seq",updatable=false)
	private T writer;
	@LastModifiedBy
	@Column(name="modifier_seq")
	private T modifier;
}
