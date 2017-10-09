package com.gongxm.runnable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.Book;
import com.gongxm.bean.BookChapter;
import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.bean.BookList;
import com.gongxm.services.BookChapterService;
import com.gongxm.services.BookListService;
import com.gongxm.services.BookService;
import com.gongxm.utils.CollectUtils;
import com.gongxm.utils.HtmlParser;
import com.gongxm.utils.MyConstants;

public class BookInfoRunnable implements Runnable {

	private BookList bookList;
	BookService bookService;
	BookChapterService chapterService;
	BookListService service;
	private BookInfoAndChapterListRules rules;

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
	// private String[] regexs;

	public BookInfoRunnable(WebApplicationContext context, BookList bookList, BookInfoAndChapterListRules rules) {
		this.bookList = bookList;
		this.rules = rules;
		this.bookService = (BookService) context.getBean("bookService");
		this.service = (BookListService) context.getBean("bookListService");
		this.chapterService = (BookChapterService) context.getBean("bookChapterService");
	}

	@Override
	public void run() {

		try {
			String url = bookList.getBook_link();
			System.out.println("正在采集:" + url);
			Document doc = HtmlParser.getDocument(url);
			String cover = doc.select(rules.getCoverRegex()).first().attr("content");
			String title = doc.select(rules.getTitleRegex()).first().attr("content");
			String author = doc.select(rules.getAuthorRegex()).first().attr("content");
			String category = doc.select(rules.getCategoryRegex()).first().attr("content");
			String shortIntroduce = doc.select(rules.getShortIntroduceRegex()).first().attr("content");
			String status = doc.select(rules.getStatusRegex()).first().attr("content");
			
			Book book = bookService.findByBookUrl(url);

			if(book==null) {
				book = new Book(title, author, category, status, cover, shortIntroduce, url);
				bookService.add(book);
			}else {
				book = bookService.findById(book.getId());
				book.setStatus(status);
				book.setCover(cover);
				book.setShortIntroduce(shortIntroduce);
				bookService.update(book);
			}
			System.out.println("采集中.." + book.getBook_name());

			Element contentDiv = doc.select(rules.getContentDivClass()).first();

			if (contentDiv != null) {
				Elements elements = contentDiv.select("a");
				if (elements != null && elements.size() > 0) {
					for (int i = 0; i < elements.size(); i++) {
						Element e = elements.get(i);
						String chapterLink = e.absUrl("href");
						String chapterTitle = e.text();
						long start = System.currentTimeMillis();
						BookChapter temp = chapterService.findByChapterLink(chapterLink);
						long end = System.currentTimeMillis();
						System.out.println("耗时:"+(end-start));
						if (temp == null) {
							System.out.println("----采集章节目录-------"+chapterTitle);
							BookChapter bookChapter = new BookChapter(chapterTitle, chapterLink, i,rules);
							book.getChapters().add(bookChapter);
						}
					}
					// 更新书籍状态
					bookService.update(book);
					bookList.setStatus(MyConstants.BOOK_COLLECTED);
				} else {
					bookList.setStatus(MyConstants.BOOK_COLLECT_FAILURE);
				}
				// 更新书籍列表状态
				service.update(bookList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		CollectUtils.threadCount--;
		System.out.println("======采集完成=====");
	}

}
