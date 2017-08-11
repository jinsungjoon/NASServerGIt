package com.nwtns.framework.util;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 로그 처리 클래스
 * 
 * @author 최정주
 * 
 */
public class NWLog {
	private static Logger logger = null;
	private static Logger CCBlogger = null;
	private static Logger CCHBlogger = null;

	public NWLog() {
	}

	/**
	 * log4j debug 수준으로 로그 출력
	 * 
	 * @param cls
	 * @param msg
	 */
	public static void d(Class cls, String msg) {
		logger = Logger.getLogger(cls);
		if (logger != null)
			logger.debug(msg);
	}
	
	public static void d(Class cls, Object result) {
		logger = Logger.getLogger(cls);
		if (logger != null)
			logger.debug(result);
	}
	
	//------------------------------------------------------------
	// 코카콜라용  Debug Logger
	//------------------------------------------------------------
	public static void dc(Class cls, String msg) {
		CCBlogger = Logger.getLogger("process.ccb");
		if (CCBlogger != null)
			CCBlogger.debug(msg);
	}
	
	public static void dc(Class cls, Object result) {
		CCBlogger = Logger.getLogger("process.ccb");
		if (CCBlogger != null)
			CCBlogger.debug(result);
	}		
	//------------------------------------------------------------
	
	//------------------------------------------------------------
	//해태음료용 Debug Logger
	//------------------------------------------------------------
	public static void dh(Class cls, String msg) {
		CCHBlogger = Logger.getLogger("process.cchb");
		if (CCHBlogger != null)
			CCHBlogger.debug(msg);
	}
	
	public static void dh(Class cls, Object result) {
		CCHBlogger = Logger.getLogger("process.cchb");
		if (CCHBlogger != null)
			CCHBlogger.debug(result);
	}
	//------------------------------------------------------------
	

	public static void e(Class cls, Exception e) {
		logger = Logger.getLogger(cls);
		if (logger != null) {
			StackTraceElement[] elem = e.getStackTrace();
			for (int i = 0; i < elem.length; i++) {
				logger.error(elem[i].toString());
			}
		}
	}
	
	public static void e(Class cls, String msg) {
		logger = Logger.getLogger(cls);
		if (logger != null)
			logger.error(msg);
	}
	
	public static void e(Class cls, Object result) {
		logger = Logger.getLogger(cls);
		if (logger != null)
			logger.error(result);
	}

	public static void i(Class cls, String msg) {
		logger = Logger.getLogger(cls);
		if (logger != null)
			logger.info(msg);
	}
	
	public static void i(Class cls, Object result) {
		logger = Logger.getLogger(cls);
		if (logger != null)
			logger.info(result);
	}

	/**
	 * request parameter 정보 출력
	 * 
	 * @param cls
	 * @param request
	 */
	public static void printParam(Class cls, HttpServletRequest request) {
		logger = Logger.getLogger(cls);
		if (logger != null) {
			Enumeration parameters = request.getParameterNames();
			String parameterName = "";

			while (parameters.hasMoreElements()) {
				parameterName = (String) parameters.nextElement();
				logger.info(parameterName + " :: " + NWUtil.print(request.getParameter(parameterName), ""));
			}
		}
	}

	//------------------------------------------------------------
	// 코카콜라용  Debug Logger
	//------------------------------------------------------------
	public static void printParamC(Class cls, HttpServletRequest request) {
		CCBlogger = Logger.getLogger("process.ccb");
		if (CCBlogger != null) {
			Enumeration parameters = request.getParameterNames();
			String parameterName = "";

			while (parameters.hasMoreElements()) {
				parameterName = (String) parameters.nextElement();
				CCBlogger.info(parameterName + " :: " + NWUtil.print(request.getParameter(parameterName), ""));
			}
		}
	}
	
	//------------------------------------------------------------
	// 해태음료용  Debug Logger
	//------------------------------------------------------------
	public static void printParamH(Class cls, HttpServletRequest request) {
		CCHBlogger = Logger.getLogger("process.cchb");
		if (CCHBlogger != null) {
			Enumeration parameters = request.getParameterNames();
			String parameterName = "";

			while (parameters.hasMoreElements()) {
				parameterName = (String) parameters.nextElement();
				CCHBlogger.info(parameterName + " :: " + NWUtil.print(request.getParameter(parameterName), ""));
			}
		}
	}	
	
}
