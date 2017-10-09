package com.gongxm.dao;

import java.util.List;

import com.gongxm.bean.BookListRules;

public interface BookListRulesDao extends Dao<BookListRules> {

	List<BookListRules> findAll();

}
