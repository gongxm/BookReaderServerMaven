package com.gongxm.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.BookChapter;
import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.bean.BookList;
import com.gongxm.bean.BookListRules;
import com.gongxm.runnable.BookChapterContentRunnable;
import com.gongxm.runnable.BookInfoRunnable;
import com.gongxm.runnable.BookListRunnable;
import com.gongxm.services.BookChapterService;
import com.gongxm.services.BookListService;

//数据采集工具
public class CollectUtils {

	public static volatile int threadCount;
	public static volatile boolean collecting;

	// 书籍列表
	public static void collectBookList(WebApplicationContext context, BookListService service,
			BookChapterService chapterService, BookListRules bookListRules, boolean collectAll, boolean update) {
		collecting = true;
		threadCount = 0;
		new Thread() {
			public void run() {
				System.out.println("======开始采集目录=======");
				List<String> urls = new ArrayList<>();
				String baseUrl = bookListRules.getBaseUrl();
				threadCount++;
				for (int i = bookListRules.getStartIndex(); i <= bookListRules.getEndIndex(); i++) {
					if (TextUtils.notEmpty(bookListRules.getFlag())) {
						String url = baseUrl.replace(bookListRules.getFlag(), i + "");
						urls.add(url);
					} else {
						urls.add(baseUrl);
					}
				}
				for (String url : urls) {
					threadCount++;
					BookListRunnable task = new BookListRunnable(context, bookListRules, url);
					ThreadPoolUtil.executeOnNewThread(task);
					try {
						Thread.sleep(MyConstants.BOOK_COLLECT_INTERVAL);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				threadCount--;
			};
		}.start();

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (threadCount > 0) {
					System.out.println("还在采集目录中.......");
				} else {
					collecting = false;
					if (collectAll) {
						try {
							collectBookInfo(context, bookListRules, true, update);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					this.cancel();
					timer.cancel();
				}
			}
		};

		timer.schedule(task, MyConstants.DELAY_TIME);

	}

	// 书籍信息和章节列表
	public static void collectBookInfo(WebApplicationContext context, BookListRules bookListRules, boolean collectAll,
			boolean update) throws IOException {
		collecting = true;

		Runnable bookListTask = new Runnable() {
			public void run() {
				threadCount = 1;
				BookListService service = (BookListService) context.getBean("bookListService");
				BookInfoAndChapterListRules rules = bookListRules.getRules();
				if (rules == null) {
					System.out.println("缺少书籍信息规则!!!!!!!!!!!!!!");
					threadCount = 0;
					return;
				}
				if (update) {
					updateBookInfo(context, bookListRules, service, rules);
				} else {
					collectBookInfo(context, bookListRules, service, rules);
				}
			}
		};

		ThreadPoolUtil.executeOnNewThread(bookListTask);

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (threadCount > 0) {
					System.out.println("还在采集书籍信息中.......");
				} else {
					collecting = false;

					if (collectAll) {
						BookInfoAndChapterListRules rules = bookListRules.getRules();
						collectBookChapter(context, rules.getContentDivRegex());
					}
					this.cancel();
					timer.cancel();
				}
			}
		};
		timer.schedule(task, MyConstants.DELAY_TIME);
	}

	// 书籍章节内容
	public static void collectBookChapter(WebApplicationContext context, String contentDivRegex) {

		System.out.println("==============开始采集章节内容====================");
		collecting = true;

		Runnable chapterTask = new Runnable() {
			public void run() {
				threadCount++;
				BookChapterService service = (BookChapterService) context.getBean("bookChapterService");
				int currentPage = 1;
				int pageSize = 20;
				long total = service.getUnCollectChapterCount();

				long page = 0;
				if (total % pageSize == 0) {
					page = total / pageSize;
				} else {
					page = total / pageSize + 1;
				}

				while (currentPage <= page) {
					List<BookChapter> chapters = service.findUnCollectChapter(currentPage, pageSize);
					for (BookChapter chapter : chapters) {
						BookChapterContentRunnable task = new BookChapterContentRunnable(chapter, contentDivRegex,
								context);
						ThreadPoolUtil.executeOnNewThread(task);
					}
					currentPage++;

					try {
						Thread.sleep(MyConstants.BOOK_COLLECT_INTERVAL);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				threadCount--;
			};
		};

		ThreadPoolUtil.executeOnNewThread(chapterTask);

		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				if (threadCount > 0) {
					System.out.println("还在采集章节内容中.......");
				} else {
					System.out.println("................................章节内容采集完成................................");
					collecting = false;
					this.cancel();
					timer.cancel();
				}
			}
		};
		timer.schedule(timerTask, MyConstants.DELAY_TIME);
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
					threadCount++;
					BookInfoRunnable task = new BookInfoRunnable(context, bookList, rules);
					ThreadPoolUtil.executeOnNewThread(task);
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
					threadCount++;
					BookInfoRunnable task = new BookInfoRunnable(context, bookList, rules);
					ThreadPoolUtil.executeOnNewThread(task);
				}
				currentPage++;

				try {
					Thread.sleep(MyConstants.BOOK_COLLECT_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		threadCount--;
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
					threadCount++;
					BookInfoRunnable task = new BookInfoRunnable(context, bookList, rules);
					ThreadPoolUtil.executeOnNewThread(task);
				}
				currentPage++;
				try {
					Thread.sleep(MyConstants.BOOK_COLLECT_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		threadCount--;
	}

}
