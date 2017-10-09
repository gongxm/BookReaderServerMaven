package com.gongxm.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.gongxm.services.BookService;

@Controller
@Scope("prototype")
@Namespace("/action")
@ParentPackage("struts-default")
public class TestAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	BookService service;

	@Action("test")
	public void deleteBook() {
		/*List<Book> list = service.findListByKeyword("暂无", 1, 20);
		System.out.println("size = "+list.size());
		for (Book book : list) {
			System.out.println(book);
			service.delete(book);
		}*/
	}
}
