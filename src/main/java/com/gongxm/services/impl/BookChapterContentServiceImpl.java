package com.gongxm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gongxm.bean.BookChapterContent;
import com.gongxm.dao.BookChapterContentDao;
import com.gongxm.dao.Dao;
import com.gongxm.services.BookChapterContentService;
@Service("bookChapterContentService")
@Transactional
public class BookChapterContentServiceImpl extends BaseService<BookChapterContent>
		implements BookChapterContentService {
	
	@Autowired
	BookChapterContentDao dao;

	@Override
	public Dao<BookChapterContent> getDao() {
		return dao;
	}

	@Override
	public BookChapterContent findByChapterId(String uuid) {
		return dao.findByChapterId(uuid);
	}

}
