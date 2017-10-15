package com.gongxm.dao;

import com.gongxm.bean.BookChapterContent;

public interface BookChapterContentDao extends Dao<BookChapterContent> {

	BookChapterContent findByChapterId(String uuid);

}
