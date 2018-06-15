package com.gongxm.services;

import java.util.List;

import com.gongxm.bean.Book;

public interface BookService extends Service<Book> {

	List<String> getBookCategory();

	List<Book> getCategoryList(String category, int currentPage, int pageSize);

	List<Book> findListByKeyword(String keyword, int currentPage, int pageSize);

	public Book findByBookId(String uuid);

	void deleteById(int id);

	/**根据类别,随机推荐四本书籍
	 * @param count */
	List<Book> findBookByCategory(String category, int count);

	long getBookCountByCategory(String category);
}
