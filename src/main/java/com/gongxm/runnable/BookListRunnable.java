package com.gongxm.runnable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.BookList;
import com.gongxm.bean.BookListRules;
import com.gongxm.services.BookListService;
import com.gongxm.utils.HtmlParser;
import com.gongxm.utils.MyConstants;
import com.gongxm.utils.TextUtils;

public class BookListRunnable implements Runnable {
	private String url;
	private String contentDivClass;
	private String regex;
	private boolean repeat;
	private String book_source;

	public BookListRunnable(BookListRules bookListRules, String url) {
		this.book_source = bookListRules.getBook_source();
		this.url = url;
		this.contentDivClass = bookListRules.getContentDivClass();
		this.regex = bookListRules.getRegex();
		this.repeat = bookListRules.isRepeat();
	}

	@Override
	@Transactional
	public void run() {
		try {
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			BookListService service = (BookListService) context.getBean("bookListService");
			if (MyConstants.SHOW_INFO) {
				System.out.println("采集:" + url);
			}
			Document doc = HtmlParser.getDocument(url);
			Element element = doc.select(contentDivClass).first();
			if (element != null) {
				Elements elements = element.getElementsByTag("a");
				for (Element e : elements) {
					String bookUrl = e.absUrl("href");
					if (TextUtils.notEmpty(bookUrl) && bookUrl.matches(regex)) {
						synchronized (BookListRunnable.class) {
							BookList temp = service.findByBookLink(bookUrl);
							if (temp == null) {
								BookList list = new BookList(book_source, bookUrl, MyConstants.BOOK_UNCOLLECT);
								service.add(list);
							}
						}
						if (!repeat) {
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (MyConstants.SHOW_INFO) {
				System.out.println("----完成----");
			}
		}

	}

}
