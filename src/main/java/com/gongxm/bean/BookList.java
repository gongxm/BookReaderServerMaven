package com.gongxm.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//书籍列表
@Entity
@Table(name = "book_list")
public class BookList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String book_source;// 采集来源
	@Column
	private String book_link;
	@Column
	private int status;// 0未采集, 1已采集,2采集失败,3重复

	public BookList(String book_source, String book_link, int status) {
		super();
		this.book_source = book_source;
		this.book_link = book_link;
		this.status = status;
	}

	public BookList() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBook_link() {
		return book_link;
	}

	public void setBook_link(String book_link) {
		this.book_link = book_link;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBook_source() {
		return book_source;
	}

	public void setBook_source(String book_source) {
		this.book_source = book_source;
	}

	@Override
	public String toString() {
		return "BookList [id=" + id + ", book_source=" + book_source + ", book_link=" + book_link + ", status=" + status
				+ "]";
	}

}
