package com.gongxm.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import com.gongxm.domain.request.IDParam;
import com.gongxm.domain.response.ResponseResult;
import com.gongxm.runnable.BookChapterContentRunnable;
import com.gongxm.services.BookChapterService;
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

	// 获取书籍详情
	@Action("getBookDetail")
	public void getBookDetail() {
		String data = getData();

		ResponseResult result = new ResponseResult(MyConstants.FAILURE, StringConstants.HTTP_REQUEST_ERROR);
		try {
			IDParam param = GsonUtils.fromJson(data, IDParam.class);
			if (param != null) {
				int id = param.getId();
				if (id > 0) {
					Book book = bookService.findById(id);
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
			IDParam param = GsonUtils.fromJson(data, IDParam.class);
			if (param != null) {
				int id = param.getId();
				if (id > 0) {
					BookChapter chapter = chapterService.findById(id);
					if (chapter != null) {
						if (chapter.getStatus() == MyConstants.BOOK_COLLECTED) {
							BookChapterContent content = chapter.getChapterContent();
							String text = content.getText();
							if (TextUtils.isEmpty(text)) {
								text = "";
							}
							result.setResult(text);
							result.setErrcode(MyConstants.SUCCESS);
							result.setErrmsg(StringConstants.HTTP_REQUEST_SUCCESS);
						} else {
							WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
							BookInfoAndChapterListRules rules = chapter.getRules();
							BookChapterContentRunnable task = new BookChapterContentRunnable(chapter, rules.getContentDivRegex(), context);
							task.run();
							chapter = chapterService.findById(id);
							if (chapter != null) {
								BookChapterContent content = chapter.getChapterContent();
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
		String data = getData();
		ResponseResult resp = new ResponseResult(MyConstants.FAILURE, StringConstants.HTTP_REQUEST_ERROR);
		try {
			IDParam param = GsonUtils.fromJson(data, IDParam.class);
			if (param != null) {
				int bookid = param.getId();
				if (bookid > 0) {
					Book book = bookService.findById(bookid);
					if(book!=null) {
						Set<BookChapter> set =book.getChapters();
						if (set != null) {
							List<BookChapter> list = new ArrayList<BookChapter>();
							list.addAll(set);
							Collections.sort(list);
							resp.setErrcode(MyConstants.SUCCESS);
							resp.setErrmsg(StringConstants.HTTP_REQUEST_SUCCESS);
							resp.setResult(list);
						}else {
							resp.setErrmsg(StringConstants.BOOK_CHAPTER_NOT_FOUND);
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

	// 获取书籍类型列表
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
}
