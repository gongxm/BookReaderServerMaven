package com.gongxm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	public static final int TODAY = 1;
	public static final int TOMORROW = 2;
	public static final int AFTER_TOMORROW = 3;

	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}


	public static String getMillisTime() {
		return System.currentTimeMillis() + "";
	}

	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}

	public static Date parseTime(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(time);
	}

	public static String parseTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}

	public static String parseTime(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(time);
	}

	public static Date parseShortTime(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(time);
	}

	public static String parseShortTime(long time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(time));
	}


	public static Date getCurrentTime() {
		return new Date();
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static long parseToMillis(String time) {
		long millis = 0;
		try {
			Date date = parseTime(time);
			millis = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return millis;
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static boolean checkTime(String time) {
		long millis = parseToMillis(time);
		long currentTime = getTimeMillis();
		return currentTime < millis;
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static boolean checkTime(Date time) {
		long millis = time.getTime();
		long currentTime = getTimeMillis();
		return currentTime < millis;
	}
}
