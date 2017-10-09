package com.gongxm.runnable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.Book;
import com.gongxm.bean.BookChapter;
import com.gongxm.bean.BookChapterContent;
import com.gongxm.services.BookChapterService;
import com.gongxm.utils.HtmlParser;
import com.gongxm.utils.MyConstants;

public class BookChapterContentRunnable implements Runnable {

	private BookChapter chapter;
	private String contentDivRegex;
	private BookChapterService service;

	public BookChapterContentRunnable(BookChapter chapter, String contentDivRegex,
			WebApplicationContext context) {
		this.chapter = chapter;
		this.contentDivRegex = contentDivRegex;
		this.service = (BookChapterService) context.getBean("bookChapterService");
	}

	@Override
	public void run() {
		String url = chapter.getChapter_link();
		try {
			System.out.println("colleting:" + chapter.getChapter_name());
			Document doc = HtmlParser.getDocument(url);
			Element e = doc.select(contentDivRegex).first();
			if (e != null) {
				String text = HtmlParser.getPlainText(e);
				BookChapterContent content = new BookChapterContent(text);
				chapter.setChapterContent(content);
				chapter.setStatus(MyConstants.BOOK_COLLECTED);
			} else {
				chapter.setStatus(MyConstants.BOOK_COLLECT_FAILURE);
			}
			synchronized (Book.class) {
				service.update(chapter);
				System.out.println("collect success...");
			}
		} catch (Exception e) {
			System.out.println("collect error....");
			e.printStackTrace();
			chapter.setStatus(MyConstants.BOOK_COLLECT_FAILURE);
			synchronized (Book.class) {
				service.update(chapter);
			}
		}
	}

}
