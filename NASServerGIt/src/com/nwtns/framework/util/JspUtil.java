package com.nwtns.framework.util;

import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 파일명   : JSPUtil.java
 * 파일설명 : JSP 페이지와 관련된 여러가지 메소드들을 제공하는 유틸리티 클래스
 * 수정이력 :
 * </pre>
 */
public class JspUtil {
	public JspUtil(){
	}
	public static String mapString(HttpServletRequest req){
		String ret="";
		StringBuffer sb = new StringBuffer();

		
		for (Enumeration en = req.getParameterNames(); en.hasMoreElements(); ){
		    String key = (String)en.nextElement();
		    sb.append ( key + "=" + req.getParameter(key) + "&");
		} 

		ret = sb.toString();
		return ret ;
	}	
	public static String toStar(float s1, float s2, float s3){
		String fillStar = "●";
		String blankStar = "○";
		String star1 = "";
		String star2 = "";
		String star3 = "";
		for(int i = 0; i<10; i++){
			if(i <= Math.round(s1/10))
				star1 += fillStar;
			else
				star1 += blankStar;

			if(i <= Math.round(s2/10))
				star2 += fillStar;
			else
				star2 += blankStar;

			if(i <= Math.round(s3/10))
				star3 += fillStar;
			else
				star3 += blankStar;

		}
		return "<table><tr><td>"+star1+"</td><td>"+star2+"</td><td>"+star3+"</td></tr></table>";
	}
	public static String toBicStar(float num1){
		String fillStar = "●";
		String blankStar = "○";
		String halfStar = "◎";
		String star1 = "";
		for(int i = 0; i<5; i++){
			if(i <= (Math.round(num1/10)) / 2.0 )
				star1 += fillStar;
			else if( i > (Math.round(num1/10)) / 2.0 && i < (Math.round(num1/10)) / 2.0 + 1 )
				star1 += halfStar;
			else
				star1 += blankStar;
		}
		return star1;
	}

	public static String getParameter(HttpServletRequest request, String name, String defValue){
		String val = request.getParameter(name);
		try {
			// 첫번째 문자값을 읽음..
			int i = val != null && val.length() > 0 ? val.charAt(0) : -1;
			// 만일 164(%A4)이상 200(%C8)이하라면 iso-8859-1로 인코딩 한것이므로,

			// 이때는 다시 인코딩을 조정한다.
			val = val != null ? (i >= 164 && i <= 200 ? new String (val.getBytes("iso-8859-1"), "euc-kr") : val) : null;
		} catch (Exception e){}
		return val == null ? defValue : val;
	}

	public static String paramEncoding(String val, String defValue){
		try {
			// 첫번째 문자값을 읽음..
			int i = val != null && val.length() > 0 ? val.charAt(0) : -1;
			// 만일 164(%A4)이상 200(%C8)이하라면 iso-8859-1로 인코딩 한것이므로,

			// 이때는 다시 인코딩을 조정한다.
			val = val != null ? (i >= 164 && i <= 200 ? new String (val.getBytes("iso-8859-1"), "euc-kr") : val) : null;
		} catch (Exception e){}
		return val == null ? defValue : val;
	}


	/**
	 * <pre>
	 * 기능 : 웹에서 한글은 꼭 이함수를 사용해야 함(한글이 깨지는현상을 막아줌)
	 * </pre>
	 * @param  Object NULL이 아닌 문자열로 변경할 Object
	 * @return String - NULL이 아닌 문자열
	 */
	public static synchronized String toKor(String english) {

		String korean = "";

		if (english==null || english.equalsIgnoreCase(""))
			return korean ;
		else if (english.equalsIgnoreCase("''"))
			return english ;

		try {korean = new String(new String(english.getBytes("8859_1"), "KSC5601"));}
		catch (Exception e) {korean = new String(english);}
		return korean;

	}


	/**
	 * <pre>
	 * 기능 : NULL인 경우 빈 문자열 Return
	 * </pre>
	 * @param  Object NULL이 아닌 문자열로 변경할 Object
	 * @return String - NULL이 아닌 문자열
	 */
	public static String getNonNullString(Object argObject) {
		if (argObject == null) {
			return "";
		} else {
			return argObject.toString();
		}
	}




