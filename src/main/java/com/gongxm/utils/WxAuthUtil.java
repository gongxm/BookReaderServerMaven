package com.gongxm.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 
 * @author gongxm
 *
 */
@SuppressWarnings("deprecation")
public class WxAuthUtil {
	/**
	 * 缓存微信openId和session_key
	 * @param wxOpenId		微信用户唯一标识
	 * @param wxSessionKey	微信服务器会话密钥
	 * @param expires		会话有效期, 以秒为单位, 例如2592000代表会话有效期为30天
	 * @return
	 */
	public static String create3rdSession(){
		String thirdSessionKey = RandomStringUtils.randomAlphanumeric(64);
		return thirdSessionKey;
	}
	/**
	 * 解密用户敏感数据
	 * @param encryptedData 密文
	 * @param iv            加密算法的初始向量
	 * @param sessionKey    用户sessionKey
	 * @return
	 */
	public static String decodeUserInfo(String encryptedData, String iv, String sessionKey){

	    try {
	        AES aes = new AES();
	        byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
	        if(null != resultByte && resultByte.length > 0){
	            String userInfo = new String(resultByte, MyConstants.DEFAULT_ENCODING);
	            return userInfo;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
