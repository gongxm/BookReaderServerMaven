package com.gongxm.test;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gongxm.bean.BookChapter;
import com.gongxm.dao.BookDao;
import com.gongxm.services.BookChapterService;
import com.gongxm.services.BookListRulesService;
import com.gongxm.services.BookListService;
import com.gongxm.services.BookService;
import com.gongxm.services.UserService;
import com.gongxm.utils.HtmlParser;
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
	
	@Test
	public void test1() {
		/*Book book = new Book("测试版", "我", "测试", "完本", "http://www.baidu.com", "暂无", "http://www.gongxm.com");
		BookChapter ch = new BookChapter("第一章", "http://www.gongxm.com/001", 1);
		book.getChapters().add(ch);
		bookService.add(book);*/
	}
	
	@Test
	public void test9() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			BookChapter chapter = chapterService.findByChapterLink("http://www.88dushu.com/xiaoshuo/90/90918/31584051.html");
			System.out.println(chapter);
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start));
	}
	

	public static void main(String[] args) throws IOException {
		
		String url = "http://www.88dushu.com/xiaoshuo/91/91615/29694897.html";
		Document doc = HtmlParser.getDocument(url);
		Element e = doc.select("div.yd_text2").first();
		String text = HtmlParser.getPlainText(e);
		System.out.println(text);
	}
	
	//查询重复记录
	//select book_link ,count(*) as count  from book_list group by book_link having count>1; 

}
