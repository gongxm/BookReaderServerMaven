package com.gongxm.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.BookChapterContent;
import com.gongxm.dao.BookChapterContentDao;

@Repository("bookChapterContentDao")
public class BookChapterContentDaoImpl extends BaseDao<BookChapterContent> implements BookChapterContentDao {
	
	@Override
	public BookChapterContent findByChapterId(String uuid) {
		try {
			return qr.query("select * from book_chapter_content where id=?",
					new BeanHandler<BookChapterContent>(BookChapterContent.class), uuid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
