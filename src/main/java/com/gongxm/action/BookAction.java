package com.gongxm.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.Book;
import com.gongxm.bean.BookChapter;
import com.gongxm.bean.BookChapterContent;
import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.domain.CategoryItem;
import com.gongxm.domain.request.GetCategoryListParam;
import com.gongxm.domain.request.StringIDParam;
import com.gongxm.domain.response.ResponseResult;
import com.gongxm.runnable.BookChapterContentRunnable;
import com.gongxm.runnable.BookChaptersRunnable;
import com.gongxm.services.BookChapterContentService;
import com.gongxm.services.BookChapterService;
import com.gongxm.services.BookInfoAndChapterListRulesService;
import com.gongxm.services.BookService;
import com.gongxm.utils.GsonUtils;
import com.gongxm.utils.MyConstants;
import com.gongxm.utils.StringConstants;
import com.gongxm.utils.TextUtils;

@Controller
@Scope("prototype")
@Namespace("/action")
@ParentPackage("struts-default")
public class BookAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private BookService bookService;
	@Autowired
	BookChapterService chapterService;
	@Autowired
	BookChapterContentService chapterContentService;
	@Autowired
	private BookInfoAndChapterListRulesService rulesService;

	// 获取书籍详情
	@Action("getBookDetail")
	public void getBookDetail() {
		String data = getData();

		ResponseResult result = new ResponseResult(MyConstants.FAILURE, StringConstants.HTTP_REQUEST_ERROR);
		try {
			StringIDParam param = GsonUtils.fromJson(data, StringIDParam.class);
			if (param != null) {
				String bookid = param.getId();
				if (TextUtils.notEmpty(bookid)) {
					Book book = bookService.findByBookId(bookid);
					if (book != null) {
						result.setErrcode(MyConstants.SUCCESS);
						result.setErrmsg(StringConstants.HTTP_REQUEST_SUCCESS);
						result.setResult(book);
					} else {
						result.setErrmsg(StringConstants.BOOK_NOT_FOUND);
					}
				} else {
					result.setErrmsg(StringConstants.BOOK_ID_ERROR);
				}
			} else {
				result.setErrmsg(StringConstants.HTTP_REQUEST_PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrmsg(StringConstants.JSON_PARSE_ERROR);
		}

		String json = GsonUtils.parseToJson(result);

		write(json);
	}

	// 获取书籍章节内容
	@Action("getBookChapter")
	public void getBookChapter() {
		String data = getData();
		ResponseResult result = new ResponseResult(MyConstants.FAILURE, StringConstants.HTTP_REQUEST_ERROR);
		try {
			StringIDParam param = GsonUtils.fromJson(data, StringIDParam.class);
			if (param != null) {
				String uuid = param.getId();
				if (TextUtils.notEmpty(uuid)) {
					BookChapter chapter = chapterService.findByChapterId(uuid);
					if (chapter != null) {
						int status = chapter.getStatus();
						if (status == MyConstants.BOOK_COLLECTED) {
							BookChapterContent content = chapterContentService.findByChapterId(uuid);
							String text = content.getText();
							if (TextUtils.isEmpty(text)) {
								text = "";
							}
							result.setResult(text);
							result.setErrcode(MyConstants.SUCCESS);
							result.setErrmsg(StringConstants.HTTP_REQUEST_SUCCESS);
						} else if (status == MyConstants.BOOK_COLLECTE_ING) {
							result.setErrmsg(StringConstants.BOOK_COLLECTE_ING);
						} else {
							WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
							BookInfoAndChapterListRules rules = rulesService.findById(chapter.getRulesId());
							BookChapterContentRunnable task = new BookChapterContentRunnable(chapter,
									rules.getContentDivRegex(), context);
							task.run();
							chapter = chapterService.findByChapterId(uuid);
							if (chapter != null) {
								BookChapterContent content = chapterContentService.findByChapterId(uuid);
								String text = content.getText();
								if (TextUtils.isEmpty(text)) {
									text = "";
								}
								result.setResult(text);
								result.setErrcode(MyConstants.SUCCESS);
								result.setErrmsg(StringConstants.HTTP_REQUEST_SUCCESS);
							}
						}
					} else {
						result.setErrmsg(StringConstants.BOOK_CHAPTER_NOT_FOUND);
					}
				} else {
					result.setErrmsg(StringConstants.BOOK_ID_ERROR);
				}
			} else {
				result.setErrmsg(StringConstants.HTTP_REQUEST_PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrmsg(StringConstants.JSON_PARSE_ERROR);
		}

		String json = GsonUtils.parseToJson(result);
		write(json);
	}

	// 获取章节列表
	@Action("getChapterList")
	public void getChapterList() {
		ResponseResult resp = new ResponseResult(MyConstants.FAILURE, StringConstants.HTTP_REQUEST_ERROR);
		try {
			StringIDParam param = GsonUtils.fromJson(getData(), StringIDParam.class);
			if (param != null) {
				String bookid = param.getId();
				if (TextUtils.notEmpty(bookid)) {
					Book book = bookService.findByBookId(bookid);
					if (book != null) {
						int status = book.getCollectStatus();
						if (status == MyConstants.BOOK_UNCOLLECT) {
							WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
							update(context, book);
							List<BookChapter> list = chapterService.findByBookId(bookid);
							if (list != null) {
								Collections.sort(list);
								resp.setErrcode(MyConstants.SUCCESS);
								resp.setErrmsg(StringConstants.HTTP_REQUEST_SUCCESS);
								resp.setResult(list);
							} else {
								resp.setErrmsg(StringConstants.BOOK_CHAPTER_NOT_FOUND);
							}
						} else if (status == MyConstants.BOOK_COLLECTE_ING) {
							resp.setErrmsg(StringConstants.BOOK_COLLECTE_ING);
						} else {
							List<BookChapter> list = chapterService.findByBookId(bookid);
							if (list != null) {
								Collections.sort(list);
								resp.setErrcode(MyConstants.SUCCESS);
								resp.setErrmsg(StringConstants.HTTP_REQUEST_SUCCESS);
								resp.setResult(list);
							} else {
								resp.setErrmsg(StringConstants.BOOK_CHAPTER_NOT_FOUND);
							}
						}
					} else {
						resp.setErrmsg(StringConstants.BOOK_NOT_FOUND);
					}
				} else {
					resp.setErrmsg(StringConstants.BOOK_ID_ERROR);
				}
			} else {
				resp.setErrmsg(StringConstants.HTTP_REQUEST_PARAM_ERROR);
			}

		} catch (Exception e) {
			resp.setErrmsg(StringConstants.JSON_PARSE_ERROR);
		}

		String json = GsonUtils.parseToJson(resp);
		write(json);
	}

	// 获取书籍类型
	@Action("getBookCategory")
	public void getBookCategory() {
		ResponseResult result = new ResponseResult(MyConstants.FAILURE, StringConstants.HTTP_REQUEST_ERROR);

		List<String> categories = bookService.getBookCategory();

		String json = "[]";
		if (categories != null) {
			result.setErrcode(MyConstants.SUCCESS);
			result.setErrmsg(StringConstants.HTTP_REQUEST_SUCCESS);
			result.setResult(categories);
			json = GsonUtils.toJson(result);
		}

		write(json);
	}

	// 获取书籍类型列表数据
	@Action("getCategoryList")
	public void getCategoryList() {
		String data = getData();
		ResponseResult resp = new ResponseResult(MyConstants.FAILURE, StringConstants.HTTP_REQUEST_ERROR);
		try {
			GetCategoryListParam param = GsonUtils.fromJson(data, GetCategoryListParam.class);
			if (param != null) {
				String category = param.getCategory();
				int currentPage = param.getCurrentPage();
				int pageSize = param.getPageSize();
				List<Book> books = bookService.getCategoryList(category, currentPage, pageSize);
				if (books != null && books.size() > 0) {
					List<CategoryItem> items = new ArrayList<CategoryItem>();
					for (Book book : books) {
						CategoryItem item = new CategoryItem(book);
						items.add(item);
					}
					resp.setResult(items);
				}
				resp.setErrcode(MyConstants.SUCCESS);
				resp.setErrmsg(StringConstants.HTTP_REQUEST_SUCCESS);
			} else {
				resp.setErrmsg(StringConstants.HTTP_REQUEST_PARAM_ERROR);
			}

		} catch (Exception e) {
			resp.setErrmsg(StringConstants.JSON_PARSE_ERROR);
		}

		String json = GsonUtils.toJson(resp);
		write(json);
	}

	// 更新指定书籍
	@Action("updateBook")
	public void updateBook() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		ResponseResult resp = new ResponseResult(MyConstants.FAILURE, StringConstants.HTTP_REQUEST_ERROR);
		try {
			StringIDParam param = GsonUtils.fromJson(getData(), StringIDParam.class);
			if (param != null) {
				String bookid = param.getId();
				if (TextUtils.notEmpty(bookid)) {
					Book book = bookService.findByBookId(bookid);
					if (book != null) {
						if (book.getCollectStatus() == MyConstants.BOOK_COLLECTE_ING) {
							resp.setErrmsg(StringConstants.BOOK_COLLECTE_ING);
						} else {
							update(context, book);
							resp.setErrcode(MyConstants.SUCCESS);
							resp.setErrmsg(StringConstants.BOOK_UPDATE_SUCCESS);
						}
					} else {
						resp.setErrmsg(StringConstants.BOOK_NOT_FOUND);
					}
				} else {
					resp.setErrmsg(StringConstants.BOOK_ID_ERROR);
				}
			} else {
				resp.setErrmsg(StringConstants.HTTP_REQUEST_PARAM_ERROR);
			}

		} catch (Exception e) {
			resp.setErrmsg(StringConstants.JSON_PARSE_ERROR);
		}

		String json = GsonUtils.parseToJson(resp);
		write(json);
	}

	// 更新书籍
	private void update(WebApplicationContext context, Book book) {
		int rulesId = book.getRulesId();
		BookInfoAndChapterListRules rules = rulesService.findById(rulesId);
		BookChaptersRunnable task = new BookChaptersRunnable(context, book.getBook_link(), rules, true);
		task.run();
	}
}
