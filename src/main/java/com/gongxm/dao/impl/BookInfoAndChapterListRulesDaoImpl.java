package com.gongxm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.dao.BookInfoAndChapterListRulesDao;

@Repository("bookInfoAndChapterListRulesDao")
public class BookInfoAndChapterListRulesDaoImpl extends BaseDao<BookInfoAndChapterListRules>
		implements BookInfoAndChapterListRulesDao {
	

	@Override
	public List<BookInfoAndChapterListRules> findAll() {
		
		try {
			return qr.query("select * from book_info_and_chapter_list_rules",
					new BeanListHandler<BookInfoAndChapterListRules>(BookInfoAndChapterListRules.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
