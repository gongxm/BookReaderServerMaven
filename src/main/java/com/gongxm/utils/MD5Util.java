package com.gongxm.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.gongxm.encode.BASE64Decoder;
import com.gongxm.encode.BASE64Encoder;

public class MD5Util {
	public static String base64Encoding(String str) {
		byte[] b = str.getBytes();
		return new BASE64Encoder().encode(b);
	}

	public static String base64DeEncod(String str) {
		try {
			return new String(new BASE64Decoder().decodeBuffer(str));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String MD5(String str) {
		if(str==null){
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] b = md.digest(str.getBytes());
			return new BASE64Encoder().encode(b);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

}
