package com.nwtns.nas.command;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.nwtns.framework.exception.ActionKeyException;
import com.nwtns.framework.mvc.NWActionForward;
import com.nwtns.framework.mvc.NWICommand;
import com.nwtns.framework.mvc.NWSuperCommand;
import com.nwtns.nas.dao.DbDao;

/**
 * 비즈니스 로직 처리 클래스
 * @author cjj
 *
 */
public class RptCommand extends NWSuperCommand implements NWICommand {

	private final String CMDVER_DDLIST_V01   = "RPT_DDLIST_V01";	    	// 일별 장애접수현황 조회
	
	
	private Logger logger = null;
	
	public RptCommand() {
		logger = Logger.getLogger(RptCommand.class.getName());
    }
	
	@Override
	public NWActionForward execute(HttpServletRequest req) throws ServletException, IOException, ActionKeyException {
		NWActionForward action = null;
		String serviceFlag = req.getParameter("serviceFlag");
		HashMap<String,Object> param = null;
		
		
		try {
			/* 1. 파라미터 수신, 세션처리, JSON초기화 */
			param = super.excute(req);
            
//			ValidationCommand validation = new ValidationCommand();
//			String strValidation = validation.checkValidationKey(req);
//			if(strValidation.compareTo(ValidationCommand.VALIDATION_TRUE) != 0) {
//				validation.regConnectLog(req, String.format("Validation Check Error - validationKey : [%s]", req.getParameter("validationKey")));
//				return req.getParameter("callback") != null ? 
//						jsonForward(action, NWJson.error(serviceFlag, strValidation), req.getParameter("callback")) :
//						jsonForward(action, NWJson.error(serviceFlag, strValidation));
//			}
//			validation.regConnectLog(req, "");
			
			/* 3. 비즈니스 로직 분기 (serviceFlag로 업무별 분기) */
			switch(serviceFlag)
			{
			case CMDVER_DDLIST_V01:
				action = getDDList(req, param);
				break;
			default:
				action = jsonForward(action, NWJson.error(serviceFlag, "잘못된 서비스플래그 입니다."), param.get("callback").toString());
				break;
			}
		} catch (Exception e) {
			if(req.getParameter("callback")!=null)
				action = jsonForward(action, NWJson.error(serviceFlag, e.toString()), param.get("callback").toString());
			else
				action = jsonForward(action, NWJson.error(serviceFlag, e.toString()));
			logger.error(e);
		}
		return action;
	}
	
	/************************************************************
	 ***********************************************************/
	private NWActionForward getDDList(HttpServletRequest req, HashMap<String,Object> param) throws Exception {
		NWActionForward action = null;
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = (String)req.getParameter("callback");
		String userId      = req.getParameter("userId");
		int currPage       = Integer.parseInt(req.getParameter("currPage").toString());
		int rowCnt       = Integer.parseInt(req.getParameter("rowCnt").toString());
		HashMap<String, Integer> map = pageConverting(currPage,rowCnt);
		JSONObject jsonRoot = null;
		param.put("currPage", map.get("currPage"));
		param.put("limit"   , map.get("limit"));
		DbDao dao = new DbDao(userId);
		
		
		ArrayList<HashMap<String, Object>> list1 = dao.queryForList("ASRpt.getDDList2", param);
		if(dao.IsResult().equals(false)) {
			jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
			return jsonForward(action, jsonRoot, callback);
		}
		
		jsonRoot = NWJson.success(serviceFlag, list1, "RES_MDATA");
		
		return jsonForward(action, jsonRoot, callback);
	}
	
	public HashMap<String, Integer> pageConverting(int index,int rowCnt){
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		int limit    = rowCnt * index;
		int currPage = limit  - (rowCnt-1);
		map.put("limit"   , limit);
		map.put("currPage", currPage);
		return map;
	}
}