	/**
	 * <pre>
	 * 기능 : 문자열에서 글자사이에 특정 문자열을 특정 수만큼 채워넣기
	 *         사용예) getFilledString("ERP전산팀", "..", 2);
	 *                ==> "E....R....P....전....산....팀"
	 * </pre>
	 * @param  String 문자열
	 * @param  String 채워넣을 문자열
	 * @param  int    채워넣을 수
	 * @return String - 글자 사이에 특정 문자열이 채워진 문자열
	 */

	public static String n2s(String sz)
	{
		if(sz == null || sz.equals(""))
			return "";
		else
			return sz;

	}

	public static String nchkno(String sz)
	{
		if(sz == null || sz.equals(""))
			return "0";
		else
			return sz;
	}

	public static String a2k(String str) {
		String rtn = null;
		try {
			rtn = (str==null)?"":new String(str.getBytes("8859_1"),"euc-kr");
		} catch (java.io.UnsupportedEncodingException e) {}
		return rtn;
	}

	public static String getFilledString(String argSourceStr, String argFillStr, int argFillCnt) {
		if (argSourceStr == null) {
			return "";
		}

		String filledStr = "";

		for (int inx = 0; inx < argSourceStr.length(); inx++) {
			filledStr += argSourceStr.substring(inx, inx + 1);

			if (inx != argSourceStr.length() - 1) {
				for (int inx1 = 0; inx1 < argFillCnt; inx1++) {
					filledStr += argFillStr;
				}
			}
		}

		return filledStr;
	}

	/**
	 * <pre>
	 * 기능 : 문자열을 특정 Byte(글자수)내에 같은 간격으로 공백 채워넣기
	 *        사용예) getSameWidthString("ERP전산팀", 20, true);
	 *                ==> "E&nbsp;&nbsp;R&nbsp;&nbsp;P&nbsp;&nbsp;전&nbsp;&nbsp;산&nbsp;&nbsp;팀"
	 * </pre>
	 * @param  String  문자열
	 * @param  int     전체 글자수
	 * @param  boolean 채울 공백을 "&nbsp;"로 대체여부
	 * @return String - 같은 간격으로 공백이 채워진 문자열
	 */
	public static String getSameWidthString(String argSourceStr, int argByte, boolean argIsNBSP) {
		if (argSourceStr == null) {
			return "";
		}

		String sameWidthStr = "";
		String fillStr      = " ";
		int    sourceLength = argSourceStr.getBytes().length;
		int    filledCnt    = argByte - sourceLength;

		if (filledCnt <= 0) {
			return argSourceStr;
		}

		filledCnt = filledCnt / (argSourceStr.length() - 1);
		if (argIsNBSP) {
			fillStr = "&nbsp;";
		}

		for (int inx = 0; inx < argSourceStr.length(); inx++) {
			sameWidthStr += argSourceStr.substring(inx, inx + 1);

			if (inx != argSourceStr.length() - 1) {
				for (int inx1 = 0; inx1 < filledCnt; inx1++) {
					sameWidthStr += fillStr;
				}
			}
		}

		return sameWidthStr;
	}

	/**
	 * <pre>
	 * 기능 : 문자열을 특정 Byte(글자수) 만큼 체크하여 뒤에 "..." 붙이기
	 *         사용예) getCheckLengthString("ERP전산팀에서 만들었습니다.", 20);
	 *                ==> "ERP전산팀에서 만..."
	 * </pre>
	 * @param  String  문자열
	 * @param  int     전체 글자수
	 * @return String - 길이 체크된 문자열
	 */
	public static String getCheckLengthString(String argSourceStr, int argByte) {
		if (argSourceStr == null) {
			return "";
		}

		String checkLengthStr = "";
		String appendStr      = "..";
		int    checkByte      = argByte;

		if (argSourceStr.getBytes().length <= argByte) {
			return argSourceStr;
		}

		checkByte -= appendStr.getBytes().length;

		for (int inx = 0; inx < argSourceStr.length(); inx++) {
			String tempStr = argSourceStr.substring(inx, inx + 1);

			checkByte -= tempStr.getBytes().length;
			if (checkByte < 0) {
				break;
			}

			checkLengthStr += tempStr;
		}
		checkLengthStr += appendStr;

		return checkLengthStr;
	}

