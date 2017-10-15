package com.gongxm.runnable;

import org.hibernate.SessionFactory;
import org.jsoup.nodes.Document;
import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.Book;
import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.bean.BookList;
import com.gongxm.services.BookListService;
import com.gongxm.services.BookService;
import com.gongxm.utils.ConcurrentUtils;
import com.gongxm.utils.HtmlParser;
import com.gongxm.utils.MD5Utils;
import com.gongxm.utils.MyConstants;

public class BookInfoRunnable implements Runnable {
	private BookInfoAndChapterListRules rules;
	private WebApplicationContext context;
	private String bookUrl;

	// 标题
	// String titleRegex;
	// // 作者
	// String authorRegex;
	// // 类别
	// String categoryRegex;
	// // 状态
	// String statusRegex;
	// // 封面
	// String coverRegex;
	// // 简介
	// String shortIntroduceRegex;

	public BookInfoRunnable(WebApplicationContext context, String bookUrl, BookInfoAndChapterListRules rules) {
		this.bookUrl = bookUrl;
		this.rules = rules;
		this.context = context;
	}

	@Override
	public void run() {
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		boolean participate = ConcurrentUtils.bindHibernateSessionToThread(sessionFactory);
		try {
			BookListService bookListService = (BookListService) context.getBean("bookListService");
			BookService bookService = (BookService) context.getBean("bookService");
			if (MyConstants.DEBUG) {
				System.out.println("正在采集:" + bookUrl);
			}
			Document doc = HtmlParser.getDocument(bookUrl);
			String cover = doc.select(rules.getCoverRegex()).first().attr("content");
			String title = doc.select(rules.getTitleRegex()).first().attr("content");
			String author = doc.select(rules.getAuthorRegex()).first().attr("content");
			String category = doc.select(rules.getCategoryRegex()).first().attr("content");
			String shortIntroduce = doc.select(rules.getShortIntroduceRegex()).first().attr("content");
			String status = doc.select(rules.getStatusRegex()).first().attr("content");
			BookList bookList = bookListService.findByBookLink(bookUrl);
			try {
				String bookId = MD5Utils.creatID(bookUrl);
				Book book = bookService.findByBookId(bookId);
				if (book == null) {
					book = new Book(bookId, title, author, category, status, cover, shortIntroduce, bookUrl,
							rules.getId());
					bookService.add(book);
					bookList.setStatus(MyConstants.BOOK_COLLECTED);
				} else {
					book.setStatus(status);
					book.setCover(cover);
					book.setShortIntroduce(shortIntroduce);
					// 更新书籍状态
					bookService.update(book);
					bookList.setStatus(MyConstants.BOOK_COLLECTED);
				}
			} catch (Exception e) {
				e.printStackTrace();
				bookList.setStatus(MyConstants.BOOK_COLLECT_FAILURE);
			}
			// 更新书籍列表状态
			bookListService.update(bookList);
		} catch (Exception e) {
			if (MyConstants.DEBUG) {
				e.printStackTrace();
				System.out.println("采集出错======================================================");
			}
		} finally {
			if (MyConstants.DEBUG) {
				System.out.println("======采集完成=====");
			}
			ConcurrentUtils.closeHibernateSessionFromThread(participate, sessionFactory);
		}
	}

}
