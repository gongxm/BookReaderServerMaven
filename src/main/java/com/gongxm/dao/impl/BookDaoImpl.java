package com.gongxm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.Book;
import com.gongxm.dao.BookDao;

@Repository("bookDao")
public class BookDaoImpl extends BaseDao<Book> implements BookDao {

	@Override
	public void add(Book book) {
		super.add(book);
		// 把新添加的数据加入solr
		/*SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", book.getId());
		doc.setField("author", book.getAuthor());
		doc.setField("book_link", book.getBook_link());
		doc.setField("book_name", book.getBook_name());
		doc.setField("category", book.getCategory());
		doc.setField("cover", book.getCover());
		doc.setField("shortIntroduce", book.getShortIntroduce());
		doc.setField("status", book.getStatus());
		SolrClient solrClient = getSolrClient(MyConstants.SOLR_QUERY_BOOK_URL);
		try {
			solrClient.add(doc);
			solrClient.commit();
			updateChapters(book);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				solrClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
	
	@Override
	public void update(Book book) {
		Book merge = hqlObj.merge(book);
		super.update(merge);
		//更新对应的章节索引
		updateChapters(book);
	}

	//更新书籍的章节信息
	private void updateChapters(Book book) {
		/*Set<BookChapter> chapters = book.getChapters();
		if(chapters!=null && chapters.size()>0) {
			SolrClient solrClient = getSolrClient(MyConstants.SOLR_QUERY_CHAPTER_URL);
			try {
				for (BookChapter chapter : chapters) {
					SolrInputDocument doc = new SolrInputDocument();
					doc.setField("id", chapter.getId());
					doc.setField("position", chapter.getPosition());
					doc.setField("chapter_name", chapter.getChapter_name());
					doc.setField("chapter_link", chapter.getChapter_link());
					doc.setField("status", chapter.getStatus());
					solrClient.add(doc);
				}
				solrClient.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					solrClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
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
			// criteria.add(Restrictions.like("book_name", keyword, MatchMode.ANYWHERE));
			List<Book> list = (List<Book>) hqlObj.findByCriteria(criteria, (currentPage - 1) * pageSize, pageSize);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Book findByBookUrl(String url) {
		/*SolrClient solrClient = getSolrClient(MyConstants.SOLR_QUERY_BOOK_URL);
		try {
			String escapedKw = ClientUtils.escapeQueryChars(url);
			SolrQuery query = new SolrQuery();
			query.setQuery("book_link:" + escapedKw);
			QueryResponse response = solrClient.query(query);
			SolrDocumentList results = response.getResults();
			if (results != null && results.size() > 0) {
				SolrDocument document = results.get(0);
				Book book = new Book();
				book.setId(Integer.parseInt((String) document.get("id")));
				book.setAuthor((String) document.get("author"));
				book.setBook_link((String) document.get("book_link"));
				book.setBook_name((String) document.get("book_name"));
				book.setCategory((String) document.get("category"));
				book.setCover((String) document.get("cover"));
				book.setShortIntroduce((String) document.get("shortIntroduce"));
				book.setStatus((String) document.get("status"));
				return book;
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
		String sql = "delete from books where id=?";
		sqlObj.update(sql, new Object[] { id }, new int[] { java.sql.Types.INTEGER });
		/*SolrClient solrClient = getSolrClient(MyConstants.SOLR_QUERY_BOOK_URL);
		try {
			solrClient.deleteById(id+"");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				solrClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
}