	/**
	 * <pre>
	 * 기능 : 문자열을 특정 Byte(글자수) 만큼 체크하여 뒤에 "<br>" 붙이기
	 *          사용예) getCheckWidthString("ERP전산팀에서 만들었습니다.", 20);
	 *                ==> "ERP전산팀에서 만들었<br>습니다."
	 * </pre>
	 * * @param  String  문자열
	 * @param  int     한 라인의 글자수
	 * @return String - 넓이가 체크된 문자열
	 */
	public static String getCheckWidthString(String argSourceStr, int argByte) {
		if (argSourceStr == null) {
			return "";
		}

		String checkWidthStr = "";
		String appendStr     = "\r\n";
		int    checkByte     = argByte;

		argSourceStr = getNonNullString(argSourceStr);

		if (argSourceStr.getBytes().length <= argByte) {
			return argSourceStr;
		}

		for (int inx = 0; inx < argSourceStr.length(); inx++) {
			String tempStr = argSourceStr.substring(inx, inx + 1);

			if (tempStr.equals("\r") || tempStr.equals("\n")) {  // 캐리지리턴인 경우
				if (tempStr.equals("\n")) {  // 다시 계산...
					checkByte = argByte;
				}

			} else {
				checkByte -= tempStr.getBytes().length;

				if (checkByte < 0) {
					checkWidthStr += appendStr;
					checkByte      = argByte - 1;
				}
			}

			checkWidthStr += tempStr;
		}

		return checkWidthStr;
	}


	/**
	 * <pre>
	 * 기능 : 문자열에서 글자사이에 특정 문자열을 특정 수만큼 채워넣기
	 *         사용예) split("123,456,789", ",");
	 *                ==> [0]=123, [1]=456, [2]=789

	 * </pre>
	 * @param  String 문자열
	 * @param  String 분리문자열
	 * @return String[] - 배열의 분리문자열에 해당하는 문자열
	 */

	public static String[] getToken(String argValue, String delimeter) {

		String[] res = null;


		if(argValue != null){
			StringTokenizer st = new StringTokenizer(argValue, delimeter);
			res = new String [st.countTokens()];

			try
			{
				int i =0;
				while(st.hasMoreTokens()) {
					res[i] = st.nextToken();
					i++;
				}
			}
			catch(java.util.NoSuchElementException _ex) { }
		}
		else
		{
			res = null;
		}

		return res;

	}


	public static Vector split(String argValue, String delimeter) {
		Vector resVector = new Vector();

		if(argValue != null){

			StringTokenizer st = new StringTokenizer(argValue, delimeter);
			try
			{
				while (st.hasMoreTokens()) {

					resVector.addElement(st.nextToken());

				}
			}
			catch(java.util.NoSuchElementException _ex) { }
		}
		return resVector;
	}




	public static String nullToStr(String s) {
		return s != null ? s.trim() : "";
	}

	public static int nullToZero(String s) {
		String s1 = nullToStr(s);
		return s1.equals("") ? 0 : Integer.parseInt(s1);
	}
	public static String changeCheck(String s) {
		String s1 = nullToStr(s);
		return s1.equals("1") ? "true" : "false";
	}


	/**
	 * <pre>
	 * 기능 : 문자열에서 특정 문자열을 특정 수만큼 채워넣기
	 *         사용예) getLpad("12", "0", 5);  ==> "00012"
	 *                     getRpad("12", "0", 5);  ==> "12000"

	 * </pre>
	 * @param  String 문자열
	 * @param  String 추가문자열
	 * @param  int       전체길이
	 * @return   String 추가된 문자열
	 */

	public static String getLpad(String argVal, String addVal ,int totalSize) {

		String returnVal="";
		for (int inx=0; inx < totalSize - argVal.trim().length() ; inx++ ){
			returnVal += addVal;
		}

		return returnVal + argVal.trim();

	}

	public static String getRpad(String argVal, String addVal ,int totalSize) {

		String returnVal="";
		for (int inx=0; inx < totalSize - argVal.trim().length() ; inx++ ){
			returnVal += addVal;
		}

		return  argVal.trim() + returnVal;

	}

