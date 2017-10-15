package com.gongxm.bean;

import java.io.Serializable;

import javax.persistence.Entity;
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
public class BookChapterContent implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Expose
	private String id;

	@Type(type = "text")
	@Expose
	private String text;

	public BookChapterContent() {
		super();
	}

	public BookChapterContent(String chapterId, String text) {
		super();
		this.id = chapterId;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "BookChapterContent [id=" + id + ", text=" + text + "]";
	}

}