package com.gongxm.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gongxm.bean.Book;
import com.gongxm.domain.CategoryItem;
import com.gongxm.domain.request.SearchParam;
import com.gongxm.domain.response.CategoryBookListResp;
import com.gongxm.services.BookService;
import com.gongxm.utils.GsonUtils;

@Controller
@Scope("prototype")
@Namespace("/action")
@ParentPackage("struts-default")
public class SearchAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	BookService bookService;

	@Action("search")
	public void search() {
		SearchParam param = GsonUtils.fromJson(getData(), SearchParam.class);
		CategoryBookListResp result = new CategoryBookListResp();
		if(param!=null) {
			String keyword = param.getKeyword();
			int currentPage = param.getCurrentPage();
			int pageSize = param.getPageSize();
			result.setCurrentPage(currentPage);
			List<Book> books = bookService.findListByKeyword(keyword,currentPage,pageSize);
			if (books != null && books.size() > 0) {
				List<CategoryItem> items = new ArrayList<CategoryItem>();
				for (Book book : books) {
					CategoryItem item = new CategoryItem(book);
					items.add(item);
				}
				result.setResult(items);
				result.setSuccess();
			}
		}
		String json = GsonUtils.toJson(result);
		write(json);
	}

}
