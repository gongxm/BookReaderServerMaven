package com.gongxm.services;

import java.util.List;

import com.gongxm.bean.BookListRules;

public interface BookListRulesService extends Service<BookListRules> {

	List<BookListRules> findAll();

}
