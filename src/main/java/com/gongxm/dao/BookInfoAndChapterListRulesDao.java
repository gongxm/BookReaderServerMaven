package com.gongxm.dao;

import java.util.List;

import com.gongxm.bean.BookInfoAndChapterListRules;

public interface BookInfoAndChapterListRulesDao extends Dao<BookInfoAndChapterListRules>{

	List<BookInfoAndChapterListRules> findAll();

}
