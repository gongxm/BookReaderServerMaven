package com.gongxm.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gongxm.bean.User;
import com.gongxm.services.UserService;
import com.gongxm.utils.BeanFillUtils;
import com.gongxm.utils.MD5Util;
import com.gongxm.utils.MyConstants;

/**
 */
@WebServlet("/registServlet")
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserService userService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		 WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext()); 
		 userService = (UserService) context.getBean("userService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer=response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String repassword=request.getParameter("repassword");
		User user=BeanFillUtils.fillBean(request);
		if(!password.equals(repassword)){
			writer.write("<h1 align='center'><font color='red' size=5>两次输入的密码不相同!</font><br/></h1>");
			request.getSession().setAttribute("user", user);
			response.setHeader("refresh", "2;url="+request.getContextPath()+"/regist.jsp");
			return;
		}
		User oldUser=userService.findUserByName(username);
		if(oldUser!=null){
			writer.write("<h1 align='center'><font color='red' size=5>用户已被占用!</font><br/></h1>");
			response.setHeader("refresh", "2;url="+request.getContextPath()+"/regist.jsp");
			return;
		}
		User newUser=new User();
		newUser.setUsername(username);
		newUser.setPermissions(MyConstants.ROLE_USER);
		newUser.setPassword(MD5Util.MD5(password));
		userService.addUser(newUser);
		writer.write("<h1 align='center'><font color='green' size=5>注册成功!</font><br/></h1>");
		response.setHeader("refresh", "2;url="+request.getContextPath()+"/login.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
