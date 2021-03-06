package com.gongxm.dao;

import java.util.List;

import com.gongxm.bean.Book;

public interface BookDao extends Dao<Book> {

	List<String> getBookCategory();

	List<Book> getCategoryList(String category, int currentPage, int pageSize);

	List<Book> findListByKeyword(String keyword, int currentPage, int pageSize);

	void deleteById(int id);

	public Book findByUuid(String uuid);

	List<Book> findBookByCategory(String category, int count);
	
	public long getBookCountByCategory(String category);

}
