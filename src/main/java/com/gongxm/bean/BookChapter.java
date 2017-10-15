package com.gongxm.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "book_chapters")
public class BookChapter implements Comparable<BookChapter>, Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Expose
	private String id;
	@Expose
	private int position;
	@Expose
	private String chapter_name;
	@Expose
	private String chapter_link;
	@Expose
	private int status;// 采集状态
	@Expose
	private String bookId; // 书籍ID
	@Expose
	private int rulesId; // 采集规则ID

	public BookChapter() {
	}

	public BookChapter(String id, String chapter_name, String chapter_link, int position, String bookId, int rulesId) {
		super();
		this.id = id;
		this.chapter_name = chapter_name;
		this.chapter_link = chapter_link;
		this.position = position;
		this.bookId = bookId;
		this.rulesId = rulesId;
	}

	public BookChapter(String id, String chapter_name, String chapter_link) {
		super();
		this.id = id;
		this.chapter_name = chapter_name;
		this.chapter_link = chapter_link;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChapter_name() {
		return chapter_name;
	}

	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}

	public String getChapter_link() {
		return chapter_link;
	}

	public void setChapter_link(String chapter_link) {
		this.chapter_link = chapter_link;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRulesId() {
		return rulesId;
	}

	public void setRulesId(int rulesId) {
		this.rulesId = rulesId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "BookChapter [id=" + id + ", position=" + position + ", chapter_name=" + chapter_name + ", chapter_link="
				+ chapter_link + ", status=" + status + "]";
	}

	@Override
	public int compareTo(BookChapter chapter) {
		return this.position - chapter.position;
	}

}