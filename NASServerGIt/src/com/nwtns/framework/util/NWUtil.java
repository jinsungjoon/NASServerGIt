package com.nwtns.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



/**
 * 기타 유틸 메소드 모음
 * @author 최정주
 */
public class NWUtil {
	/**
	 * 오브젝트가 null 체크
	 * 
	 * @param o
	 * @param change
	 * @return
	 */
	public static String print(Object o, String change) {
		if (o == null)
			return change + "";
		else {
			return o.toString().trim();
		}
	}
	
	public static String printZero(Object o) {
		if (o == null)
			return "0";
		else {
			return o.toString().trim().equals("") ? "0" : o.toString().trim();
		}
	}
	
	public static String printXSS(Object o, String change) {
		if (o == null)
			return change + "";
		else {
			return cleanXSS(o.toString().trim());
		}
	}
	
	private static String cleanXSS(String value) {
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        // 2014.04.10[KDBAEK] 거래처명등에 '()'가 실제 들어가는 데이터가 있어서 반영하지 않는다.
        //value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        
        return value;
    }

	/**
	 * int값 null 체크
	 * 
	 * @param o
	 * @param change
	 * @return
	 */
	public static int print(Object o, int change) {
		if (o == null)
			return change;
		else
			return Integer.parseInt(o.toString());
	}
	
	public static String getCurrentDate() {
		Calendar currentCal = Calendar.getInstance();
		
		String strDate = String.format("%04d%02d%02d%02d%02d%02d"
				, currentCal.get(Calendar.YEAR)
				, currentCal.get(Calendar.MONTH)+1
				, currentCal.get(Calendar.DAY_OF_MONTH)
				, currentCal.get(Calendar.HOUR_OF_DAY)
				, currentCal.get(Calendar.MINUTE)
				, currentCal.get(Calendar.SECOND));
		
		return strDate;
	}
	

	/**
	 * []
	 *  - 2016.02.17[KDBAEK] 입진단 취약으로 결과가 나와, 사용하지 않아 주석 처리 함.
	 *    취약성 설명 : 입력 스트림에 EOL문자(\r 또는 \n) 가 포함되지 않는 경우,시스템 자원을 모두 소모시키고,OutOfMemory 오류를 발생시키며 Application 을 종료시킨다. 이 취약점을 이용해서 공격자는 상당히 큰 입력파일을 제공하여 애플리케이션에 대한 공격을 실행할 수 있다.
	 *    조치 가이드 : 
	          1) 읽을 자원의 용량 제한을 하여야 한다.
              2) readLine() 대신 길이 제한(BufferedReader.read()) 을 강제 실행하는 메서드를 사용하는 것이 좋다.
                 BufferedReader br = new BufferedReader InputStreamReader(new FileInputStream(file),”UTF8”));
                 //creates buffer
                 Char[] buffer = new char[MAX_SIZE];
                 br.read(buffer,0,MAX_SIZE);
	 * @param url
	 * @param params
	 * @return
	 */
//	public static StringBuffer httpPost(String url) {
//		StringBuffer buff = new StringBuffer();
//
//		try {
//			
//			URL strUrl = new URL(url);
//
//			HttpURLConnection con = (HttpURLConnection) strUrl.openConnection();
//
//			// 서버로부터 메세지를 받을 수 있도록 한다. 기본값은 true이다.
//			con.setDoInput(true);
//
//			// 헤더값을 설정한다.
//			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//
//			// 전달 방식을 설정한다. POST or GET, 기본값은 GET 이다.
//			con.setRequestMethod("POST");
//
//			// 서버로 데이터를 전송할 수 있도록 한다. GET방식이면 사용될 일이 없으나, true로 설정하면 자동으로 POST로 설정된다. 기본값은 false이다.
//			con.setDoOutput(true);
//
//			// POST방식이면 서버에 별도의 파라메터값을 넘겨주어야 한다.
//			// String param =
//			// "ID=rQ+g4R8qmTlAey1Wn/PwUA==&cust_no=vBiSI2BWVsu6lK03U7dsfA==&prom_no=";
//			//String param = "ID=" + URLEncoder.encode("aaaaa","UTF-8") + "&cust_no=" + URLEncoder.encode("aaaaa","UTF-8");
//			
//			String param = URLEncoder.encode(url,"UTF-8"); //"ID=" + URLEncoder.encode("aaaaa","UTF-8") + "&cust_no=" + URLEncoder.encode("aaaaa","UTF-8");
//
//			OutputStream out_stream = con.getOutputStream();
//
//			out_stream.write(param.getBytes("UTF-8"));
//			out_stream.flush();
//			out_stream.close();
//
//			InputStream is = null;
//			BufferedReader in = null;
//
//			is = con.getInputStream();
//			in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
//
//			String line = null;
//
//			while ((line = in.readLine()) != null) {
//				buff.append(line + "\n");
//			}
//						
//		} catch (MalformedURLException ex) {
////			System.out.println(ex);
//		} catch (IOException ex) {
////			System.out.println(ex);
//		}
//		return buff;
//	}
	
