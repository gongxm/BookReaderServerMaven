package com.gongxm.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.bean.BookListRules;
import com.gongxm.domain.request.IDParam;
import com.gongxm.domain.response.ResponseResult;
import com.gongxm.services.BookInfoAndChapterListRulesService;
import com.gongxm.services.BookListRulesService;
import com.gongxm.utils.GsonUtils;
import com.gongxm.utils.MyConstants;
import com.gongxm.utils.TextUtils;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
@Namespace("/action")
@ParentPackage("struts-default")
public class BookRulesAction extends BaseAction implements ModelDriven<BookInfoAndChapterListRules> {

	private static final long serialVersionUID = 1L;

	BookInfoAndChapterListRules rules = new BookInfoAndChapterListRules();

	@Autowired
	BookListRulesService rulesService;
	
	@Autowired
	@Qualifier("bookInfoAndChapterListRulesService")
	BookInfoAndChapterListRulesService bscService;

	// 根据BookListRules的ID获取BookInfoAndChapterListRules的内容
	@Action(value = "showBookRules", results = { @Result(name = "success", location = "/WEB-INF/bookRules.jsp") })
	public String showBookRules() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String sid = request.getParameter("id");
		if (TextUtils.notEmpty(sid)) {
			int id = Integer.parseInt(sid);
			BookListRules rules = rulesService.findById(id);
			if (rules != null) {
				BookInfoAndChapterListRules bookRules = rules.getRules();
				HttpSession session = ServletActionContext.getRequest().getSession();
				session.setAttribute("bookListRulesId", id);
				session.setAttribute("bookRules", bookRules);
			}
		}
		return SUCCESS;
	}

	@Action("showAllBookRules")
	public void showAllBookRules() {
		ResponseResult result = new ResponseResult();
		List<BookInfoAndChapterListRules> rules = bscService.findAll();
		result.setResult(rules);
		result.setSuccess();
		String json = GsonUtils.parseToJson(result);
		write(json);
	}
	
	@Action("getBookRules")
	public void getBookRules() {
		IDParam param = GsonUtils.fromJson(getData(), IDParam.class);
		ResponseResult result = new ResponseResult();
		int id = param.getId();
		if(id>0) {
			BookInfoAndChapterListRules rules = bscService.findById(id);
			result.setResult(rules);
			result.setSuccess();
		}
		String json = GsonUtils.parseToJson(result);
		write(json);
	}

	// 更新或者添加新规则
	@Action("updateBookRules")
	public void updateBookRules() {
		ResponseResult result = new ResponseResult();
		if (rules != null) {
			BookListRules bookListRules = rulesService.findById(rules.getBook_list_rules_id());
			if (bookListRules != null) {
				bookListRules.setRules(rules);
				rulesService.update(bookListRules);
				result.setErrcode(MyConstants.SUCCESS);
				result.setErrmsg("修改规则成功!");
			}
		}
		String json = GsonUtils.toJson(result);
		write(json);
	}

	@Override
	public BookInfoAndChapterListRules getModel() {
		return rules;
	}

}
