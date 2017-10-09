package com.gongxm.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

/**
 * Created by gongxm on 2017/7/11.
 */

public class HtmlParser {
	
	public static void main(String[] args) throws IOException {
		String path = "http://www.88dushu.com/sort1/2/";
		String regex = "http://www.88dushu.com/xiaoshuo/[0-9/]+";
		
		Document doc = Jsoup.connect(path).timeout(30000).header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").get();
		Element element = doc.select("div.booklist").first();
		
		Elements elements = element.getElementsByTag("a");
		for (Element e : elements) {
			String absUrl = e.absUrl("href");
			if(absUrl.matches(regex)) {
			System.out.println(absUrl);
			}
		}
	}
	
	
	/**
	 * 获取文档对象
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Document getDocument(String url) throws IOException {
		Document doc = Jsoup.connect(url).timeout(30000).header("User-Agent",
				"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
				.get();
		return doc;
	}

	public static String getPlainText(Element element) {
		FormattingVisitor formatter = new FormattingVisitor();
		NodeTraversor traversor = new NodeTraversor(formatter);
		traversor.traverse(element);
		return formatter.toString().replace(Jsoup.parse("&nbsp;").text(), " ");
	}

	private static class FormattingVisitor implements NodeVisitor {
		private String lineSeparator = "\n";
		private static final int maxWidth = 80;
		private int width = 0;
		private StringBuilder accum = new StringBuilder();

		public void head(Node node, int depth) {
			String name = node.nodeName();
			if (node instanceof TextNode)
				append(((TextNode) node).text());
			else if (name.equals("li"))
				append(lineSeparator + " * ");
			else if (name.equals("dt"))
				append("  ");
			else if (StringUtil.in(name, "p", "h1", "h2", "h3", "h4", "h5", "tr"))
				append(lineSeparator);
		}

		private boolean flag;

		public void tail(Node node, int depth) {
			String name = node.nodeName();
			if (StringUtil.in(name, "br", "dd", "dt", "p", "h1", "h2", "h3", "h4", "h5")) {
				if (!flag) {
					append(lineSeparator);
					flag = true;
				} else {
					flag = false;
				}
			}
		}

		private void append(String text) {
			if (text.startsWith(lineSeparator))
				width = 0;
			if (text.equals(" ")
					&& (accum.length() == 0 || StringUtil.in(accum.substring(accum.length() - 1), " ", lineSeparator)))
				return;

			if (text.length() + width > maxWidth) {
				String words[] = text.split("\\s+");
				for (int i = 0; i < words.length; i++) {
					String word = words[i];
					boolean last = i == words.length - 1;
					if (!last)
						word = word + " ";
					if (word.length() + width > maxWidth) {
						accum.append(lineSeparator).append(word);
						width = word.length();
					} else {
						accum.append(word);
						width += word.length();
					}
				}
			} else {
				accum.append(text);
				width += text.length();
			}
		}

		@Override
		public String toString() {
			return accum.toString();
		}
	}
}
