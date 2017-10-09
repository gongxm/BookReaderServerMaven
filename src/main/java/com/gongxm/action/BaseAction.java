package com.gongxm.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gongxm.utils.MyConstants;
import com.gongxm.utils.StringUtils;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	public String getData() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			ServletInputStream is = request.getInputStream();
			String json = StringUtils.readStream(is);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void write(String json) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding(MyConstants.DEFAULT_ENCODING);
			response.setContentType("application/json");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
