package com.gongxm.dao;

import java.util.List;

import com.gongxm.bean.BookList;

public interface BookListDao extends Dao<BookList> {

	List<BookList> findUnCollectBookListBySource(String book_source, int currentPage, int pageSize);

	List<BookList> findAllUnCollectBookList(int currentPage, int pageSize);

	long getUnCollectBookListCountBySource(String book_source);

	long getAllUnCollectBookListCount();

	BookList findByBookLink(String bookUrl);

	long getBookListCountBySource(String book_source);

	List<BookList> findBookListBySource(String book_source, int currentPage, int pageSize);


}
