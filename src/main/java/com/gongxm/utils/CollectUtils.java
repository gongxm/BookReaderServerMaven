package com.gongxm.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.bean.BookList;
import com.gongxm.bean.BookListRules;
import com.gongxm.runnable.BookInfoRunnable;
import com.gongxm.runnable.BookListRunnable;
import com.gongxm.services.BookListService;

//数据采集工具
public class CollectUtils {


	// 书籍列表
	public static void collectBookList(BookListRules bookListRules) {
		new Thread() {
			public void run() {
				System.out.println("======开始采集目录=======");
				List<String> urls = new ArrayList<>();
				String baseUrl = bookListRules.getBaseUrl();
				for (int i = bookListRules.getStartIndex(); i <= bookListRules.getEndIndex(); i++) {
					if (TextUtils.notEmpty(bookListRules.getFlag())) {
						String url = baseUrl.replace(bookListRules.getFlag(), i + "");
						urls.add(url);
					} else {
						urls.add(baseUrl);
					}
				}
				for (String url : urls) {
					BookListRunnable task = new BookListRunnable(bookListRules, url);
					ThreadPoolUtils.executeOnNewThread(task);
					try {
						Thread.sleep(MyConstants.BOOK_COLLECT_INTERVAL);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();

	}

	// 书籍信息
	public static void collectBookInfo(WebApplicationContext context, BookListRules bookListRules, boolean update)
			throws IOException {
		Runnable bookListTask = new Runnable() {
			public void run() {
				BookListService service = (BookListService) context.getBean("bookListService");
				BookInfoAndChapterListRules rules = bookListRules.getRules();
				if (rules == null) {
					System.out.println("缺少书籍信息规则!!!!!!!!!!!!!!");
					return;
				}
				if (update) {
					updateBookInfo(context, bookListRules, service, rules);
				} else {
					collectBookInfo(context, bookListRules, service, rules);
				}
			}
		};

		ThreadPoolUtils.executeOnNewThread(bookListTask);
	}

	

	// 采集书籍信息
	private static void collectBookInfo(WebApplicationContext context, BookListRules bookListRules,
			BookListService service, BookInfoAndChapterListRules rules) {
		System.out.println("============================开始采集书籍信息===============================");
		int currentPage = 1;
		int pageSize = 20;
		String book_source = bookListRules.getBook_source();
		if (TextUtils.notEmpty(book_source)) {
			long total = service.getUnCollectBookListCountBySource(book_source);
			long page = 0;
			if (total % pageSize == 0) {
				page = total / pageSize;
			} else {
				page = total / pageSize + 1;
			}

			while (currentPage <= page) {

				List<BookList> list = service.findUnCollectBookListBySource(book_source, currentPage, pageSize);
				for (BookList bookList : list) {
					BookInfoRunnable task = new BookInfoRunnable(context, bookList.getBook_link(), rules);
					ThreadPoolUtils.executeOnNewThread(task);
				}
				currentPage++;

				try {
					Thread.sleep(MyConstants.BOOK_COLLECT_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} else {
			long total = service.getAllUnCollectBookListCount();
			long page = 0;
			if (total % pageSize == 0) {
				page = total / pageSize;
			} else {
				page = total / pageSize + 1;
			}
			while (currentPage <= page) {
				List<BookList> list = service.findAllUnCollectBookList(currentPage, pageSize);
				for (BookList bookList : list) {
					BookInfoRunnable task = new BookInfoRunnable(context, bookList.getBook_link(), rules);
					ThreadPoolUtils.executeOnNewThread(task);
				}
				currentPage++;

				try {
					Thread.sleep(MyConstants.BOOK_COLLECT_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 采集书籍信息
	private static void updateBookInfo(WebApplicationContext context, BookListRules bookListRules,
			BookListService service, BookInfoAndChapterListRules rules) {
		System.out.println("============================开始更新书籍信息===============================");
		int currentPage = 1;
		int pageSize = 20;

		String book_source = bookListRules.getBook_source();
		if (TextUtils.notEmpty(book_source)) {
			long total = service.getBookListCountBySource(book_source);
			long page = 0;
			if (total % pageSize == 0) {
				page = total / pageSize;
			} else {
				page = total / pageSize + 1;
			}

			while (currentPage <= page) {
				List<BookList> list = service.findBookListBySource(book_source, currentPage, pageSize);
				for (BookList bookList : list) {
					BookInfoRunnable task = new BookInfoRunnable(context, bookList.getBook_link(), rules);
					ThreadPoolUtils.executeOnNewThread(task);
				}
				currentPage++;
				try {
					Thread.sleep(MyConstants.BOOK_COLLECT_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
