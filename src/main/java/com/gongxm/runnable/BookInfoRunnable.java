package com.gongxm.runnable;

import org.hibernate.SessionFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
	private String category;

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

	public BookInfoRunnable(WebApplicationContext context, String bookUrl, BookInfoAndChapterListRules rules, String category) {
		this.bookUrl = bookUrl;
		this.rules = rules;
		this.context = context;
		this.category=category;
	}

	@Override
	public void run() {
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		boolean participate = ConcurrentUtils.bindHibernateSessionToThread(sessionFactory);
		try {
			BookListService bookListService = (BookListService) context.getBean("bookListService");
			BookService bookService = (BookService) context.getBean("bookService");
			if (MyConstants.SHOW_INFO) {
				System.out.println("正在采集:" + bookUrl);
			}
			Document doc = HtmlParser.getDocument(bookUrl);
			String cover = doc.select(rules.getCoverRegex()).first().attr("content");
			String title = doc.select(rules.getTitleRegex()).first().attr("content");
			String author = doc.select(rules.getAuthorRegex()).first().attr("content");
//			String category = doc.select(rules.getCategoryRegex()).first().attr("content");
			String shortIntroduce = doc.select(rules.getShortIntroduceRegex()).first().attr("content");
			String status = doc.select(rules.getStatusRegex()).first().attr("content");
			BookList bookList = bookListService.findByBookLink(bookUrl);
			
			shortIntroduce = shortIntroduce.trim().equals("")?"暂无":shortIntroduce;

			Document doc2 = HtmlParser.getDocument(bookUrl);
			Element contentDiv = doc2.select(rules.getContentDivClass()).first();

			String lastChapter = "暂无";
			long lastUpdateTime = System.currentTimeMillis();
			int chapterCount = 0;
			if (contentDiv != null) {
				Elements elements = contentDiv.select("a");
				if (elements != null && elements.size() > 0) {
					chapterCount = elements.size();
					Element element = elements.get(chapterCount - 1);
					lastChapter = element.text();
				}
			}
			try {
				String bookId = MD5Utils.creatID(bookUrl);
				Book book = bookService.findByBookId(bookId);
				if (book == null) {
					book = new Book(bookId, title, author, category, status, cover, shortIntroduce, bookUrl,
							rules.getId());
					book.setLastChapter(lastChapter);
					book.setLastUpdateTime(lastUpdateTime);
					book.setChapterCount(chapterCount);
					bookService.add(book);
					bookList.setStatus(MyConstants.BOOK_COLLECTED);
				} else {
					book.setLastChapter(lastChapter);
					book.setLastUpdateTime(lastUpdateTime);
					book.setChapterCount(chapterCount);
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
			}
			if (MyConstants.SHOW_INFO) {
				System.out.println("采集出错========"+bookUrl);
			}
		} finally {
			if (MyConstants.SHOW_INFO) {
				System.out.println("======采集完成=====");
			}
			ConcurrentUtils.closeHibernateSessionFromThread(participate, sessionFactory);
		}
	}

}
