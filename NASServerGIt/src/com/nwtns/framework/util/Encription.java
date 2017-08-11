package com.nwtns.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encription {
	
	private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
        	int halfbyte = (data[i] >>> 4) & 0x0F;
        	int two_halfs = 0;
        	do {
	        	if ((0 <= halfbyte) && (halfbyte <= 9))
	                buf.append((char) ('0' + halfbyte));
	            else
	            	buf.append((char) ('a' + (halfbyte - 10)));
	        	halfbyte = data[i] & 0x0F;
        	} while(two_halfs++ < 1);
        }
        return buf.toString();
    }
 
	public static String SHA256(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-256");
		byte[] sha256hash = new byte[32];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha256hash = md.digest();
		return convertToHex(sha256hash);
	}
	
    public static void main(String[] args) throws Exception {
        
        String sPwd = "0709813";
        String sEncPwd = SHA256(sPwd);
        
        try {
//        	System.out.println(sEncPwd);
        } catch(Exception e) {
//            System.err.println("enc error");
        }

    }	
	

}
