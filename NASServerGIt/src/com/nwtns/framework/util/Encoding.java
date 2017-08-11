package com.nwtns.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class Encoding {
	
	public static String encodingKor(String str){
		if(str != null){
			str = str.trim();
			try{
				str = new String(str.getBytes("8859_1"),"UTF-8");	
				//str = new String(str.getBytes("8859_1"),"euc-kr");
				//str = new String(str.getBytes("8859_1"),"ISO8859-1");
			}catch(Exception e){
				//SystemLogWrite.getInstance().println(e);
				e.printStackTrace();
			}	
		}			
		return str;
	}
	public static String decodingKor(String str){
		if(str != null){
			str = str.trim();
			try{
				str = new String(str.getBytes("UTF-8"),"8859_1");
			}catch(Exception e){
				//SystemLogWrite.getInstance().println(e);
				e.printStackTrace();
			}	
		}			
		return str;
	}

	public static String decodeURL(String str) {
		return URLDecoder.decode(str);
	}

	public static String decodeURL(String str, String enc) {
		try {
			return URLDecoder.decode(str, enc);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	
	/// 테스트...
	public String decodingCheck(String str){
		if(str != null){
			String strType[] = {"UTF-8", "KSC5601", "8859_1", "ISO8859-1", "euc-kr", "iso-8859-1"};
			str = str.trim();
			try{
				for(int i=0;i<strType.length;i++) {
					for(int j=0;j<strType.length;j++) {
						str = new String(str.getBytes(strType[i]),strType[j]);
						NWLog.d(this.getClass(), String.format("type1:%s, type2:%s, result:%s", strType[i],strType[j], str));
					}
					NWLog.d(this.getClass(), ">> " + URLDecoder.decode(str, strType[i]));
				}
				
			}catch(Exception e){
				//SystemLogWrite.getInstance().println(e);
				e.printStackTrace();
			}	
		}			
		return str;
	}
}
