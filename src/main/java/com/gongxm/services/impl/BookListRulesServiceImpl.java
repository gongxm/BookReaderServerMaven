package com.gongxm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gongxm.bean.BookListRules;
import com.gongxm.dao.BookListRulesDao;
import com.gongxm.dao.Dao;
import com.gongxm.services.BookListRulesService;
@Service("bookListRulesService")
@Transactional
public class BookListRulesServiceImpl extends BaseService<BookListRules> implements BookListRulesService {
	@Autowired
	BookListRulesDao dao;

	@Override
	public Dao<BookListRules> getDao() {
		return dao;
	}

	@Override
	public List<BookListRules> findAll() {
		return dao.findAll();
	}

}
