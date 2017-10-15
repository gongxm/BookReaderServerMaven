package com.gongxm.runnable;

import org.hibernate.SessionFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.Book;
import com.gongxm.bean.BookChapter;
import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.services.BookChapterService;
import com.gongxm.services.BookService;
import com.gongxm.utils.ConcurrentUtils;
import com.gongxm.utils.HtmlParser;
import com.gongxm.utils.MD5Utils;
import com.gongxm.utils.MyConstants;

public class BookChaptersRunnable implements Runnable {
	private boolean update;
	private String bookUrl;
	private BookInfoAndChapterListRules rules;
	private WebApplicationContext context;

	public BookChaptersRunnable(WebApplicationContext context, String bookUrl, BookInfoAndChapterListRules rules,
			boolean update) {
		this.bookUrl = bookUrl;
		this.rules = rules;
		this.context = context;
		this.update = update;
	}

	@Override
	public void run() {
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		boolean participate = ConcurrentUtils.bindHibernateSessionToThread(sessionFactory);
		BookChapterService chapterService = (BookChapterService) context.getBean("bookChapterService");
		BookService bookService = (BookService) context.getBean("bookService");
		String bookId = MD5Utils.creatID(bookUrl);
		Book book = bookService.findByBookId(bookId);
		try {
			book.setCollectStatus(MyConstants.BOOK_COLLECTE_ING);
			bookService.update(book);
			if (MyConstants.DEBUG) {
				System.out.println("采集中.." + book.getBook_name());
			}
			Document doc = HtmlParser.getDocument(bookUrl);
			Element contentDiv = doc.select(rules.getContentDivClass()).first();

			if (contentDiv != null) {
				Elements elements = contentDiv.select("a");

				if (elements != null && elements.size() > 0) {
					int startIndex = 0;
					if (update) {
						int count = chapterService.findChapterCountByBookId(book.getId());
						if (count >= elements.size()) {
							return;
						}
						startIndex = count;
					}

					for (int i = startIndex; i < elements.size(); i++) {
						Element e = elements.get(i);
						String chapterLink = e.absUrl("href");
						String chapterTitle = e.text();
						String uuid = MD5Utils.creatID(chapterLink);
						if (MyConstants.DEBUG) {
							System.out.println("----采集章节目录-------" + chapterTitle);
						}
						try {
							BookChapter bookChapter = new BookChapter(uuid, chapterTitle, chapterLink, i, book.getId(),
									rules.getId());
							chapterService.add(bookChapter);
						} catch (Exception e1) {
							if (MyConstants.DEBUG) {
								e1.printStackTrace();
								System.out.println("出错链接:" + chapterLink + ", md5=" + uuid);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			book.setCollectStatus(MyConstants.BOOK_COLLECTED);
			bookService.update(book);
			ConcurrentUtils.closeHibernateSessionFromThread(participate, sessionFactory);
			if (MyConstants.DEBUG) {
				System.out.println("采集完成..." + book.getBook_name());
			}
		}
	}

}
