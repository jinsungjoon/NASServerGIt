package com.nwtns.nas.base;

import java.io.File;

import com.nwtns.framework.ParentConf;
import com.nwtns.framework.util.NWLog;
import com.nwtns.framework.util.NWUtil;

public class Conf extends ParentConf{

	//2015.09.02[KDBAEK] 여기 삭제되고 사용부분에서 회사구분이 적용되도록 변경해야 한다.
	public static final String PRJ_NM = "PS"; // CCB
	
	public static String appKey = "iuBBb8nUd4pBQxO5iAiXkHAxoek=";
	//public static String serviceConf = NWUtil.getProperty("mserver.properties", "SFAERP_SERVICE_CONF");
	//private static String serviceName = NWUtil.getProperty("mserver.properties", "SFAERP_SERVICE_NAME");
	
	// 
//	public static String UPLOAD_PATH_TEMP		= NWUtil.getProperty("mserver.properties", "UPLOAD_PATH_TEMP");
//	public static String UPLOAD_PATH_SIGN		= NWUtil.getProperty("mserver.properties", "UPLOAD_PATH_SIGN");
//	public static String UPLOAD_PATH_LOGFILE	= NWUtil.getProperty("mserver.properties", "UPLOAD_PATH_LOGFILE");
//	public static String UPLOAD_PATH_DB	    	= NWUtil.getProperty("mserver.properties", "UPLOAD_PATH_DB");
//	public static String PDA_CON_URL	        = NWUtil.getProperty("mserver.properties", "PDA_CON_URL");
//	public static String PDA_CON_USERNAME	    = NWUtil.getProperty("mserver.properties", "PDA_CON_USERNAME");
//	public static String PDA_CON_PASSWORD	    = NWUtil.getProperty("mserver.properties", "PDA_CON_PASSWORD");
	
	//public static String PDA_CON_PASSWORD	    = NWUtil.getProperty("mserver.properties", "PDA_CON_PASSWORD");
	

	// 파일 업로드 허용 용량
	public static final int MAX_UPLOAD_SIZE =  20 * 1024 * 1024;
	
	// 2017.07.28[KDBAEK] 변경
	//public static String CONFIG_PROPERTIES = "D:\\webRoot\\ASM_WEB\\WEB-INF\\conf\\config.properties"; //real
	public static String CONFIG_PROPERTIES = Conf.getConfigfile();
	public static String ROOT_PATH = Conf.getRootPath();

	public static String getRootPath() {
    	String MYPATH=Conf.class.getResource("").getPath();
 		int positionNo = MYPATH.indexOf("WEB-INF");
 		//OS에 따른 MYPATH 분기. 유닉스 계열의 경우 루트 폴더가 빠져서 substring을 0부터
 		String osName = System.getProperty("os.name").toLowerCase();
 		if(osName.indexOf("nix")>0||osName.indexOf("nux")>0)
 			MYPATH = MYPATH.substring(0, positionNo);  
 		else if(osName.indexOf("dow")>0)
 			MYPATH = MYPATH.substring(1, positionNo);  
 		else
 			MYPATH = MYPATH.substring(0, positionNo);  
 		
 		NWLog.d(NWUtil.class, "config.getProperty - MYPATH : " + MYPATH);
 		
 		return MYPATH;
	}
	
	public static String getConfigfile() {
 		String fullPath = Conf.getRootPath() +File.separator+"WEB-INF"+File.separator+"conf"+File.separator+"config.properties";
 		
 		NWLog.d(NWUtil.class, "config.getProperty - MYPATH : " + fullPath);
 		
 		return fullPath;
	}
	
	public static class DirConfig{
		public static int MAX_UPLOAD_SIZE =  0;							// 파일 업로드 허용 용량

		public static String FILE_TYPE_DEFAULT 		= "file";
		public static String FILE_TYPE_TEMP 		= "temp";
//		public static String FILE_TYPE_SIGN 		= "sign";
//		public static String FILE_TYPE_PIC			= "pic";
//		public static String FILE_TYPE_CLAIM		= "claim";
//		public static String FILE_TYPE_MARKET		= "market_issue";
//		public static String FILE_TYPE_FESTIVAL		= "festival";
//		public static String FILE_TYPE_SUGGEST		= "suggest";
		public static String FILE_TYPE_APPLOG 		= "applog";
		public static String FILE_TYPE_ORDER_LOG 	= "orderlog";

		public static String PATH_DIR_UPLOAD 		= "";	// 저장 폴더 루트
		public static String PATH_DIR_DEFAULT		= "";	// 파일 저장 폴더
		public static String PATH_DIR_TEMP			= "";	// 임시 파일 저장 폴더
		public static String PATH_DIR_APPLOG		= "";	// 로그 저장 폴더
		public static String PATH_DIR_ORDERLOG		= "";	// 주문 로그 저장 폴더
		public static String PATH_APP_FILE			= "";	// 앱 파일 폴더

	}

	public static class UploadConfig{
		public static  int MAX_UPLOAD_SIZE 			=  0;											// 파일 업로드 허용 용량

		public static   String FILE_TYPE_ALL = "FILE";
//		public static   String FILE_TYPE_SIGN = "SIGN";
//		public static   String FILE_TYPE_PICTURE = "PICTURE";
		public static   String FILE_TYPE_LOG = "LOG";
		public static 	String FILE_TYPE_APK = "APK";
		public static 	String FILE_TYPE_EXCEL = "EXCEL";

		public static  String PATH_TEMP_FILE	= "";		// 임시 파일 저장 폴더
		public static  String PATH_APP_LOG_FILE	= "";		// 로그 저장 폴더 테스트 서버

		public static String PATH_WOWZA_CTX = "";	// WOWZA 서버  CONTEXT PATH
		public static String PATH_DOCIMG = "";	// 문서 이미지 저장 폴더

	}
}