	// BY ISH POST proc
	public static String httpPost(String url, HashMap<String, String> params) {

		 String body = "";
		 String keyName = "";
		 String value = "";
		 HttpClient client = new HttpClient();
	     PostMethod postMethod = null;

	     try {
	    	client.getParams().setSoTimeout(60*1000); //타임아웃은 5초
            client.getHttpConnectionManager().getParams().setConnectionTimeout(60*1000);
            client.getHttpConnectionManager().getParams().setSoTimeout(60*1000);
//            client.getParams().setSoTimeout(30*1000); //타임아웃은 5초
//            client.getHttpConnectionManager().getParams().setConnectionTimeout(30*1000);
//            client.getHttpConnectionManager().getParams().setSoTimeout(30*1000);
            client.getParams().setParameter("http.protocol.content-charset", "KSC5601");
		
            postMethod = new PostMethod(url);
            postMethod.getParams().setSoTimeout(30*1000);

            if(params != null) {
            	Set key = params.keySet();
            	for (Iterator iterator = key.iterator(); iterator.hasNext();) {
            		keyName = (String)iterator.next();
                    value = (String)params.get(keyName);
                    postMethod.addParameter(new NameValuePair(keyName,value));
            	}
            }

            int status = client.executeMethod(postMethod);
            if (status != HttpStatus.SC_OK) {
//                System.out.println("PostMethod failed: " + postMethod.getStatusLine());
                return "FAIL"; // 오류 발생 시에는 FAIL을 리턴한다.
            }
            
            String resCharSet =  postMethod.getResponseCharSet();
            //NWLog.d(NWUtil.getClass(), "resCharSet : " + resCharSet);
//            System.out.println("resCharSet : " + resCharSet);
            body = postMethod.getResponseBodyAsString();
//            NWLog.e("body : " + body);
//            System.out.println("body : " + body);

        } catch(Exception e) {
        	NWLog.d(NWUtil.class, e);
//        	System.out.println(e);
        	return "FAIL"; // 오류 발생 시에는 FAIL을 리턴한다.
        } finally {
            if(postMethod !=null) postMethod.releaseConnection();
        }
		return body;
	}
	
	public static String httpPost(String url, String params) throws Exception {
		CloseableHttpClient httpClient = new DefaultHttpClient();
       String result = null;

        //HttpPost객체 생성하고 호출 URL설정
        HttpPost httpPost = new HttpPost(url);

        //StringEntity에 해당 값을 담고, HttpPost에 세팅함
        StringEntity stringEntity = new StringEntity(params, "UTF-8");
        httpPost.setEntity(stringEntity);

        //body 및 form data encoding type 지정
        httpPost.setHeader("CONTENT-TYPE", "application/json");
        httpPost.setHeader("ACCEPT", "application/json");
        httpPost.setHeader("USER-AGENT", "Mozilla/5.0");

        //execute메소드 실행 하여 결과값 셋팅
        HttpResponse response = null;
        response = httpClient.execute(httpPost);
        result = EntityUtils.toString(response.getEntity(), "UTF-8");

        return result;
	}
	
