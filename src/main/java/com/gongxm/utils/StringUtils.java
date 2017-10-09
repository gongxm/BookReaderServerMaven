package com.gongxm.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class StringUtils {
    public static String readStream(InputStream is, String encoding){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = is.read(buffer))!=-1){
                baos.write(buffer, 0, len);
            }
//            is.close();//不要关闭流
            return new String(baos.toByteArray(),encoding);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     */
    public static String readStream(InputStream is){
        return readStream(is,MyConstants.DEFAULT_ENCODING);
    }
	/**
	 */
	public static String getRandomStringByLength(int length) {  
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < length; i++) {  
            int number = random.nextInt(base.length());  
            sb.append(base.charAt(number));  
        }  
        return sb.toString();  
    } 
	
	
	//替换HTML内容
	public static String htmlTrance(String str) {
		return str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

}
