package com.gongxm.quartz;

import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gongxm.bean.BookListRules;
import com.gongxm.services.BookListRulesService;
import com.gongxm.utils.CollectUtils;

@Component // 关键1，将该工具类注册为组件， 加粗！！！
public class UpdateBookInfo implements Job {
	@Autowired
	public BookListRulesService rulesService;

	public static UpdateBookInfo instance; // 关键2

	public UpdateBookInfo() {
	}
	
	// 关键3
	@PostConstruct
	public void init() {
		instance = this;
	}


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("-------定时更新书籍列表-------");
		try {
			List<BookListRules> list = instance.rulesService.findAll();
			for (BookListRules bookListRules : list) {
				bookListRules.setEndIndex(1);
				CollectUtils.collectBookList(bookListRules);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
