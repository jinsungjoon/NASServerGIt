package com.nwtns.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class NWEncoding {


	/********************************************************************************
	 *<br>
	 *인자로 받은 문자열이 한글인지 검사 <br>
	 * 
	 * @param in
	 * @return
	 *******************************************************************************/
	public static boolean isHangle(String in) {
		boolean flag = false;
		String str[] = new String[in.length()];
		for (int i = 0; i < in.length(); i++)
			str[i] = in.substring(i, i + 1);

		for (int i = 0; i < str.length; i++) {
			char charArray[] = str[i].toCharArray();
			for (int j = 0; j < charArray.length; j++)
				if (charArray[j] >= '\uAC00' && charArray[j] <= '\uD7A3')
					flag = true;

		}

		return flag;
	}

	/********************************************************************************
	 *<br>
	 * 
	 *<br>
	 * 
	 * @param s
	 * @return
	 *******************************************************************************/
	public static String eng2utf8(String s) {
		if (s == null)
			return s;
		try {
			return new String(s.getBytes("8859_1"), "utf-8");
		} catch (Exception exception) {
			return s;
		}
	}

	/********************************************************************************
	 *<br>
	 * 
	 *<br>
	 * 
	 * @param s
	 * @return
	 *******************************************************************************/
	public static String eng2kor(String s) {
		if (s == null)
			return s;
		try {
			return new String(s.getBytes("8859_1"), "ksc5601");
		} catch (Exception exception) {
			return s;
		}
	}

	/********************************************************************************
	 *<br>
	 * 
	 *<br>
	 * 
	 * @param s
	 * @return
	 *******************************************************************************/
	public static String kor2utf8(String s) {
		if (s == null)
			return s;
		try {
			return new String(s.getBytes("ksc5601"), "utf-8");
		} catch (Exception exception) {
			return s;
		}
	}

	/********************************************************************************
	 *<br>
	 * 
	 *<br>
	 * 
	 * @param s
	 * @return
	 *******************************************************************************/
	public static String utf82kor(String s) {
		if (s == null)
			return s;
		try {
			return new String(s.getBytes("utf-8"), "ksc5601");
		} catch (Exception exception) {
			return s;
		}
	}

	/********************************************************************************
	 *<br>
	 * 
	 *<br>
	 * 
	 * @param s
	 * @return
	 *******************************************************************************/
	public static String kor2eng(String s) {
		if (s == null)
			return s;
		try {
			return new String(s.getBytes("ksc5601"), "8859_1");
		} catch (Exception exception) {
			return s;
		}
	}

	/********************************************************************************
	 *<br>
	 *array 변환 : 문자열 입력을 구분자로 구분하여 배열로 변환하여 반환 <br>
	 * 
	 * @param str
	 * @param delim
	 * @return
	 *******************************************************************************/
	public static String[] str2strs(String str, String delim) {
		String[] strs = null;
		if (!NWUtil.isEmpty(str)) { // 배열에 담는다.
			StringTokenizer st = new StringTokenizer(str, delim);
			int size = st.countTokens();
			int idx = 0;
			strs = new String[size];
			while (st.hasMoreTokens()) {
				strs[idx] = st.nextToken();
				idx++;
			}
		}
		return strs;
	}


	/********************************************************************************
	 *<br>
	 *인자로 받은 문자열을 int형으로 형변환 시켜준다. <br>
	 * 
	 * @param str
	 * @return int로 변환된 값
	 *******************************************************************************/
	public static int toInt(String str) {
		int li = 0;
		if (str == null || str.equals(""))
			return 0;
		try {
			li = Integer.parseInt(str);
		} catch (Exception e) {
			System.out.println(e);
		}
		return li;
	}

	/********************************************************************************
	 *<br>
	 *인자로 받은 문자값을 long으로 형변환 시켜준다. <br>
	 * 
	 * @param str
	 * @return
	 *******************************************************************************/
	public static long toLong(String str) {
		long li = 0L;
		if (str == null || str.equals(""))
			return 0L;
		try {
			li = Long.parseLong(str);
		} catch (Exception e) {
			System.out.println(e);
		}
		return li;
	}

	/********************************************************************************
	 *<br>
	 *Key값을 boolean으로 리턴한다. true/false의 대소문자는 고려하지 않는다. <br>
	 * 
	 * @param srt
	 * @return
	 *******************************************************************************/
	public static boolean str2Boolean(String srt) {
		if ("TRUE".equalsIgnoreCase(srt) == true)
			return true;
		else
			return false;
	}

	/********************************************************************************
	 *<br>
	 *문자열 시간을 Date로 변환 <br>
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 *******************************************************************************/
	public static Date str2date(String s, String pattern) {
		if (s == null || s.equals(""))
			return null;

		if (NWUtil.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}

		try {
			SimpleDateFormat smf = new SimpleDateFormat(pattern);
			return smf.parse(s, new ParsePosition(0));
		} catch (Exception e) {
			return null;
		}
	}

	/********************************************************************************
	 *<br>
	 *영문,한글이 포함된 문자열 길이 구하기.. <br>
	 * 
	 * @param str
	 * @return
	 *******************************************************************************/
	public static int CharLen(String str) {

		int strlen = 0;

		for (int j = 0; j < str.length(); j++) {
			char c = str.charAt(j);
			if (c < 0xac00 || 0xd7a3 < c) {
				strlen++;
			} else
				strlen += 2; // 한글이다..
		}
		return strlen;
	}


	/**
	 * 언어셋을 확인한다
	 * 
	 * @param str_kr
	 * @throws Exception
	 */
	public static void charSet(String str_kr) throws Exception {
		String charset[] = { "euc-kr", "ksc5601", "iso-8859-1", "8859_1", "ascii", "UTF-8" };

		for (int i = 0; i < charset.length; i++) {
			for (int j = 0; j < charset.length; j++) {
				if (i == j)
					continue;
				System.out.println(charset[i] + " : " + charset[j] + " :" + new String(str_kr.getBytes(charset[i]), charset[j]));
			}
		}
		System.out.println("------------------------------------------");
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
}
