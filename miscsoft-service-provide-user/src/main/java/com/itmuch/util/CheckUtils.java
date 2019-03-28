package com.itmuch.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckUtils {
	
	private static final Logger log = LoggerFactory.getLogger(CheckUtils.class);
	private static final String TOKEN = "SYKJ";
	
	public static boolean checkSignature(HttpServletRequest req ) {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp"); 
		String nonce = req.getParameter("nonce");
		String [] arr = new String[] {TOKEN, timestamp, nonce};
		Arrays.sort(arr);
		String temp = 	StringUtils.join(arr);
		String result = getSha1(temp);
		return signature.equals(result);
	}
	
	public static void main(String[] args) {
//		System.out.println(checkSignature("123", "adab", "1231cdsc"));
	}

    public static String getSha1(String str){
        if (null == str || 0 == str.length()){
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
             
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
        	throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(e);
        }
    }
}