	public static void main(String[] args) {
		NWUtil util = new NWUtil();
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("empnum", "1");  
		map.put("SALRTE", "1");   
		map.put("CALLOC", "1");  
		map.put("SALNUM", "1");  
		map.put("EXCHGNUM", "1"); 
		map.put("CUSTNUM", "1"); 
		
		util.httpPost("http://www.daum.net/", map);
	}

	/********************************************************************************
	 *<br>
	 *정규식 패턴을 이용한 문자열 변환 <br>
	 * 
	 * @param str
	 * @param pattern
	 * @param replace
	 * @return
	 *******************************************************************************/
	public static String rplc(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();
		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}
	

	/********************************************************************************
	 *<br>
	 *인자로 받은 값(Object)가 null인지 검사한다. <br>
	 * 
	 * @param obj
	 * @return null인지 검사값
	 *******************************************************************************/
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		return (obj instanceof String) && "".equals(((String) obj).trim());
	}


    /**
     * 고유값을 리턴한다.
     * @param lastNum
     * @return
     */
    public static String getUnique() {
    	String time = "";
    	
    	//2016.02.04[KDBAEK] Random => SecureRandom 변경
    	int rand1 = (new SecureRandom()).nextInt(1000);
    	//int rand1 = (int)(Math.random() * 1000);
    	
    	if(rand1 < 100){
    		rand1 = rand1 + 100;
    	}
   		time = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new java.util.Date())+rand1;
        return time;
    }
    
	//random 문자 발생
	public static String getRandoString(int loopCount) {
		String dummyString="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz";
		//2016.02.04[KDBAEK] Random => SecureRandom 변경
   	 	SecureRandom rn = new SecureRandom();
		//Random rn=new Random();
		// char 48=0 65=A 90=Z 97=a 122=z
		StringBuilder tempBuilder=new StringBuilder(100);
		int randomInt;
		char tempChar;
		for(int loop=0;loop<loopCount;loop++) {
			randomInt=rn.nextInt(61);
			tempChar=dummyString.charAt(randomInt);
			tempBuilder.append(tempChar);
		}
		//System.out.println(tempBuffer);
		return tempBuilder.toString();
	}
	
	//random 문자 발생
	public static String getRandoNumber(int loopCount) {
		String dummyString="1234567890";
		//2016.02.04[KDBAEK] Random => SecureRandom 변경
   	 	SecureRandom rn = new SecureRandom();
		//Random rn=new Random();
		// char 48=0 65=A 90=Z 97=a 122=z
		StringBuilder tempBuilder=new StringBuilder(100);
		int randomInt;
		char tempChar;
		for(int loop=0;loop<loopCount;loop++) {
			randomInt=rn.nextInt(61);
			tempChar=dummyString.charAt(randomInt);
			tempBuilder.append(tempChar);
		}
		//System.out.println(tempBuffer);
		return tempBuilder.toString();
	}

	/*10 이하 숫자 앞에 0 붙이기*/
	public static String appendZero(int num){
		String zero = "0";
		if(num < 10){
			zero = zero + Integer.toString(num);
		}else{
			zero = Integer.toString(num);
		}
		return zero;
	}
    /* 전화번호 짜르기*/
	public static String splitPhoneNumber(String str, String gubun, int arryNum) {
		String strarry[] = {"","",""};
		int i=0;
		if(str != ""){
			StringTokenizer st = new StringTokenizer(str, gubun);
			
			while (st.hasMoreTokens()) {
				strarry[i] = st.nextToken();
				i++;
			}
		}
		
		return strarry[arryNum];
	}
    /* 게시판 제목 substring ... */
    public static String moreSubject(String str,int num){
    	
    	if(str.length() > num ){
    		str = str.substring(0,num)+"..";
    	}    	
    	return str;
    }
	//메일 자르기
	public static String splitEmail(String str, int num, String gubun) {

		String strarry[] = {"",""};
		int i=0;
		if(str != "" && str != null){
			StringTokenizer st = new StringTokenizer(str, gubun);
			while (st.hasMoreTokens()) {
				strarry[i] = st.nextToken();
				i++;
			}
		}
		return strarry[num];
	}
	
	/**
	 * 숫자에 , 붙이는 정규 표현식
	 * @param number
	 * @return
	 */
	public static String appendComma(String number){
		return number.replaceAll("(?<=[0-9])(?=(?:[0-9]{3})+(?![0-9]))",",");
	}

    public static String getDateType(String type) {
    	String time = "";
    	
    	if(type.equals("A"))       time = "yyyy-MM-dd aa hh:mm:ss";
    	else  if(type.equals("B")) time = "yyyyMMddHHmmss";
    	else  if(type.equals("C")) time = "yyyy-MM-dd HH:mm:ss";
    	else  if(type.equals("D")) time = "yyyyMMdd";
    	else  if(type.equals("E")) time = "yyyy-MM-dd HH:mm";
    	else  if(type.equals("F")) time = "HH:mm";
    	else  if(type.equals("G")) time = "yyyyMM";
    	else  if(type.equals("H")) time = "yyyy-MM-dd";
    	else  time = "yyyy-MM-dd aa hh:mm:ss";

        return (new SimpleDateFormat(time)).format(new java.util.Date());
    }
    
    // format : yyyyMMddHHmmss
    public static String getSysDate(String format) {
    	return (new SimpleDateFormat(format)).format(new java.util.Date());
    }
    
    
    public static String getProperty(String propFile, String propName) {
//    	System.out.println("propFile:"+propFile);
//    	System.out.println("propName:"+propName);
		String value = ""; 
		Properties prop = new Properties();
		
	     try {
	    	String MYPATH=NWUtil.class.getResource("").getPath();
	 		int positionNo = MYPATH.indexOf("WEB-INF");
	 		//OS에 따른 MYPATH 분기. 유닉스 계열의 경우 루트 폴더가 빠져서 substring을 0부터
	 		String osName = System.getProperty("os.name").toLowerCase();
	 		if(osName.indexOf("nix")>0||osName.indexOf("nux")>0)
	 			MYPATH = MYPATH.substring(0, positionNo);  
	 		else if(osName.indexOf("dow")>0)
	 			MYPATH = MYPATH.substring(1, positionNo);  
	 		else
	 			MYPATH = MYPATH.substring(0, positionNo);  
	 		
	 		//NWLog.d(NWUtil.class, "NWUtil.getProperty - MYPATH : " + MYPATH);
	 		
	 		String fullPath = MYPATH+File.separator+"WEB-INF"+File.separator+"conf"+File.separator+propFile.toString();
	 		//String fullPath = "D:/MobileWas/StarPlus/LGCare/StarPlus-Dev/WEB-INF/conf/mserver.properties";
	 		
	 		//NWLog.d(NWUtil.class, fullPath);
	 		
	 		InputStream stream = new FileInputStream(fullPath);
	 		prop.load(stream);
	 		stream.close();
	 		
	 		value = prop.get(propName.toString()).toString();
	 		
	 		NWLog.d(NWUtil.class, String.format("%s-%s : %s", propFile, propName, value));
	     } catch(IOException ex){
	    	 NWLog.e(NWUtil.class, "******** NWUtil.getPropery Error");
	         ex.printStackTrace();
	     }
	     
	     return value;
	}
    
    //Random 하게 문자열 재배열
    public static String Create_PassWord(String strValue) {
    	 //String으로 넘겨받은 사용자의 루트+사번+물류 코드 값을 char 변수에 넣는다.
    	 char[] cha = new char[strValue.length()];
    	 for(int chaNum = 0; chaNum < cha.length; chaNum++) {
    	    cha[chaNum] = strValue.charAt(chaNum);
    	 }
    	 
    	 int length = cha.length;
    	 
    	 StringBuffer sb = new StringBuffer();
    	 //2016.02.04[KDBAEK] Random => SecureRandom 변경
    	 SecureRandom rn = new SecureRandom();
         //Random rn = new Random();
         
         for( int i = 0 ; i < length ; i++ ){
             sb.append( cha[ rn.nextInt( cha.length ) ] );
         }
         
         NWLog.i(NWUtil.class,sb.toString());
         
         return sb.toString();

    }

    	  
}
