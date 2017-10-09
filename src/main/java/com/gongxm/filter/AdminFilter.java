package com.gongxm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gongxm.bean.User;
import com.gongxm.utils.MyConstants;

@WebFilter(filterName="AdminFilter",urlPatterns={"/admin","/rulesManagement","/collectManagement"})
public class AdminFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			request = (HttpServletRequest) req;
			response = (HttpServletResponse) resp;
		} catch (Exception e) {
			throw new RuntimeException("non http request or response");
		}
		request.setCharacterEncoding(MyConstants.DEFAULT_ENCODING);
		response.setCharacterEncoding(MyConstants.DEFAULT_ENCODING);
		response.setContentType("text/html;charset=" + MyConstants.DEFAULT_ENCODING);
		
		User user=(User) request.getSession().getAttribute("user");
		if(user==null){
			response.getWriter().write("<h1 align='center'><font color='red' size=5>您还没有登陆,即将转到登陆页面!</font></h1>");
			response.setHeader("refresh", "1;url="+request.getContextPath()+"/login.jsp");
			return;
		}
		if(!MyConstants.ROLE_ROOT.equals(user.getPermissions())){
			response.getWriter().write("<h1 align='center'><font color='blue' size=5>您的权限不足,无法操作,即将转到首页!</font></h1>");
			response.setHeader("refresh", "1;url="+request.getContextPath());
			return;
		}
		if(request.getRequestURI().contains("/admin")){
			request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

}
