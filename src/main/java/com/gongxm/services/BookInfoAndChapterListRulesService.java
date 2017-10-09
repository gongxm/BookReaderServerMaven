package com.gongxm.services;

import java.util.List;

import com.gongxm.bean.BookInfoAndChapterListRules;

public interface BookInfoAndChapterListRulesService extends Service<BookInfoAndChapterListRules> {

	List<BookInfoAndChapterListRules> findAll();

}
