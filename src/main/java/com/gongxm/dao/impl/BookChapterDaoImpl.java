package com.gongxm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.BookChapter;
import com.gongxm.dao.BookChapterDao;
import com.gongxm.utils.MyConstants;

@Repository("bookChapterDao")
public class BookChapterDaoImpl extends BaseDao<BookChapter> implements BookChapterDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<BookChapter> findByBookId(int bookid) {
		try {
			String sql = "from BookChapter where book_id=?";
			List<BookChapter> list = (List<BookChapter>) hqlObj.find(sql, bookid);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getUnCollectChapterCount() {
		try {
			String sql = "select count(*) from book_chapters where status=?";
			Long count =sqlObj.queryForObject(sql, new Object[] {MyConstants.BOOK_UNCOLLECT}, Long.class);
			return count;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookChapter> findUnCollectChapter(int currentPage, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(BookChapter.class);
			criteria.add(Restrictions.eq("status", MyConstants.BOOK_UNCOLLECT));
			List<BookChapter> list = (List<BookChapter>) hqlObj.findByCriteria(criteria, (currentPage - 1) * pageSize,pageSize);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BookChapter findByChapterLink(String chapterLink) {
		/*SolrClient solrClient = getSolrClient(MyConstants.SOLR_QUERY_CHAPTER_URL);
		try {
			
			String escapedKw = ClientUtils.escapeQueryChars(chapterLink);
			SolrQuery query = new SolrQuery();
			query.set("shards", MyConstants.SOLR_QUERY_CHAPTER_URL);
			query.setQuery("chapter_link:" + escapedKw);
			QueryResponse response = solrClient.query(query);
			SolrDocumentList results = response.getResults();
			if (results != null && results.size() > 0) {
				SolrDocument document = results.get(0);
				BookChapter chapter = new BookChapter();
				chapter.setId(Integer.parseInt((String) document.get("id")));
				chapter.setChapter_link((String) document.get("chapter_link"));
				chapter.setChapter_name((String) document.get("chapter_name"));
				chapter.setPosition((int) document.get("position"));
				chapter.setStatus((int) document.get("status"));
				return chapter;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				solrClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		return null;
	}
	
	class BookChapterMap  implements RowMapper<BookChapter> {
		@Override
		public BookChapter mapRow(ResultSet rs, int i) throws SQLException {
			BookChapter chapter = new BookChapter();
			chapter.setId(rs.getInt("id"));
			chapter.setChapter_link(rs.getString("chapter_link"));
			chapter.setChapter_name(rs.getString("chapter_name"));
			chapter.setPosition(rs.getInt("position"));
			chapter.setStatus(rs.getInt("status"));
			return chapter;
		}
		
	}
}
