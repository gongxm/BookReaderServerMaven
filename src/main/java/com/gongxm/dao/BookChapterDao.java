package com.gongxm.dao;

import java.util.ArrayList;
import java.util.List;

import com.gongxm.bean.BookChapter;

public interface BookChapterDao extends Dao<BookChapter> {

	List<BookChapter> findByBookId(String bookid);

	long getUnCollectChapterCount();

	List<BookChapter> findUnCollectChapter(int currentPage, int pageSize);

	//BookChapter findByChapterLink(String chapterLink);
	
	public BookChapter findByChapterId(String uuid);

	int findChapterCountByBookId(String bookId);

	void addAll(ArrayList<BookChapter> list);

}
