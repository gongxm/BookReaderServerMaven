package com.gongxm.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@Namespace("/action")
@ParentPackage("struts-default")
public class TestAction extends BaseAction{
	private static final long serialVersionUID = 1L;

	@Action
	public void test() {
		Connection connect = Jsoup.connect("http://127.0.0.1:8080/BookReaderServer/action/updateBook");
		try {
			String data = "{id:SHJDcmhFVmE4S0V5Y0k1aFAxTmZmUT09}";
			Response response = connect.requestBody(data)
					.header("Content-Type", "application/json;charset=UTF-8").ignoreContentType(true)
					.method(Method.POST).timeout(5000).execute();
			response.body();
			write("11111111111111111111");
		} catch (Exception e) {
			e.printStackTrace();
			write("22222222222222222222");
		}
	}
}
