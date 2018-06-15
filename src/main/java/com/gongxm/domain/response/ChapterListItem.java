package com.gongxm.domain.response;

import com.gongxm.bean.BookChapter;
import com.google.gson.annotations.Expose;

public class ChapterListItem {
	@Expose
	private String id;
	@Expose
	private int position;
	@Expose
	private String chapter_name;
	@Expose
	private String bookId; // 书籍ID

	public ChapterListItem() {
		super();
	}

	public ChapterListItem(BookChapter chapter) {
		super();
		this.id = chapter.getId();
		this.position = chapter.getPosition();
		this.chapter_name = chapter.getChapter_name();
		this.bookId = chapter.getBookId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getChapter_name() {
		return chapter_name;
	}

	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

}
