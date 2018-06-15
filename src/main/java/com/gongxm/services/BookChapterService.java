package com.gongxm.services;

import java.util.ArrayList;
import java.util.List;

import com.gongxm.bean.BookChapter;

public interface BookChapterService extends Service<BookChapter> {

	List<BookChapter> findByBookId(String bookid);

	long getUnCollectChapterCount();

	List<BookChapter> findUnCollectChapter(int currentPage, int pageSize);

	BookChapter findByChapterId(String uuid);
//	BookChapter findByChapterLink(String chapterLink);

	int findChapterCountByBookId(String bookId);

	void addAll(ArrayList<BookChapter> list);

}
