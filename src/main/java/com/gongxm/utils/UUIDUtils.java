package com.gongxm.utils;

import java.util.Random;
import java.util.UUID;

public class UUIDUtils {
	private static final int[] in = {1,2,3,4,5,6,7,8,9};
	
	public static String createID(){
		 UUID uuid = UUID.randomUUID();
		 return uuid.toString();
	}
	
	public static int getNotSimple(int len) {  
        Random rand = new Random();  
        for (int i = in.length; i > 1; i--) {  
            int index = rand.nextInt(i);  
            int tmp = in[index];  
            in[index] = in[i - 1];  
            in[i - 1] = tmp;  
        }  
        int result = 0;  
        for (int i = 0; i < len; i++) {  
            result = result * 10 + in[i];  
        }  
        return result;  
    }
	
	
	/**
	 * 格式: C2B1-5079-400E-A7CF-B40C
	 * @return
	 */
	public static String createKey(){
		String key = createID();
		return key.substring(4,28).toUpperCase();
	}
	
	
	/**
	 * @return
	 */
	public static String createClubId(){
		String key = createKey();
		String clubId = MD5Util.MD5Encode(key, "utf-8");
		return clubId.substring(0,16);
	}
	
	/**
	 * @return
	 */
	public static String createProjectId(){
		String key = createKey();
		String projectId = MD5Util.MD5Encode(key, "utf-8");
		return projectId.substring(2,18);
	}

	
	public static String createValidateCode() {
		String time = TimeUtils.getMillisTime();
		String validateCode = time.substring(9);
		return validateCode;
	}
	
}
