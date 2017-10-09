package com.gongxm.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProxyUtils {
	public static String executGet(String path) throws IOException {
		URL url = new URL("http://17h940602m.iask.in/proxy.php");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("content-type", "application/json"); 
		OutputStream out = con.getOutputStream();
		String json = "{\"url\":\""+path+"\"}";
		out.write(json.getBytes());
		out.flush();
		// 根据ResponseCode判断连接是否成功  
        int responseCode = con.getResponseCode();  
        if (responseCode != 200) {  
            System.out.println(" Error===" + responseCode);  
        } else {  
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        	InputStream is = con.getInputStream();
        	int b;
        	while((b=is.read())!=-1) {
        		bos.write(b);
        	}
        	out.close();
        	return bos.toString(MyConstants.DEFAULT_ENCODING);
        }  
        return null;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		String path = "http://www.yssm.org/ucbook/1/1.html";
		String data = executGet(path);
		System.out.println(data);
	}
}
