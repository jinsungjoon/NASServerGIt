package com.nwtns.framework.mvc;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.nwtns.framework.util.NWLog;
import com.nwtns.framework.util.NWUtil;
import com.nwtns.framework.util.ParseJSON;


/**
 * 전체 Command 클래스에서 사용되는 부모 클래스
 * 1. 초기화 작업
 * 2. 넘어온 파라미터 추출 
 * 3. db session 관리
 * 4. 사용자 Session 관리
 * 
 * @since 20131126 최초작성
 * @author 최정주
 *
 */
public class NWSuperCommand {

	//protected NWActionForward action;
	
	/* session 관련 */
	//protected boolean bool_session = false;	// 로그인 여부
	
	/* 기타 변수 선언 */
	//protected String jspURL 			= ""; 	// JSP 파일명
	//protected String serviceFlag 		= "";	// service 메소드에서 업무 분기를 위한 flag
	//protected int nowpage 				= 1;	// 현재 페이지

	/* 파싱 관련 */
	protected ParseJSON NWJson 			= null;	// 공통 JSON 파싱 클래스
	//protected JSONObject jsonRoot 		= null;
	//protected JSONArray jsonArr 		= null;
	//protected JSONObject jsonObj 		= null;
	//protected HashMap<String,Object> param 	= null;		// 넘어온 Parameter를 담는 변수
	
	/* MQ */
	//protected String GET_QNAME = null;
	//protected String PUT_QNAME = null;
	
	/*********************************************************
	 * 초기화 및 세팅
	 * 	1. 초기화 
	 *  2. session 처리 
	 *  3. parameter 세팅
	 *  4. db access
	 **********************************************************/
	protected HashMap<String,Object> excute(HttpServletRequest req) throws Exception{

		NWJson = new ParseJSON();
		//jsonRoot 	= new JSONObject();
		//jsonArr 	= new JSONArray();
		//jsonObj 	= new JSONObject();
		
		//bool_session = sessionCheck(req);
		//requestParam(req);

		// 파라미터를 Map에 매핑 -------------------------------------------------------------------------------------
		HashMap<String,Object> param 		= new HashMap<String,Object>();
		//NWLog.printParam(this.getClass(), req);	// 넘어온 파라미터 로그를 출력
		
		Enumeration parameters = req.getParameterNames();
		String parameterName = "";
		while (parameters.hasMoreElements()) {
			parameterName = (String) parameters.nextElement();		
			param.put(parameterName, NWUtil.print(req.getParameter(parameterName), ""));
			NWLog.i(this.getClass(), parameterName + " :: " + NWUtil.print(req.getParameter(parameterName), ""));
		}
		NWLog.i(this.getClass(),"Parameter End");
		// 파라미터를 Map에 매핑 -------------------------------------------------------------------------------------
		return param;
	}

	/*********************************************************
	 * 2. Request Parameter Receive
	 *    - 넘어온 파라미터를 Map에 자동 매핑
	 **********************************************************/
//	protected void requestParam(HttpServletRequest req) throws Exception {
//		param 		= new HashMap<String,Object>();
//		NWLog.printParam(this.getClass(), req);	// 넘어온 파라미터 로그를 출력
//		
//		//serviceFlag = NWUtil.print(req.getParameter("serviceFlag"), "");
//		//nowpage = NWUtil.print(req.getParameter("nowpage"),1);
//		
//		Enumeration parameters = req.getParameterNames();
//		String parameterName = "";
//
//		while (parameters.hasMoreElements()) {
//			parameterName = (String) parameters.nextElement();		
//			param.put(parameterName, NWUtil.print(req.getParameter(parameterName), ""));
//			NWLog.d(this.getClass(), parameterName + " :: " + NWUtil.print(req.getParameter(parameterName), ""));
//		}
//	}

	protected boolean sessionCheck(HttpServletRequest request) throws Exception {
		// session
		if(request.getSession().getAttribute("SESSION_USR_ID") != null){
//			ses_usr_id = (String)request.getSession().getAttribute("SESSION_USR_ID");
//			ses_usr_fg = (String)request.getSession().getAttribute("SESSION_USR_FG");
//			ses_usr_nm = (String)request.getSession().getAttribute("SESSION_USR_NM");
//			ses_ptn_cd = (String)request.getSession().getAttribute("SESSION_PTN_CD");
//			ses_ptn_nm = (String)request.getSession().getAttribute("SESSION_PTN_NM");
			//bool_session = true;
		}
		//return bool_session;
		return true;
	}

	protected NWActionForward JspForward(NWActionForward action , String jspURL){
		action = new NWActionForward();
		action.setUrl(jspURL);
		action.setRedirect(false);
		action.setAjax(false);	
		action.setTimeout("120000");
		return action;
	}
	
	protected NWActionForward jsonForward(NWActionForward action , JSONObject resJson){
		action = new NWActionForward();
		action.setRedirect(false);
		action.setAjax(true);	
		action.setResData(resJson.toJSONString());
		action.setTimeout("120000");
		return action;
	}
	
	protected NWActionForward jsonForward(NWActionForward action , JSONObject resJson, String callback){
		action = new NWActionForward();
		action.setRedirect(false);
		action.setAjax(true);	
		action.setTimeout("120000");
		action.setResData(callback + "(" + resJson.toJSONString() + ")");
		return action;
	}
	protected NWActionForward jsonForward(NWActionForward action , org.json.JSONObject resJson, String callback){
		action = new NWActionForward();
		action.setRedirect(false);
		action.setAjax(true);	
		action.setTimeout("120000");
		action.setResData(callback + "(" + resJson.toString() + ")");
		return action;
	}
	protected NWActionForward stringForward(NWActionForward action , String jsonString){
		action = new NWActionForward();
		action.setRedirect(false);
		action.setAjax(true);	
		action.setTimeout("120000");
		action.setResData(jsonString);
		return action;
	}
	
	protected NWActionForward stringForward(NWActionForward action , String jsonString, String callback){
		action = new NWActionForward();
		action.setRedirect(false);
		action.setAjax(true);	
		action.setTimeout("120000");
		action.setResData(callback + "(" + jsonString + ")");
		return action;
	}
}