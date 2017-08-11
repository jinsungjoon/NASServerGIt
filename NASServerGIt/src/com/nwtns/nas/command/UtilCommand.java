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
public class UtilCommand extends NWSuperCommand implements NWICommand {

	private final String CMDVER_UTIL_SELT_MAKER_V01   = "UTIL_SELT_MAKER_V01";	    // SELECT-BOX - 제조사정보 조회
	private final String CMDVER_UTIL_SELT_SAWON_V01   = "UTIL_SELT_SAWON_V01";	    // SELECT-BOX - 부서/사원정보 조회
	private final String CMDVER_UTIL_SELT_CUST_V01    = "UTIL_SELT_CUST_V01";	    // SELECT-BOX - 고객사정보 조회
	private final String CMDVER_UTIL_PROD_INFO_V01    = "UTIL_PROD_INFO_V01";	    // 제품정보 조회
	private final String CMDVER_UTIL_PART_INFO_V01    = "UTIL_PART_INFO_V01";	    // 부품정보 조회
	
	
	private Logger logger = null;
	
	public UtilCommand() {
		logger = Logger.getLogger(UtilCommand.class.getName());
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
			case CMDVER_UTIL_SELT_MAKER_V01:
				// http://115.92.190.211:12001/ASM_WEB/mobile/asm/util.asm?serviceFlag=UTIL_SELT_MAKER_V01&userId=kdbaek@nwtns.com&osFlag=10&callback=1111&deiceId=353168061804256
				action = getSeltMaker(req, param);
				break;
			case CMDVER_UTIL_SELT_CUST_V01:
				// http://115.92.190.211:12001/ASM_WEB/mobile/asm/util.asm?serviceFlag=UTIL_SELT_CUST_V01&userId=kdbaek@nwtns.com&osFlag=10&callback=1111
				action = getSeltCust(req, param);
				break;
			case CMDVER_UTIL_SELT_SAWON_V01:
				// 
				action = getSeltSawon(req, param);
				break;
			case CMDVER_UTIL_PROD_INFO_V01:
				action = getProdInfo(req, param);
				break;
			case CMDVER_UTIL_PART_INFO_V01:
				action = getPartInfo(req, param);
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
	private NWActionForward getSeltMaker(HttpServletRequest req, HashMap<String,Object> param) throws Exception {
		NWActionForward action = null;
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = (String)req.getParameter("callback");
		String userId      = req.getParameter("userId");
		String makerCode   = req.getParameter("makerCode");
		String groupCode   = req.getParameter("groupCode");
		
		JSONObject jsonRoot = null;
		
		DbDao dao = new DbDao(userId);
		
		// 1) 제조사, 제품군
		if(makerCode == null || makerCode.equals("")) {
			ArrayList<HashMap<String, Object>> list1 = dao.queryForList("ASUtil.getSeltProdMakerList", param);
			if(dao.IsResult().equals(false)) {
				jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
				return jsonForward(action, jsonRoot, callback);
			}
			ArrayList<HashMap<String, Object>> list2 = dao.queryForList("ASUtil.getSeltProdGroupList", param);
			if(dao.IsResult().equals(false)) {
				jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
				return jsonForward(action, jsonRoot, callback);
			}
			
			jsonRoot = NWJson.success(serviceFlag, new Object[] {list1, list2},new String[] {"RES_MDATA", "RES_MGROUP"});
		// 2) 제품모델
		} else {
			ArrayList<HashMap<String, Object>> list1 = dao.queryForList("ASUtil.getSeltProdModelList", param);
			if(dao.IsResult().equals(true)) {
				jsonRoot = NWJson.success(serviceFlag, list1, "RES_MDATA");
			} else {
				logger.error(dao.getDbOutMsg());
				jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
			}
		}
		
		return jsonForward(action, jsonRoot, callback);
	}
	
	/************************************************************
	 ***********************************************************/
	private NWActionForward getSeltSawon(HttpServletRequest req, HashMap<String,Object> param) throws Exception {
		NWActionForward action = null;
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = (String)req.getParameter("callback");
		String userId      = req.getParameter("userId");
		String searchTag   = req.getParameter("searchTag");
		
		
		
		DbDao dao = new DbDao(userId);
		
		ArrayList<HashMap<String, Object>> list1;
		if(searchTag == null || searchTag.equals("")) {
			list1 = dao.queryForList("ASUtil.getSeltDeptList", param);
		} else {
			param.put("roleCode", searchTag);
			list1 = dao.queryForList("ASUtil.getSeltSawonList", param);
		}
		
		JSONObject jsonRoot = null;
		if(dao.IsResult().equals(true)) {
			jsonRoot = NWJson.success(serviceFlag, list1, "RES_MDATA");
		} else {
			logger.error(dao.getDbOutMsg());
			jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
		}
		
		return jsonForward(action, jsonRoot, callback);
	}
	
	/************************************************************
	 ***********************************************************/
	private NWActionForward getSeltCust(HttpServletRequest req, HashMap<String,Object> param) throws Exception {
		NWActionForward action = null;
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = (String)req.getParameter("callback");
		String userId      = req.getParameter("userId");
		String custCode    = req.getParameter("custCode");
		String custDiv     = req.getParameter("custDiv");
		//String custDept    = req.getParameter("custDept");
		
		JSONObject jsonRoot = null;
		
		DbDao dao = new DbDao(userId);
		
		// 1) 고객사 리스트 조회
		if(custCode == null || custCode.equals("")) {
			// 고객사 리스트
			ArrayList<HashMap<String, Object>> list1 = dao.queryForList("ASUtil.getSeltCustList", param);
			if(dao.IsResult().equals(false)) {
				jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
				return jsonForward(action, jsonRoot, callback);
			}
			
			jsonRoot = NWJson.success(serviceFlag, list1, "RES_MDATA");
		// 2) (선택한)고객사 사업부, 프로젝트 정보
		} else if( (custCode != null && custCode.equals("")==false) && (custDiv == null || custDiv.equals(""))) {
			// 고객사 사업부 리스트
			ArrayList<HashMap<String, Object>> list1 = dao.queryForList("ASUtil.getSeltCustDivList", param);
			if(dao.IsResult().equals(false)) {
				jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
				return jsonForward(action, jsonRoot, callback);
			}
			// 고객사 프로젝트
			ArrayList<HashMap<String, Object>> list2 = dao.queryForList("ASUtil.getSeltCustProjectList", param);
			if(dao.IsResult().equals(true)) {
				jsonRoot = NWJson.success(serviceFlag, list2, "RES_MDATA");
			} else {
				logger.error(dao.getDbOutMsg());
				jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
			}
			
			jsonRoot = NWJson.success(serviceFlag, new Object[] {list1, list2},new String[] {"RES_MDATA", "RES_PRJ"});
		// 3) (선택한)고객사 - 사업부 - 영업소 리스트 조회
		} else if( (custCode != null && custCode.equals("")==false) && (custDiv != null & custDiv.equals("")==false)) {
			// 영업소 리스트 조회
			ArrayList<HashMap<String, Object>> list1 = dao.queryForList("ASUtil.getSeltCustDeptList", param);
			if(dao.IsResult().equals(false)) {
				jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
				return jsonForward(action, jsonRoot, callback);
			}
			
			jsonRoot = NWJson.success(serviceFlag, list1, "RES_MDATA");
		} else {
			jsonRoot = NWJson.error(serviceFlag, "고객사 조회조건이 올바르지 않습니다.");
		}
		
		return jsonForward(action, jsonRoot, callback);
	}
	
	/************************************************************
	 ***********************************************************/
	private NWActionForward getProdInfo(HttpServletRequest req, HashMap<String,Object> param) throws Exception {
		NWActionForward action = null;
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = (String)req.getParameter("callback");
		String userId      = req.getParameter("userId");
		
		JSONObject jsonRoot = null;
		
		DbDao dao = new DbDao(userId);
		
		ArrayList<HashMap<String, Object>> list = dao.queryForList("ASUtil.getProdInfo", param);
		if(dao.IsResult().equals(false)) {
			logger.error(dao.getDbOutMsg());
			jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
			return jsonForward(action, jsonRoot, callback);
		}
		
		jsonRoot = NWJson.success(serviceFlag, list, "RES_MDATA");
		
		return jsonForward(action, jsonRoot, callback);
	}
	
	/************************************************************
	 * [부품정보 조회]
	 ***********************************************************/
	private NWActionForward getPartInfo(HttpServletRequest req, HashMap<String,Object> param) throws Exception {
		NWActionForward action = null;
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = (String)req.getParameter("callback");
		String userId      = req.getParameter("userId");
		String searchTag   = req.getParameter("searchTag");
		
		JSONObject jsonRoot = null;
		
		DbDao dao = new DbDao(userId);
		
		ArrayList<HashMap<String, Object>> list = null;
		// searchTag   P:부품정보, C:거래처부품정보
		if(searchTag.equals("P")) {
			list = dao.queryForList("ASUtil.getPartInfo", param);
		} else if(searchTag.equals("C")) {
			list = dao.queryForList("ASUtil.getCustPart", param);
		} else {
			return jsonForward(action, NWJson.error(serviceFlag, "조회정보가 올바르지 않습니다."), callback);
		}
		
		if(dao.IsResult().equals(false)) {
			logger.error(dao.getDbOutMsg());
			jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
			return jsonForward(action, jsonRoot, callback);
		}
		
		jsonRoot = NWJson.success(serviceFlag, list, "RES_MDATA");
		
		return jsonForward(action, jsonRoot, callback);
	}
			
}
