package com.gongxm.utils;

public class TextUtils {

	/**
	 * 
	 * @param strings
	 * @return
	 */
	public static boolean notEmpty(String... strings) {
		return !isEmpty(strings);
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * 
	 * @param strings
	 * @return
	 */
	public static boolean isEmpty(String... strings) {
		for (int i = 0; i < strings.length; i++) {
			if (strings[i] == null || strings[i].trim().length() == 0) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		String regex = "1[345678]\\d{9}";
		return phone != null && phone.matches(regex);
	}

	
	
	//去除两个字符串中首尾相同的内容
	public static String dealWithText(String src,String reg){
		int length =0;
		for(int i=0;i<reg.length();i++){
			if(src.charAt(i)!=reg.charAt(i)){
				length=i;
				break;
			}
		}
		String str = src.substring(length);
		length=0;
		
		str = new StringBuilder(str).reverse().toString();
		reg = new StringBuilder(reg).reverse().toString();
		for(int i=0;i<reg.length();i++){
			if(str.charAt(i)!=reg.charAt(i)){
				length=i;
				break;
			}
		}
		String result = str.substring(length);
		return new StringBuilder(result).reverse().toString();
	}
}
