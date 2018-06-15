package com.gongxm.utils;

import java.util.ArrayList;
import java.util.List;

import com.gongxm.bean.BookChapter;
import com.gongxm.domain.response.ChapterListItem;

public class ChapterUtils {
	public static List<ChapterListItem> changeData(List<BookChapter> list){
		List<ChapterListItem> result = new ArrayList<ChapterListItem>();
		if(list==null) {
			return result;
		}
		for (BookChapter chapter : list) {
			ChapterListItem item = new ChapterListItem(chapter);
			result.add(item);
		}
		return result;
	}
}
