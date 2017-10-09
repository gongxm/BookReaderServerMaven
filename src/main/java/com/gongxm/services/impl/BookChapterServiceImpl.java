package com.gongxm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gongxm.bean.BookChapter;
import com.gongxm.dao.BookChapterDao;
import com.gongxm.dao.Dao;
import com.gongxm.services.BookChapterService;
@Service("bookChapterService")
@Transactional
public class BookChapterServiceImpl extends BaseService<BookChapter> implements BookChapterService {
	@Autowired
	@Qualifier("bookChapterDao")
	private BookChapterDao dao;

	@Override
	public Dao<BookChapter> getDao() {
		return dao;
	}

	@Override
	public List<BookChapter> findByBookId(int bookid) {
		return dao.findByBookId(bookid);
	}

	@Override
	public long getUnCollectChapterCount() {
		return dao.getUnCollectChapterCount();
	}

	@Override
	public List<BookChapter> findUnCollectChapter(int currentPage, int pageSize) {
		return dao.findUnCollectChapter(currentPage,pageSize);
	}

	@Override
	public BookChapter findByChapterLink(String chapterLink) {
		return dao.findByChapterLink(chapterLink);
	}

}
