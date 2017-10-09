package com.gongxm.services;

import java.util.List;

import com.gongxm.bean.BookChapter;

public interface BookChapterService extends Service<BookChapter> {

	List<BookChapter> findByBookId(int bookid);

	long getUnCollectChapterCount();

	List<BookChapter> findUnCollectChapter(int currentPage, int pageSize);

	BookChapter findByChapterLink(String chapterLink);

}
