package com.gongxm.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gongxm.bean.Book;
import com.gongxm.domain.request.SearchParam;
import com.gongxm.domain.response.ResponseResult;
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
		ResponseResult result = new ResponseResult();
		if(param!=null) {
			String keyword = param.getKeyword();
			int currentPage = param.getCurrentPage();
			int pageSize = param.getPageSize();
			List<Book> list = bookService.findListByKeyword(keyword,currentPage,pageSize);
			result.setResult(list);
			result.setSuccess();
		}
		String json = GsonUtils.parseToJson(result);
		write(json);
	}

}