	public static String change_code(String Source_str){

		String Target_str = "";

		if ( Source_str.equalsIgnoreCase(null) ||  Source_str.equalsIgnoreCase(""))
			return Target_str ;
		else if ( Source_str.equalsIgnoreCase("''"))
			return Source_str ;

		try {Target_str = new String(new String(Source_str.getBytes("8859_1"), "KSC5601"));}
		catch (Exception e) {Target_str = new String(Source_str);}

		for(int index = 0; (index = Target_str.indexOf("'", index)) >= 0;  index += 1)
		{Target_str= Target_str.substring(0, index) + "´" + Target_str.substring(index + 1);}

		for(int index = 0; (index = Target_str.indexOf("\\", index)) >= 0;  index += 1)
		{Target_str= Target_str.substring(0, index) + "￦" + Target_str.substring(index + 1);}

		for(int index = 0; (index = Target_str.indexOf(",", index)) >= 0;  index += 1)
		{Target_str= Target_str.substring(0, index) + "，" + Target_str.substring(index + 1);}

		return Target_str;

	}

	// 날짜가 형식에 맞는 월인지 체크하는 함수
	public static String checkDate(String sDate)
	{
		String sYear = "";
		String sMonth = "";
		String sDay = "";

		int sYear_i  = 0;
		int sDay_i = 0;
		int sMonth_i = 0;



		String result = "";


		sYear =  sDate.substring(0,4);
		sMonth = sDate.substring(4,6);
		sDay = sDate.substring(6);

		sYear_i = Integer.parseInt(sYear);
		sMonth_i = Integer.parseInt(sMonth);
		sDay_i = Integer.parseInt(sDay);


		if (sMonth.equals("02")){			//2월달일 경우

			if( sDay_i > 28 || sDay_i < 0 ){					//해달 날이 28일 이후이면

//				System.out.println(sYear_i%4);
//				System.out.println(sYear_i%100);
//				System.out.println(sYear_i%400);
				if(sYear_i%4==0 && sYear_i%100>0 || sYear_i%400 ==0){  	//해당년이 윤년이면

					sDay = "29";

				}else{		//해당년이 윤년이 아닐경

					sDay = "28";
				}

			}



		}else{										//2월이 아닌경우

			if( sMonth.equals("01") ||  sMonth.equals("03") || sMonth.equals("05") || sMonth.equals("07") || sMonth.equals("08") || sMonth.equals("10") ||sMonth.equals("12") )
			{
				//월이 1,3,5,7,8,10,12 경우(최고 31일)

				if (   sDay_i > 31 || sDay_i < 0 ){
					sDay = "31";
				}

			}else{											//월이 4, 6, 9 , 11 일경우 (최고 30일)
				if (sDay_i > 30 || sDay_i < 0 ){
					sDay = "30";
				}

			}


		}

		result = sYear+sMonth+sDay;


		return result;
	}

//	날짜가 형식에 맞는 월인지 체크하는 함수
	public static String checkDate2(String sDate)
	{
		String sYear = "";
		String sMonth = "";
		String sDay = "";

		int sYear_i  = 0;
		int sDay_i = 0;
		int sMonth_i = 0;



		String result = "";


		sYear =  sDate.substring(0,4);
		sMonth = sDate.substring(5,7);
		sDay = sDate.substring(8);

		sYear_i = Integer.parseInt(sYear);
		sMonth_i = Integer.parseInt(sMonth);
		sDay_i = Integer.parseInt(sDay);


		if (sMonth.equals("02")){			//2월달일 경우

			if( sDay_i > 28 || sDay_i < 0 ){					//해달 날이 28일 이후이면

//				System.out.println(sYear_i%4);
//				System.out.println(sYear_i%100);
//				System.out.println(sYear_i%400);
				if(sYear_i%4==0 && sYear_i%100>0 || sYear_i%400 ==0){  	//해당년이 윤년이면

					sDay = "29";

				}else{		//해당년이 윤년이 아닐경

					sDay = "28";
				}

			}



		}else{										//2월이 아닌경우

			if( sMonth.equals("01") ||  sMonth.equals("03") || sMonth.equals("05") || sMonth.equals("07") || sMonth.equals("08") || sMonth.equals("10") ||sMonth.equals("12") )
			{
				//월이 1,3,5,7,8,10,12 경우(최고 31일)

				if (   sDay_i > 31 || sDay_i < 0 ){
					sDay = "31";
				}

			}else{											//월이 4, 6, 9 , 11 일경우 (최고 30일)
				if (sDay_i > 30 || sDay_i < 0 ){
					sDay = "30";
				}

			}


		}

		result = sYear+'-'+sMonth+'-'+sDay;


		return result;
	}

//	double 형태의 String 에서 소수점 밑의 자리를 다 버림

