package com.gongxm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.BookList;
import com.gongxm.dao.BookListDao;
import com.gongxm.utils.MyConstants;
@Repository("bookListDao")
public class BookListDaoImpl extends BaseDao<BookList> implements BookListDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<BookList> findUnCollectBookListBySource(String book_source, int currentPage, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(BookList.class);
			criteria.add(Restrictions.eq("book_source", book_source));
			criteria.add(Restrictions.eq("status", MyConstants.BOOK_UNCOLLECT));
			List<BookList> list = (List<BookList>) hqlObj.findByCriteria(criteria, (currentPage - 1) * pageSize,pageSize);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookList> findAllUnCollectBookList(int currentPage, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(BookList.class);
			criteria.add(Restrictions.eq("status", MyConstants.BOOK_UNCOLLECT));
			List<BookList> list = (List<BookList>) hqlObj.findByCriteria(criteria, (currentPage - 1) * pageSize,pageSize);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getUnCollectBookListCountBySource(String book_source) {
		try {
			String sql = "select count(*) from book_list where book_source=? and status=?";
			Long count =sqlObj.queryForObject(sql, new Object[] {book_source,MyConstants.BOOK_UNCOLLECT}, Long.class);
			return count;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long getAllUnCollectBookListCount() {
		try {
			String sql = "select count(*) from book_list where status=?";
			Long count =sqlObj.queryForObject(sql, new Object[] {MyConstants.BOOK_UNCOLLECT}, Long.class);
			return count;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public BookList findByBookLink(String bookUrl) {
		String sql = "select * from book_list where book_link=?";
		BookList bookList = null;
		try {
			bookList = sqlObj.queryForObject(sql, new BookListMap(), bookUrl);
		} catch (DataAccessException e) {
			// e.printStackTrace();
		}
		return bookList;
	}
	
	class BookListMap implements RowMapper<BookList>{
		@Override
		public BookList mapRow(ResultSet rs, int index) throws SQLException {
			BookList bookList = new BookList();
			bookList.setBook_link(rs.getString("book_link"));
			bookList.setBook_source(rs.getString("book_source"));
			bookList.setId(rs.getInt("id"));
			bookList.setStatus(rs.getInt("status"));
			return bookList;
		}
	}

	@Override
	public long getBookListCountBySource(String book_source) {
		try {
			String sql = "select count(*) from book_list where book_source=?";
			Long count =sqlObj.queryForObject(sql, new Object[] {book_source}, Long.class);
			return count;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookList> findBookListBySource(String book_source, int currentPage, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(BookList.class);
			criteria.add(Restrictions.eq("book_source", book_source));
			List<BookList> list = (List<BookList>) hqlObj.findByCriteria(criteria, (currentPage - 1) * pageSize,pageSize);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
