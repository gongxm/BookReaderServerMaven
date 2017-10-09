package com.gongxm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gongxm.bean.Book;
import com.gongxm.dao.BookDao;
import com.gongxm.dao.Dao;
import com.gongxm.services.BookService;
@Service("bookService")
@Transactional
public class BookServiceImpl extends BaseService<Book> implements BookService {
	
	@Autowired
	@Qualifier("bookDao")
	private BookDao dao;
	
	@Override
	public Dao<Book> getDao() {
		return dao;
	}

	@Override
	public List<String> getBookCategory() {
		return dao.getBookCategory();
	}

	@Override
	public List<Book> getCategoryList(String category, int currentPage, int pageSize) {
		return dao.getCategoryList(category,currentPage,pageSize);
	}

	@Override
	public List<Book> findListByKeyword(String keyword, int currentPage, int pageSize) {
		return dao.findListByKeyword(keyword,currentPage,pageSize);
	}

	@Override
	public Book findByBookUrl(String url) {
		return dao.findByBookUrl(url);
	}

}
