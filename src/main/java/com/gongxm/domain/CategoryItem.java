package com.gongxm.domain;

import com.gongxm.bean.Book;

public class CategoryItem {
	private int id;
	private String book_name;
	private String author;
	private String cover;
	
	public CategoryItem() {
	}
	
	public CategoryItem(Book book) {
		this.id = book.getId();
		this.book_name = book.getBook_name();
		this.author = book.getAuthor();
		this.cover = book.getCover();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

}
