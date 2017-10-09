package com.gongxm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gongxm.utils.MyConstants;
import com.gongxm.utils.StringUtils;

/**
 * BaseServlet: Servlet父类
 */
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected WebApplicationContext context;

	public BaseServlet() {
		super();
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		 context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext()); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream is = request.getInputStream();
		String requestJson = StringUtils.readStream(is, MyConstants.DEFAULT_ENCODING);
		// response.setContentType("application/json");
		request.setCharacterEncoding(MyConstants.DEFAULT_ENCODING);
		postRequest(request, response, requestJson);
	}

	public abstract void postRequest(HttpServletRequest request, HttpServletResponse response, String requestJson)
			throws ServletException, IOException;

	public void writeResult(HttpServletResponse response, String json) throws IOException {
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
	}

}
