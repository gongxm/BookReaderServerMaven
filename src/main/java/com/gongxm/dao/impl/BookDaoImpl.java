package com.gongxm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.Book;
import com.gongxm.dao.BookDao;
import com.gongxm.utils.GsonUtils;
import com.gongxm.utils.MyConstants;
import com.gongxm.utils.RedisUtils;
import com.gongxm.utils.TextUtils;

import redis.clients.jedis.Jedis;

@Repository("bookDao")
public class BookDaoImpl extends BaseDao<Book> implements BookDao {

	@Value("${book_list}")
	private String book_list;

	@Override
	public void add(Book book) {
		super.add(book);
		// 把数据添加到缓存中
		try {
			Jedis jedis = RedisUtils.getJedis();
			jedis.hset(book_list, book.getBook_link(), GsonUtils.toJson(book));
			jedis.close();
		} catch (Exception e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Book book) {
		super.update(book);
		// 从缓存中删除数据
		try {
			Jedis jedis = RedisUtils.getJedis();
			jedis.hdel(book_list, book.getBook_link());
			jedis.close();
		} catch (Exception e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<String> getBookCategory() {
		try {
			String sql = "select distinct category from books";
			List<String> list = sqlObj.queryForList(sql, String.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getCategoryList(String category, int currentPage, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Book.class);
			criteria.add(Restrictions.eq("category", category));
			List<Book> list = (List<Book>) hqlObj.findByCriteria(criteria, (currentPage - 1) * pageSize, pageSize);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> findListByKeyword(String keyword, int currentPage, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Book.class);
			criteria.add(Restrictions.or(Restrictions.like("book_name", keyword, MatchMode.ANYWHERE),
					Restrictions.like("author", keyword, MatchMode.ANYWHERE)));
			List<Book> list = (List<Book>) hqlObj.findByCriteria(criteria, (currentPage - 1) * pageSize, pageSize);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	@Override
	public Book findByBookUrl(String url) {

		// 先从缓存中查询
		try {
			Jedis jedis = RedisUtils.getJedis();
			String json = jedis.hget(book_list, url);
			jedis.close();
			if (TextUtils.notEmpty(json)) {
				Book book = GsonUtils.fromJson(json, Book.class);
				return book;
			}
		} catch (Exception e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}

		String sql = "select * from books where book_link=?";
		Book book = null;
		try {
			book = sqlObj.queryForObject(sql, new BookMap(), url);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		if (book != null) {
			// 把数据添加到缓存中
			try {
				Jedis jedis = RedisUtils.getJedis();
				jedis.hset(book_list, url, GsonUtils.toJson(book));
				jedis.close();
			} catch (Exception e) {
				if (MyConstants.DEBUG) {
					e.printStackTrace();
				}
			}
		}
		return book;
	}

	class BookMap implements RowMapper<Book> {
		@Override
		public Book mapRow(ResultSet rs, int i) throws SQLException {
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setAuthor(rs.getString("author"));
			book.setBook_link(rs.getString("book_link"));
			book.setBook_name(rs.getString("book_name"));
			book.setCategory(rs.getString("category"));
			book.setCover(rs.getString("cover"));
			book.setShortIntroduce(rs.getString("shortIntroduce"));
			book.setStatus(rs.getString("status"));
			return book;
		}

	}
	
	@Override
	public void deleteById(int id) {

		Book book = findById(id);
		if (book != null) {
			delete(book);
			// 从缓存中删除数据
			try {
				Jedis jedis = RedisUtils.getJedis();
				jedis.hdel(book_list, book.getBook_link());
				jedis.close();
			} catch (Exception e) {
				if (MyConstants.DEBUG) {
					e.printStackTrace();
				}
			}
		}
	}
}
