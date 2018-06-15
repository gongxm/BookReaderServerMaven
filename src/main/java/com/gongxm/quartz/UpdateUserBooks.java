package com.gongxm.quartz;

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

import com.gongxm.bean.UserConfig;
import com.gongxm.services.UserConfigService;
import com.gongxm.utils.GsonUtils;
import com.gongxm.utils.MyConstants;
import com.gongxm.utils.TextUtils;
import com.google.gson.reflect.TypeToken;

@Component
public class UpdateUserBooks implements Job {

	@Autowired
	UserConfigService userConfigService;

	public static UpdateUserBooks instance; // 关键2

	public UpdateUserBooks() {
	}

	// 关键3
	@PostConstruct
	public void init() {
		instance = this;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("-------定时更新用户书籍列表信息-------");
		List<UserConfig> list = instance.userConfigService.findAll();

		for (UserConfig uc : list) {
			String json = uc.getStore();

			if (TextUtils.notEmpty(json)) {
				List<String> ids = GsonUtils.fromJson(json, new TypeToken<List<String>>() {
				}.getType());
				if (ids != null) {
					for (String bookid : ids) {
						Connection connect = Jsoup.connect("http://127.0.0.1:8080/BookReaderServer/action/updateBook");
						try {
							String data = "{id:" + bookid + "}";
							Response response = connect.requestBody(data)
									.header("Content-Type", "application/json;charset=UTF-8").ignoreContentType(true)
									.method(Method.POST).timeout(5000).execute();
							response.body();
							
							Thread.sleep(MyConstants.BOOK_UPDATE_INTERVAL);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

		}

	}

}
