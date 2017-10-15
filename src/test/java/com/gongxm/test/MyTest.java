package com.gongxm.test;

import java.io.IOException;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.commons.dbutils.QueryRunner;
import org.hibernate.Hibernate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.gongxm.bean.Book;
import com.gongxm.bean.BookChapter;
import com.gongxm.bean.BookChapterContent;
import com.gongxm.dao.BookDao;
import com.gongxm.services.BookChapterContentService;
import com.gongxm.services.BookChapterService;
import com.gongxm.services.BookListRulesService;
import com.gongxm.services.BookListService;
import com.gongxm.services.BookService;
import com.gongxm.services.UserService;
import com.gongxm.utils.GsonUtils;
import com.gongxm.utils.HtmlParser;
import com.gongxm.utils.MD5Utils;
import com.gongxm.utils.ThreadPoolUtils;
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
	@Transactional
	public void test1() {
		
	}
	
	
	@Test
	public void test2() {
		
		BookChapter chapter = chapterService.findById("ZXgzY2NxRE9STFY0SmNPbjBad1RYQT09");
		System.out.println(GsonUtils.toJson(chapter));
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
