package com.gongxm.test;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gongxm.bean.BookChapter;
import com.gongxm.bean.User;
import com.gongxm.dao.BookDao;
import com.gongxm.services.BookChapterContentService;
import com.gongxm.services.BookChapterService;
import com.gongxm.services.BookListRulesService;
import com.gongxm.services.BookListService;
import com.gongxm.services.BookService;
import com.gongxm.services.UserService;
import com.gongxm.utils.GsonUtils;
import com.gongxm.utils.MD5Utils;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MyTest {
	
	@Autowired
	@Qualifier("bookDao")
	protected BookDao bookDao;  
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookListRulesService service;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	@Qualifier("bookListService")
	BookListService bookListService;
	
	@Autowired
	BookChapterService chapterService;
	
	@Autowired
	BookChapterContentService contentService;
	
	@Value("${book_list}")
	private String book_list;
	
	@Test
	public void test1() {
		/*List<User> list = userService.findAll();
		for (User user : list) {
			String json = GsonUtils.toJson(user);
			System.out.println(json);
		}*/
	}

	public static void main(String[] args) throws IOException {
		String url = "http://www.88dushu.com/xiaoshuo/67/67371/";
		/*String md5 = MD5Util.MD5(url);
		System.out.println(md5);
		String encoding = MD5Util.base64Encoding(md5);
		System.out.println(encoding);
		System.out.println(encoding.length());
		String deEncod = MD5Util.base64DeEncod(encoding);
		System.out.println(deEncod);*/
		
		String creatID = MD5Utils.creatID(url);
		System.out.println(creatID);
	}
	
	//查询重复记录
	//select book_link ,count(*) as count  from book_list group by book_link having count>1; 

}
