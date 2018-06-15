package com.gongxm.quartz;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.services.BookInfoAndChapterListRulesService;

@Component
public class UpdateAllBooks implements Job {
	@Autowired
	public BookInfoAndChapterListRulesService service;

	public static UpdateAllBooks instance; // 关键2

	public UpdateAllBooks() {
	}

	// 关键3
	@PostConstruct
	public void init() {
		instance = this;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("-------定时更新书籍信息-------");
		try {
			List<BookInfoAndChapterListRules> list = instance.service.findAll();
			for (BookInfoAndChapterListRules rules : list) {
				Connection connect = Jsoup.connect("http://127.0.0.1:8080/BookReaderServer/action/collectBookInfo");
				try {
					String json = "{id:"+rules.getId()+",update:true}";
					Response response = connect.requestBody(json).header("Content-Type", "application/json;charset=UTF-8")
							.ignoreContentType(true).method(Method.POST).timeout(5000).execute();
					response.body();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
