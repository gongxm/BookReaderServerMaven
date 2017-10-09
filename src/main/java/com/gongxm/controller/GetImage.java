package com.gongxm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gongxm.utils.FileUtils;
import com.gongxm.utils.TextUtils;

@WebServlet("/getImage")
public class GetImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cover = req.getParameter("cover");
		 resp.setHeader("Content-Disposition", "attachment;filename=fm.jpg");  
		if(TextUtils.notEmpty(cover)){
			if(cover.startsWith("cover")){
				File dir = new File("D:/BookReaderServer");
				File image = new File(dir, cover);
				if(image.exists()){
					FileInputStream fis = new FileInputStream(image);
					FileUtils.writeFile(fis, resp.getOutputStream());
					fis.close();
					return;
				}
			}
		}
		
		File dir = new File("D:/BookReaderServer");
		File image = new File(dir, "cover/45008s.jpg"); //暂无封面
		if(image.exists()){
			FileInputStream fis = new FileInputStream(image);
			FileUtils.writeFile(fis, resp.getOutputStream());
			fis.close();
		}

	}
}