	public static String getD2S(String param){

		String result = "";

		if (param != ""){
			result = param.substring(0,param.indexOf("."));
		}


		return result;

	}

	/**  두 날짜 사이의 차이
	 *
	 *  @param startDate 시작 날짜
	 *  @param endDate   종료 날짜
	 *  @param format    날짜 형식
	 *  @return long     날짜 차이
	 */
	public static long calDateRange( String startDate, String endDate ) throws Exception{

		String	format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date sDate;
		Date eDate;
		long day2day = 0;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
			day2day = (eDate.getTime() - sDate.getTime()) / (1000*60*60*24);
		} catch(Exception e) {
			throw new Exception("wrong format string");
		}

		return day2day;
	}

	/**
	 * 파라메터의 값을 숫자화할 수 있는 것만 추출해서 문자열로 리턴한다.
	 */
	public static String getNum(String pValue){
		String rValue = "0";
		StringBuffer sb = new StringBuffer();

		if(pValue != null){
			for(int i=0;i<pValue.length();i++){
				if("-0123456789.".indexOf(pValue.substring(i,i+1)) >= 0){
					sb.append(pValue.substring(i,i+1));
				}
			}

			rValue = sb.toString();
			if(pValue.indexOf("(") == 0){//괄호가 쳐져 있는 경우는 -(마이너스)로 인식한다.
				rValue = "-" + rValue;
			}
		}

		return rValue.equals("") ? "0" : rValue;
	}
	public static String getNum(java.math.BigDecimal pValue){
		return pValue == null ? "0" : pValue + "";
	}
	public static String getNum(Object pValue){
		return JspUtil.getNum((String) pValue);
	}
	public static String getNum(int pValue){
		return pValue + "";
	}
	public static String getNum(long pValue){
		return pValue + "";
	}
	/** 문자열로 숫자를 리턴한다. 고정소수점 형식으로 리턴한다.(지수표현을 사용안함)  */
	public static String getNum(double pValue){
		return new DecimalFormat("0.##########").format(pValue);
	}
	/** 문자열로 숫자를 리턴한다. 고정소수점 형식으로 리턴한다.(지수표현을 사용안함)  */
	public static String getNum(float pValue){
		return new DecimalFormat("0.##########").format(pValue);
	}

	/**
	 * 파라메터의 값을 숫자화할 수 있는 것만 추출해서 double로 리턴한다.
	 */
	public static double getDouble(String pValue){
		return Double.parseDouble(getNum(pValue));
	}
	public static double getDouble(java.math.BigDecimal pValue){
		return pValue == null ? 0 : pValue.doubleValue();
	}
	public static double getDouble(Object pValue){
		return JspUtil.getDouble((String) pValue);
	}
	public static double getDouble(int pValue){
		return pValue;
	}
	public static double getDouble(long pValue){
		return pValue;
	}
	public static double getDouble(double pValue){
		return pValue;
	}
	public static double getDouble(float pValue){
		return pValue;
	}

	/**
	 * 파라메터의 값을 숫자화할 수 있는 것만 추출해서 Long으로 리턴한다. 소수점 이하는 버린다.
	 */
	public static long getLong(String pValue){
		String v = getNum(pValue);
		if(v.indexOf(".") != -1){
			return Math.round(Math.floor(Double.parseDouble(v)));
		}else{
			return Long.parseLong(getNum(pValue));
		}
	}
	public static long getLong(java.math.BigDecimal pValue){
		return pValue == null ? 0 : pValue.longValue();
	}
	public static long getLong(Object pValue){
		return JspUtil.getLong((String) pValue);
	}
	public static long getLong(int pValue){
		return pValue;
	}
	public static long getLong(long pValue){
		return pValue;
	}
	public static long getLong(double pValue){
		return Math.round(Math.floor(pValue));
	}
	public static long getLong(float pValue){
		return Math.round(Math.floor(pValue));
	}
	/**
	 * 파라메터의 값을 숫자화할 수 있는 것만 추출해서 Int으로 리턴한다. 소수점 이하는 버린다.
	 */
	public static int getInt(String pValue){
		String v = getNum(pValue);
		if(v.indexOf(".") != -1){
			return Integer.parseInt(Math.floor(Double.parseDouble(v)) + "");
		}else{
			return Integer.parseInt(getNum(pValue));
		}
	}
	public static int getInt(java.math.BigDecimal pValue){
		return pValue == null ? 0 : pValue.intValue();
	}
	public static int getInt(Object pValue){
		return JspUtil.getInt((String) pValue);
	}
	public static int getInt(int pValue){
		return pValue;
	}
	public static int getInt(long pValue){
		return (int) pValue;
	}
	public static int getInt(double pValue){
		return (int) Math.round(Math.floor(pValue));
	}
	public static int getInt(float pValue){
		return (int) Math.round(Math.floor(pValue));
	}

	/** 입력된 숫자형문자열을 1000 단위 구분자를 추가하여 문자열로 리턴한다. */
	public static String getMFormat(String pValue){
		return JspUtil.getMFormat(JspUtil.getDouble(pValue));
	}
	public static String getMFormat(java.math.BigDecimal pValue){
		return new DecimalFormat("###,##0.##########").format(pValue);
	}
	public static String getMFormat(Object pValue){
		return JspUtil.getMFormat((String) pValue);
	}
	/** 1000 단위 구분자를 추가하여 문자열로 리턴한다. */
	public static String getMFormat(double pValue){
		return new DecimalFormat("###,##0.##########").format(pValue);
	}
	/** 1000 단위 구분자를 추가하여 문자열로 리턴한다. */
	public static String getMFormat(float pValue){
		return new DecimalFormat("###,##0.##########").format(pValue);
	}
	/** 1000 단위 구분자를 추가하여 문자열로 리턴한다. */
	public static String getMFormat(int pValue){
		return new DecimalFormat("###,##0.##########").format(pValue);
	}
	/** 1000 단위 구분자를 추가하여 문자열로 리턴한다. */
	public static String getMFormat(long pValue){
		return new DecimalFormat("###,##0.##########").format(pValue);
	}

	/** pFormat 형식에 맞게 숫자포맷으로 값을 리턴한다. */
	public static String getMFormat(String pValue, String pFormat){
		return JspUtil.getMFormat(JspUtil.getDouble(pValue));
	}
	public static String getMFormat(java.math.BigDecimal pValue, String pFormat){
		return new DecimalFormat(pFormat).format(pValue);
	}
	public static String getMFormat(Object pValue, String pFormat){
		return JspUtil.getMFormat((String) pValue);
	}
	public static String getMFormat(double pValue, String pFormat){
		return new DecimalFormat(pFormat).format(pValue);
	}
	public static String getMFormat(float pValue, String pFormat){
		return new DecimalFormat(pFormat).format(pValue);
	}
	public static String getMFormat(int pValue, String pFormat){
		return new DecimalFormat(pFormat).format(pValue);
	}
	public static String getMFormat(long pValue, String pFormat){
		return new DecimalFormat(pFormat).format(pValue);
	}

	/**
	 * 문자열에 아무런 데이터가 없으면  빈공백 추가하고 데이터가 있으면 trim을 해준다
	 */
	public static String rns(String pValue){
		if(pValue == null){
			return "";
		}else{
			if(pValue.trim().equals("")){
				return "";
			}else{
				return pValue.trim();
			}
		}
	}

	/**
	 * 문자열에 아무런 데이터가 없으면  & nbsp; 추가하고 데이터가 있으면 trim을 해준다
	 */
	public static String rns2(String pValue){
		if(pValue == null){
			return "&nbsp;";
		}else{
			if(pValue.trim().equals("")){
				return "&nbsp;";
			}else{
				return pValue.trim();
			}
		}
	}

	/**
	 * 정수형문자열에 아무런 데이터가 없으면  빈공백을  추가하고 데이터가 있으면 trim 및 콤마를 제거한다.
	 */
	public static String rns3(String pValue){
		if(pValue == null){
			return "0";
		}else{
			if(pValue.trim().equals("")){
				return "0";
			}else{
				return pValue.trim().replaceAll(",", "");
			}
		}
	}

	/**
	 *Converts String to float.
	 *
	 *Parameters:
	 *    value -
	 *    defaultValue -
	 *Returns:
	 *    converted value, or default value if error
	 */
	public static float toFloat(String value, float defaultValue) {
		if (value == null)
			return defaultValue;
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException nfex) {
			return defaultValue;
		}
	}

	/**
	 *Converts String to double.
	 *
	 *Parameters:
	 *    value -
	 *    defaultValue -
	 *Returns:
	 *    converted value, or default value if error
	 */
	public static double toDouble(String value, double defaultValue) {
		if (value == null)
			return defaultValue;
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException nfex) {
			return defaultValue;
		}
	}

	/**
	 *Converts String to int.
	 *
	 *Parameters:
	 *    value -
	 *    defaultValue -
	 *Returns:
	 *    converted value, or default value if error
	 */
	public static int toInt(String value, int defaultValue) {
		if (value == null)
			return defaultValue;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfex) {
			return defaultValue;
		}
	}

	/**
	 *Converts String to long.
	 *
	 *Parameters:
	 *    value -
	 *    defaultValue -
	 *Returns:
	 *    converted value, or default value if error
	 */
	public static long toLong(String value, long defaultValue) {
		if (value == null)
			return defaultValue;
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException nfex) {
			return defaultValue;
		}
	}

	/**
	 *Converts String to byte.
	 *
	 *Parameters:
	 *    value -
	 *defaultValue -
	 *    Returns:
	 *    converted value, or default value if error
	 */
	public static byte toByte(String value, byte defaultValue) {
		if (value == null)
			return defaultValue;
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException nfex) {
			return defaultValue;
		}
	}

	/**
	 *윤년인지 판단한다.
	 *
	 *Parameters:
	 *    year - 년도
	 *Returns:
	 *    윤년 true 아니면 fals
	 */
	public static boolean isLeafYear(int year) {
		return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
	}

	/**
	 * source의 길이가 length만큼 될 때 까지 source 앞에 0을 붙여 리턴한다.
	 * 예) fillZero('123',5) ==> '00123'
	 *
	 * Parameters:
	 *     source - 앞에 0을 붙일 문자열
	 *     length - 0을 붙여 만들 문자열의 전체 길이
	 * Returns:
	 *     java.lang.String
	 */
	public static String fillZero(String source, int length) {
		if (source == null)
			return "";
		if (source.length() >= length)
			return source;
		for (; source.length() < length; source = "0" + source)
			;
		return source;
	}

	/**
	 * YYYYMM 의 파라메터를 받아 해당월의 마지막 일자를 반환한다.
	 */
	public static int Last_Date(String ss){
		int in_i=0, in_k=0, Last_Date=0;

		in_k = Integer.parseInt(ss.substring( 0, 4 ));
		in_i = Integer.parseInt(ss.substring( 4, 6));

		if( in_i == 1 || in_i == 3 || in_i == 5 || in_i == 7 || in_i == 8 || in_i == 10 || in_i == 12 ){
			Last_Date = 31;
		}else if( in_i == 4 || in_i == 6 || in_i == 9 || in_i == 11 ){
			Last_Date = 30;
		}else if( in_i == 2 ) {
			if(isLeafYear(in_k)) Last_Date = 29;
			else Last_Date = 28;
		}else{
			Last_Date = -1;
		}
		return Last_Date;
	}

	/**
	 * YYYYMMDD 의 파라메터를 받아 두기간의 월차이를 반환한다.
	 */
	public static int monthDiff(String from, String to){
		int s_y, s_m, e_y, e_m;
		int rValue = 0;

		if(from.trim().length() >= 6 && to.trim().length() >= 6){
			s_y = Integer.parseInt(from.substring(0,4));
			s_m = Integer.parseInt(from.substring(4,6));

			e_y = Integer.parseInt(to.substring(0,4));
			e_m = Integer.parseInt(to.substring(4,6));

			rValue = ((e_y - s_y) * 12) + (e_m - s_m);
		}

		return rValue;
	}

	/**
	 * YYYYMMDD 의 파라메터를 받아 두기간의 근속기간을 년도만 반환한다.
	 */
	public static double Gunsok_Year(String s, String d){
		String stt_str, stt_y;

		stt_str = Gunsok_Date(s,d);
		stt_y = stt_str.substring(0,4);
		return toDouble(stt_y, 0.0);
	}

	/**
	 * YYYYMMDD 의 파라메터를 받아 두기간의 근속기간을 년월을 1.5년 형식으로 반환한다.
	 */
	public static double Gunsok_Month(String s, String d){
		String stt_str, stt_y, stt_m, stt_d;

		stt_str = Gunsok_Date(s,d).replaceAll("-","");

		stt_y = stt_str.substring(0,4);
		stt_m = stt_str.substring(4,6);
		stt_d = stt_str.substring(6,8);

		return (toInt(stt_y,0) + (toInt(stt_m,0) / 12.0) + (toInt(stt_d,0) / 365.0));
	}

	/**
	 * YYYYMMDD 의 파라메터를 받아 두기간의 근속기간을 YYYYMMDD 형식으로 반환한다.(ex 1년 4개월 22일 경우 00010422 반환)
	 */
	public static String Gunsok_Date(String s, String d){
		int stt_y = 0, stt_m = 0, stt_d = 0, stt_month;
		int tmpSDay, tmpDDay;

		tmpSDay = toInt(s.substring(6,8),0);
		tmpDDay = toInt(d.substring(6,8),0);

		stt_month = (int) monthDiff(s,d);
		stt_y = stt_month / 12;
		stt_m = stt_month % 12;

		if(tmpSDay == 1){
			if(tmpDDay >= Last_Date(d)){
				stt_m += 1;
				stt_d = 0;
			}else{
				stt_d = tmpDDay;
			}
		}else if(tmpSDay - 1 <= tmpDDay)
			stt_d = tmpDDay - tmpSDay + 1;
		else if(tmpDDay >= Last_Date(d))
			stt_d = Last_Date(s) - tmpSDay + 1;
		else{
			if(stt_month == 0){
				//잘못된 데이터 입니다.;
			}else{
				stt_m -= 1;

				stt_d = (Last_Date(s) - tmpSDay + 1) + tmpDDay;
			}
		}

		if(stt_m < 0){
			stt_y -= 1;
			stt_m = 11;
		}else if(stt_m == 12){
			stt_y += 1;
			stt_m = 0;
		}

		return fillZero(stt_y + "",4) + fillZero(stt_m + "",2) + fillZero(stt_d + "",2);
	}

	/**
	 * str을 size길이에 맞도록 왼쪽에 f_char로 채운다.
	 *
	 * Parameters:
	 *     str - 원래 문자열
	 *     f_char - 원래 문자열 뒤에 붙일 문자열
	 *     size - f_char를 붙여 만들 문자열의 길이
	 *
	 * Returns:
	 *     String 원래 문자열에 f_char를 붙여 만든 문자열
	 */
	public static String lpad(String str, String f_char, int size) {
		if (str.length() >= size)
			return str;
		else
			return getFillChar("", f_char, size - str.length()) + str;
	}

	/**
	 * str을 size길이에 맞도록 오른쪽에 f_char로 채운다.
	 *
	 * Parameters:
	 *     str - 원래 문자열
	 *     f_char - 원래 문자열 뒤에 붙일 문자열
	 *     size - f_char를 붙여 만들 문자열의 길이
	 *
	 * Returns:
	 *     String 원래 문자열에 f_char를 붙여 만든 문자열
	 */
	public static String rpad(String str, String f_char, int size) {
		if (str.length() >= size)
			return str;
		else
			return str + getFillChar("", f_char, size - str.length());
	}

	/**
	 * str을 size길이에 맞도록 뒤에 f_char로 채운다.
	 *
	 * Parameters:
	 *     str - 원래 문자열
	 *     f_char - 원래 문자열 뒤에 붙일 문자열
	 *     size - f_char를 붙여 만들 문자열의 길이
	 *
	 * Returns:
	 *     String 원래 문자열에 f_char를 붙여 만든 문자열
	 */
	public static String getFillChar(String str, String f_char, int size) {
		String fillChar = "";
		if (str.length() >= size)
			return str;
		for (int index = 0; index < size - str.length(); index++)
			fillChar = fillChar + f_char;

		return str + fillChar;
	}
	
	public static String ko(String en) { 
		 
	    String data=new String(""); 
		if( en == null)  
			return data; 
	    try { 
	        data = new String(en.getBytes("8859_1"),"KSC5601"); 
	    } catch (Exception e) { } 
	    return data; 
	} 
	 
	public static String en(String ko) { 
	    String data = new String(""); 
	    if (ko==null) 
	        return data; 
	 
	    try { 
	        data = new String(ko.getBytes("KSC5601"),"8859_1"); 
	    } catch (java.io.UnsupportedEncodingException e) { } 
	 
	    return data; 
	} 

}

