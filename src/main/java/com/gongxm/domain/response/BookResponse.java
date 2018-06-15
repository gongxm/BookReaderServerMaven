package com.gongxm.domain.response;

import java.util.Collection;
import java.util.List;

import com.gongxm.bean.Book;

public class BookResponse extends ResponseResult {
	private Book book;
	private Collection<Book> books;
	private List<Book> recommend;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Collection<Book> getBooks() {
		return books;
	}

	public void setBooks(Collection<Book> books) {
		this.books = books;
	}

	public List<Book> getRecommend() {
		return recommend;
	}

	public void setRecommend(List<Book> recommend) {
		this.recommend = recommend;
	}

}
