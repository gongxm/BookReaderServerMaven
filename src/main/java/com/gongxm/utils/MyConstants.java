package com.gongxm.utils;

public class MyConstants {

	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final int FAILURE = 0;
	public static final int SUCCESS = 1;

	// 用户权限
	public static final String ROLE_USER = "USER"; // 普通用户
	public static final String ROLE_ROOT = "ROOT"; // 超级管理员

	public static final String REMEMBER = "remember";// 是否记住登陆状态

	// 小程序信息
	public static final String APPID = "wx2f98d23529ff8780";
	public static final String APP_SECRET = "9f4f5f9c8ca32ef4cb68e2959d77d24a";
	public static final String GRANT_TYPE = "authorization_code";

	// 采集状态
	public static final int BOOK_UNCOLLECT = 0;// 未采集
	public static final int BOOK_COLLECTED = 1;// 已采集
	public static final int BOOK_COLLECT_FAILURE = 2;// 采集失败
	
	public static final int BOOK_COLLECT_INTERVAL = 10;// 采集时间间隔
	public static final String SOLR_QUERY_BOOK_URL = "http://127.0.0.1:8080/solr/book_core";
	public static final String SOLR_QUERY_CHAPTER_URL = "http://127.0.0.1:8080/solr/chapter_core";
	public static final int DELAY_TIME = 2000; // 间隔时间检查任务是否完成
	
}
