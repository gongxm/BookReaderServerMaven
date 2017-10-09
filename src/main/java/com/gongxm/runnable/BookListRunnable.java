package com.gongxm.runnable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.BookList;
import com.gongxm.bean.BookListRules;
import com.gongxm.services.BookListService;
import com.gongxm.utils.CollectUtils;
import com.gongxm.utils.HtmlParser;
import com.gongxm.utils.MyConstants;
import com.gongxm.utils.TextUtils;

public class BookListRunnable implements Runnable {
	private String url;
	private String contentDivClass;
	private String regex;
	private boolean repeat;
	private BookListService service;
	private String book_source;

	public BookListRunnable(WebApplicationContext context, BookListRules bookListRules, String url) {
		this.book_source = bookListRules.getBook_source();
		this.url = url;
		this.contentDivClass = bookListRules.getContentDivClass();
		this.regex = bookListRules.getRegex();
		this.repeat = bookListRules.isRepeat();
		this.service = (BookListService) context.getBean("bookListService");
	}

	@Override
	@Transactional
	public void run() {
		try {
			System.out.println("采集:" + url);
			Document doc = HtmlParser.getDocument(url);
			Element element = doc.select(contentDivClass).first();
			if (element != null) {
				Elements elements = element.getElementsByTag("a");
				for (Element e : elements) {
					String bookUrl = e.absUrl("href");
					if (TextUtils.notEmpty(bookUrl) && bookUrl.matches(regex)) {
						BookList temp = service.findByBookLink(bookUrl);
						if (temp == null) {
							BookList list = new BookList(book_source, bookUrl, MyConstants.BOOK_UNCOLLECT);
							service.add(list);
							Thread.sleep(10);
						}
						if (!repeat) {
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		CollectUtils.threadCount--;
		System.out.println("----完成----");
	}

}
