package com.gongxm.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import com.gongxm.domain.HttpPostResult;
import com.gongxm.domain.MyX509TrustManager;

/**************************************
 ***************************************/

public class HttpUtils {

	private static int TIME_OUT = 30000;

	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			MyX509TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param url
	 * @throws IOException
	 */
	public static String executGet(String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(TIME_OUT);
		conn.setReadTimeout(TIME_OUT);
		conn.setRequestMethod("GET");
		InputStream is = conn.getInputStream();
		String result = StringUtils.readStream(is, MyConstants.DEFAULT_ENCODING);
		return result;
	}

	/**
	 * @param url
	 * @throws IOException
	 */
	public static String executGet(String url,String charset) throws IOException {
		if(TextUtils.isEmpty(charset)){
			charset = MyConstants.DEFAULT_ENCODING;
		}
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(TIME_OUT);
		conn.setReadTimeout(TIME_OUT);
		conn.setRequestMethod("GET");
		InputStream is = conn.getInputStream();
		String result = StringUtils.readStream(is, charset);
		return result;
	}

	
	/**
	 * @param url
	 * @param data
	 * @return
	 */
	public static HttpPostResult executePost(String url,String data) {
		try {
			HttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json");
			
			if(data!=null){
				StringEntity entity = new StringEntity(data);
				post.setEntity(entity);
			}

			HttpResponse response = client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpPostResult result = new HttpPostResult();
			result.setStatusCode(statusCode);
			if (statusCode >= 200 && statusCode < 400) {
				HttpEntity entity = response.getEntity();
				InputStream stream = entity.getContent();
				result.setStream(stream);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
