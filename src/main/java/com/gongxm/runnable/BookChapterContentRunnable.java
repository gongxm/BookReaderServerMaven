package com.gongxm.runnable;

import org.hibernate.SessionFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.BookChapter;
import com.gongxm.bean.BookChapterContent;
import com.gongxm.services.BookChapterContentService;
import com.gongxm.services.BookChapterService;
import com.gongxm.utils.ConcurrentUtils;
import com.gongxm.utils.HtmlParser;
import com.gongxm.utils.MyConstants;

public class BookChapterContentRunnable implements Runnable {

	private BookChapter chapter;
	private String contentDivRegex;
	private WebApplicationContext context;

	public BookChapterContentRunnable(BookChapter chapter, String contentDivRegex, WebApplicationContext context) {
		this.chapter = chapter;
		this.contentDivRegex = contentDivRegex;
		this.context = context;
	}

	@Override
	public void run() {
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		boolean participate = ConcurrentUtils.bindHibernateSessionToThread(sessionFactory);
		String url = chapter.getChapter_link();
		BookChapterService service = (BookChapterService) context.getBean("bookChapterService");
		BookChapterContentService chapterContentService = (BookChapterContentService) context
				.getBean("bookChapterContentService");
		try {
			chapter.setStatus(MyConstants.BOOK_COLLECTE_ING);
			service.update(chapter);
			if (MyConstants.DEBUG) {
				System.out.println("colleting:" + chapter.getChapter_name());
			}
			Document doc = HtmlParser.getDocument(url);
			Element e = doc.select(contentDivRegex).first();
			if (e != null) {
				String text = HtmlParser.getPlainText(e);
				BookChapterContent content = new BookChapterContent(chapter.getId(), text);
				chapterContentService.add(content);
				chapter.setStatus(MyConstants.BOOK_COLLECTED);
			} else {
				chapter.setStatus(MyConstants.BOOK_COLLECT_FAILURE);
			}
			service.update(chapter);
			if (MyConstants.DEBUG) {
				System.out.println("collect success..." + chapter.getId());
			}
		} catch (Exception e) {
			if (MyConstants.DEBUG) {
				System.out.println("collect error....");
				e.printStackTrace();
			}
		} finally {
			if (MyConstants.DEBUG) {
				System.out.println("collect finish....");
			}
			int status = chapter.getStatus();
			if (status == MyConstants.BOOK_COLLECTE_ING) {
				chapter.setStatus(MyConstants.BOOK_COLLECT_FAILURE);
				service.update(chapter);
			}
			ConcurrentUtils.closeHibernateSessionFromThread(participate, sessionFactory);
		}
	}

}
