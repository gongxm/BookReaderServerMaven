package com.gongxm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
	public static void writeFile(InputStream is, OutputStream out){
		byte[] buf = new byte[8192];
		int len;
		try {
			while((len=is.read(buf))!=-1){
				out.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
