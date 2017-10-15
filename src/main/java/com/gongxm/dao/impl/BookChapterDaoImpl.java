package com.gongxm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.BookChapter;
import com.gongxm.dao.BookChapterDao;
import com.gongxm.utils.GsonUtils;
import com.gongxm.utils.MyConstants;
import com.gongxm.utils.RedisUtils;
import com.gongxm.utils.TextUtils;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;

@Repository("bookChapterDao")
public class BookChapterDaoImpl extends BaseDao<BookChapter> implements BookChapterDao {

	@Value("${chapter_list}")
	private String chapter_list;

	@Value("${chapter}")
	private String chapter_key;

	@SuppressWarnings("unchecked")
	@Override
	public List<BookChapter> findByBookId(String bookid) {

		// 先从缓存中查询
		try {
			Jedis jedis = RedisUtils.getJedis();
			String json = jedis.hget(chapter_list, bookid);
			jedis.close();
			if (TextUtils.notEmpty(json)) {
				List<BookChapter> list = GsonUtils.fromJson(json, new TypeToken<List<BookChapter>>() {
				}.getType());
				return list;
			}
		} catch (Exception e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}

		List<BookChapter> list = null;
		try {
			String sql = "from BookChapter where bookId=?";
			list = (List<BookChapter>) hqlObj.find(sql, bookid);
		} catch (DataAccessException e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}

		if (list != null) {
			// 把数据添加到缓存中
			try {
				Jedis jedis = RedisUtils.getJedis();
				jedis.hset(chapter_list, bookid, GsonUtils.toJson(list));
				jedis.close();
			} catch (Exception e) {
				if (MyConstants.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	@Override
	public long getUnCollectChapterCount() {
		try {
			String sql = "select count(*) from book_chapters where status=?";
			Long count = sqlObj.queryForObject(sql, new Object[] { MyConstants.BOOK_UNCOLLECT }, Long.class);
			return count;
		} catch (DataAccessException e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookChapter> findUnCollectChapter(int currentPage, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(BookChapter.class);
			criteria.add(Restrictions.eq("status", MyConstants.BOOK_UNCOLLECT));
			List<BookChapter> list = (List<BookChapter>) hqlObj.findByCriteria(criteria, (currentPage - 1) * pageSize,
					pageSize);
			return list;
		} catch (DataAccessException e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public BookChapter findByChapterId(String uuid) {

		// 先从缓存中查询
		try {
			Jedis jedis = RedisUtils.getJedis();
			String json = jedis.hget(chapter_key, uuid);
			jedis.close();
			if (TextUtils.notEmpty(json)) {
				BookChapter chapter = GsonUtils.fromJson(json, BookChapter.class);
				return chapter;
			}
		} catch (Exception e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}

		String sql = "select * from book_chapters where id=?";
		BookChapter chapter = null;
		try {
			chapter = sqlObj.queryForObject(sql, new BookChapterMap(), uuid);
		} catch (DataAccessException e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}

		if (chapter != null) {
			// 把数据添加到缓存中
			try {
				Jedis jedis = RedisUtils.getJedis();
				jedis.hset(chapter_key, uuid, GsonUtils.toJson(chapter));
				jedis.close();
			} catch (Exception e) {
				if (MyConstants.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		return chapter;
	}

	@Override
	public void update(BookChapter chapter) {
		super.update(chapter);

		// 更新缓存
		// 从缓存中删除数据
		try {
			Jedis jedis = RedisUtils.getJedis();
			jedis.hdel(chapter_key, chapter.getId()); // 删除章节列表
			jedis.close();
		} catch (Exception e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}
	}

	class BookChapterMap implements RowMapper<BookChapter> {
		@Override
		public BookChapter mapRow(ResultSet rs, int i) throws SQLException {
			BookChapter chapter = new BookChapter();
			chapter.setId(rs.getString("id"));
			chapter.setChapter_link(rs.getString("chapter_link"));
			chapter.setChapter_name(rs.getString("chapter_name"));
			chapter.setPosition(rs.getInt("position"));
			chapter.setStatus(rs.getInt("status"));
			chapter.setBookId(rs.getString("bookId"));
			chapter.setRulesId(rs.getInt("rulesId"));
			return chapter;
		}

	}

	@Override
	public int findChapterCountByBookId(String bookId) {
		try {
			String sql = "select count(*) from book_chapters where bookId=?";
			Integer count = sqlObj.queryForObject(sql, new Object[] { bookId }, Integer.class);
			return count;
		} catch (DataAccessException e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
