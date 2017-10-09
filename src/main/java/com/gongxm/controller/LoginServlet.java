package com.gongxm.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gongxm.bean.User;
import com.gongxm.services.UserService;
import com.gongxm.utils.MD5Util;
import com.gongxm.utils.MyConstants;

@WebServlet(description = "用户登陆", urlPatterns = { "/loginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserService userService;
	@Override
	public void init() throws ServletException {
		super.init();
		 WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext()); 
		 userService = (UserService) context.getBean("userService");
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Util.MD5(password);
		
		User user = userService.findUser(username, password);
		
		if (user == null) {
			writer.write("<h1 align='center'><font color='red' size=5>登陆失败,用户名或密码错误!</font></h1>");
			response.setHeader("refresh", "1;url=" + request.getContextPath()
					+ "/login.jsp");
			return;
		}
		if (request.getParameter(MyConstants.REMEMBER) != null) {
			Cookie cookie = new Cookie("user", MD5Util.base64Encoding(username)
					+ "_" + password);
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookie);
		}
		request.getSession().setAttribute("user", user);
		if (MyConstants.ROLE_ROOT.equals(user.getPermissions())) {
			writer.write("<h1 align='center'><font color='green' size=5>登陆成功,即将转到后台管理页面...</font></h1>");
			response.setHeader("refresh", "1;url=" + request.getContextPath() + "/admin");
		} else {
			writer.write("<h1 align='center'><font color='green' size=5>登陆成功,即将转到首页...</font></h1>");
			response.setHeader("refresh", "1;url=" + request.getContextPath()
					+ "/index.jsp");
		}
	}


}
