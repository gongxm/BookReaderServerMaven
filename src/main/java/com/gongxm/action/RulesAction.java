package com.gongxm.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gongxm.bean.BookListRules;
import com.gongxm.domain.request.IDParam;
import com.gongxm.domain.response.ResponseResult;
import com.gongxm.services.BookListRulesService;
import com.gongxm.utils.GsonUtils;
import com.gongxm.utils.MyConstants;
import com.gongxm.utils.StringConstants;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
@Namespace("/action")
@ParentPackage("struts-default")
public class RulesAction extends BaseAction implements ModelDriven<BookListRules> {

	private static final long serialVersionUID = 1L;

	@Autowired
	BookListRulesService rulesService;

	BookListRules bookListRules = new BookListRules();

	// 显示所有的规则
	@Action(value = "showAllRules", results = { @Result(name = "success", location = "/WEB-INF/rulesManagement.jsp") })
	public String showAllRules() {
		List<BookListRules> rulesList = rulesService.findAll();
		ServletActionContext.getRequest().getSession().setAttribute("rulesList", rulesList);
		return SUCCESS;
	}

	// 添加规则
	@Action(value = "addRules")
	public void addRules() {
		ResponseResult result = new ResponseResult();
		if (bookListRules != null) {
			rulesService.add(bookListRules);
			result.setErrcode(MyConstants.SUCCESS);
			result.setErrmsg("添加规则成功!");
		}
		String json = GsonUtils.toJson(result);
		write(json);
	}

	@Override
	public BookListRules getModel() {
		return bookListRules;
	}

	// 根据ID获取规则内容
	@Action("getRules")
	public void getRules() {
		ResponseResult result = new ResponseResult();
		try {
			IDParam param = GsonUtils.fromJson(getData(), IDParam.class);
			int id = param.getId();
			if (id > 0) {
				BookListRules rules = rulesService.findById(id);
				if (rules != null) {
					result.setResult(rules);
					result.setSuccess();
				} else {
					result.setErrmsg("找不到指定的规则!");
				}
			} else {
				result.setErrmsg(StringConstants.RULES_ID_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrmsg(StringConstants.JSON_PARSE_ERROR);
		}
		String json = GsonUtils.parseToJson(result);
		write(json);
	}

	// 修改规则
	@Action("updateRules")
	public void updateRules() {
		ResponseResult result = new ResponseResult();
		if (bookListRules != null) {
			BookListRules rules = rulesService.findById(bookListRules.getId());
			if (rules != null) {
				BookListRules data = fillData(bookListRules, rules);
				rulesService.update(data);
				result.setErrcode(MyConstants.SUCCESS);
				result.setErrmsg("修改规则成功!");
			}
		}
		String json = GsonUtils.toJson(result);
		write(json);
	}
	
	//填充数据
	private BookListRules fillData(BookListRules src,BookListRules dest) {
		dest.setBaseUrl(src.getBaseUrl());
		dest.setBook_source(src.getBook_source());
		dest.setEndIndex(src.getEndIndex());
		dest.setFlag(src.getFlag());
		dest.setRegex(src.getRegex());
		dest.setRepeat(src.isRepeat());
		dest.setRulesName(src.getRulesName());
		dest.setStartIndex(src.getStartIndex());
		dest.setContentDivClass(src.getContentDivClass());
		return dest;
	}

	// 删除规则
	@Action("delRules")
	public void delRules() {
		ResponseResult result = new ResponseResult();
		try {
			IDParam param = GsonUtils.fromJson(getData(), IDParam.class);
			int id = param.getId();
			if (id > 0) {
				BookListRules rules = rulesService.findById(id);
				if (rules != null) {
					rulesService.delete(rules);
					result.setSuccess();
				} else {
					result.setErrmsg("找不到指定的规则!");
				}
			} else {
				result.setErrmsg(StringConstants.RULES_ID_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrmsg(StringConstants.JSON_PARSE_ERROR);
		}

		String json = GsonUtils.toJson(result);
		write(json);
	}

}
