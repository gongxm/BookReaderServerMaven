package com.gongxm.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gongxm.bean.Book;
import com.gongxm.bean.BookInfoAndChapterListRules;
import com.gongxm.dao.BookDao;
import com.gongxm.services.BookChapterContentService;
import com.gongxm.services.BookChapterService;
import com.gongxm.services.BookInfoAndChapterListRulesService;
import com.gongxm.services.BookListRulesService;
import com.gongxm.services.BookListService;
import com.gongxm.services.BookService;
import com.gongxm.services.UserService;
import com.gongxm.utils.GsonUtils;
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
	BookListRulesService rulesService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	@Qualifier("bookListService")
	BookListService bookListService;
	
	@Autowired
	BookChapterService chapterService;
	
	@Autowired
	BookChapterContentService contentService;
	
	@Autowired
	BookInfoAndChapterListRulesService bookInfoRuleService;
	
	@Value("${book_list}")
	public String book_list;
	/*
	@Test
	public void test1() {
		try {
			BookInfoAndChapterListRules rules=bookInfoRuleService.findById(2);
			String bookUrl="https://www.88dus.com/xiaoshuo/93/93171/";
			Document doc = HtmlParser.getDocument(bookUrl);
			String cover = doc.select(rules.getCoverRegex()).first().attr("content");
			String title = doc.select(rules.getTitleRegex()).first().attr("content");
			String author = doc.select(rules.getAuthorRegex()).first().attr("content");
			String category = doc.select(rules.getCategoryRegex()).first().attr("content");
			String shortIntroduce = doc.select(rules.getShortIntroduceRegex()).first().attr("content");
			String status = doc.select(rules.getStatusRegex()).first().attr("content");
			
			System.out.println(cover);
			System.out.println(title);
			System.out.println(author);
			System.out.println(category);
			System.out.println(shortIntroduce);
			System.out.println(status);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		String category = "都市言情";
		
		List<Book> list = bookService.getCategoryList(category, 1, 20);//bookService.findBookByCategory(category,5);
		System.out.println(GsonUtils.toJson(list));
	}

	public static void main(String[] args) throws IOException {
		String bookUrl="https://www.ybdu.com/xiaoshuo/23/23547/";
		Document doc = HtmlParser.getDocument(bookUrl);
		Element contentDiv = doc.select(".mulu_list").first();
		Elements elements = contentDiv.select("a");
		for (Element e : elements) {
			String chapterLink = e.absUrl("href");
			String chapterTitle = e.text();
			System.out.println(chapterLink);
			System.out.println(chapterTitle);
		}
		
		String url="https://www.ybdu.com/xiaoshuo/23/23547/10476154.html";
		Document doc = HtmlParser.getDocument(url);
		Element e = doc.select(".contentbox").first();
		String text = HtmlParser.getPlainText(e);
		System.out.println(text);
		
		Date d = new Date();
		System.out.println(d.getTime());
	}
*/
}
