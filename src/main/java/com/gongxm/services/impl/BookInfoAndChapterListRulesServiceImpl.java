package com.gongxm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.dao.BookInfoAndChapterListRulesDao;
import com.gongxm.dao.Dao;
import com.gongxm.services.BookInfoAndChapterListRulesService;

@Service("bookInfoAndChapterListRulesService")
public class BookInfoAndChapterListRulesServiceImpl extends BaseService<BookInfoAndChapterListRules>
		implements BookInfoAndChapterListRulesService {
	@Autowired
	@Qualifier("bookInfoAndChapterListRulesDao")
	private BookInfoAndChapterListRulesDao dao;
	@Override
	public Dao<BookInfoAndChapterListRules> getDao() {
		return dao;
	}
	@Override
	public List<BookInfoAndChapterListRules> findAll() {
		return dao.findAll();
	}

}
