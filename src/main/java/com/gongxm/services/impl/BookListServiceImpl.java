package com.gongxm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gongxm.bean.BookList;
import com.gongxm.dao.BookListDao;
import com.gongxm.dao.Dao;
import com.gongxm.services.BookListService;

/**
 * 用户服务
 * 
 * @author gongxm
 *
 */
@Service("bookListService")
@Transactional
public class BookListServiceImpl extends BaseService<BookList> implements BookListService {
	@Autowired
	@Qualifier("bookListDao")
	private BookListDao dao;

	@Override
	public Dao<BookList> getDao() {
		return dao;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BookList> findUnCollectBookListBySource(String book_source, int currentPage, int pageSize) {
		return dao.findUnCollectBookListBySource(book_source, currentPage, pageSize);
	}

	@Transactional(readOnly = true)
	@Override
	public List<BookList> findAllUnCollectBookList(int currentPage, int pageSize) {
		return dao.findAllUnCollectBookList(currentPage, pageSize);
	}

	@Transactional(readOnly = true)
	@Override
	public long getUnCollectBookListCountBySource(String book_source) {
		return dao.getUnCollectBookListCountBySource(book_source);
	}

	@Transactional(readOnly = true)
	@Override
	public long getAllUnCollectBookListCount() {
		return dao.getAllUnCollectBookListCount();
	}

	@Transactional(readOnly = true)
	@Override
	public BookList findByBookLink(String bookUrl) {
		return dao.findByBookLink(bookUrl);
	}

	@Override
	public long getBookListCountBySource(String book_source) {
		return dao.getBookListCountBySource(book_source);
	}

	@Override
	public List<BookList> findBookListBySource(String book_source, int currentPage, int pageSize) {
		return dao.findBookListBySource(book_source, currentPage, pageSize);
	}

}
