package com.gongxm.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.dao.BookInfoAndChapterListRulesDao;

@Repository("bookInfoAndChapterListRulesDao")
public class BookInfoAndChapterListRulesDaoImpl extends BaseDao<BookInfoAndChapterListRules>
		implements BookInfoAndChapterListRulesDao {
	

	@SuppressWarnings("unchecked")
	@Override
	public List<BookInfoAndChapterListRules> findAll() {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(BookInfoAndChapterListRules.class);
			List<BookInfoAndChapterListRules> list = (List<BookInfoAndChapterListRules>) hqlObj.findByCriteria(criteria);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
