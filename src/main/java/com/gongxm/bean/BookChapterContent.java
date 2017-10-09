package com.gongxm.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

/**
 * @author gongxm
 *
 */
@Entity
@Table(name = "book_chapter_content")
public class BookChapterContent implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	private int id;

	@Type(type = "text")
	@Expose
	private String text;

	public BookChapterContent() {
		super();
	}

	public BookChapterContent(String text) {
		super();
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}