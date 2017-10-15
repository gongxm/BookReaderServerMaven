package com.gongxm.services;

import com.gongxm.bean.BookChapterContent;

public interface BookChapterContentService extends Service<BookChapterContent> {

	BookChapterContent findByChapterId(String uuid);

}
