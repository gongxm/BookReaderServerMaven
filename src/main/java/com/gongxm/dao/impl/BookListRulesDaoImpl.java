package com.gongxm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.BookListRules;
import com.gongxm.dao.BookListRulesDao;

@Repository
public class BookListRulesDaoImpl extends BaseDao<BookListRules> implements BookListRulesDao {

	@Override
	public List<BookListRules> findAll() {
		try {
			return qr.query("select * from book_list_rules",
					new BeanListHandler<BookListRules>(BookListRules.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	class BookListRulesMap implements RowMapper<BookListRules> {

		@Override
		public BookListRules mapRow(ResultSet rs, int i) throws SQLException {
			BookListRules rules = new BookListRules();
			rules.setBaseUrl(rs.getString("baseUrl"));
			rules.setBook_source(rs.getString("book_source"));
			rules.setEndIndex(rs.getInt("endIndex"));
			rules.setFlag(rs.getString("flag"));
			rules.setId(rs.getInt("id"));
			rules.setRegex(rs.getString("regex"));
			rules.setRepeat(rs.getBoolean("isRepeat"));
			rules.setRulesName(rs.getString("rulesName"));
			rules.setStartIndex(rs.getInt("startIndex"));
			rules.setContentDivClass(rs.getString("contentDivClass"));
			return rules;
		}
		
	}

}
