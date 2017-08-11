package com.nwtns.nas.command;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.nwtns.nas.base.Conf;
import com.nwtns.nas.dao.DbDao;
import com.nwtns.framework.exception.ActionKeyException;
import com.nwtns.framework.mvc.NWActionForward;
import com.nwtns.framework.mvc.NWICommand;
import com.nwtns.framework.mvc.NWSuperCommand;
import com.nwtns.framework.util.NWLog;
import com.nwtns.framework.util.NWUtil;



/**
 * 비즈니스 로직 처리 클래스
 * @author cjj
 *
 */
public class CallCommand extends NWSuperCommand implements NWICommand {

	private final String CMDUSER_CALL_PAGE_INIT_V01 		= "CALL_PAGE_INIT_V01";		// 장애접수 화면 초기화
	private final String CMDUSER_CUST_DIV_V01     		    = "CUST_DIV_V01";			// 고객사-사업부
	private final String CMDUSER_CUST_DEPT_V01     		    = "CUST_DEPT_V01";			// 고객사-사업부-영업조직
	private final String CMDUSER_USER_LIST_INIT_V01         = "USER_LIST_INIT_V01";     // 사용자 관리 페이지 초기화
	
	
	private HttpSession session;
	private Logger logger = null;	
	
	public CallCommand() {
		logger = Logger.getLogger(CallCommand.class.getName());
	}
	
	@Override
	public NWActionForward execute(HttpServletRequest req) throws ServletException, IOException, ActionKeyException {
		System.out.println("sessionId:"+req.getSession().getId());
		session = req.getSession(true);
		NWActionForward action = null;
		String serviceFlag = req.getParameter("serviceFlag");
		HashMap<String,Object> param = null;
		
		try {
			/* 1. 파라미터 수신, 세션처리, JSON초기화 */
			param = super.excute(req);
			
			//Client 가 어떤 OS를 쓰는지, 어떤 브라우져를 쓰는지?
//			if(!NWUtil.Client_Check(req))
//			{
//				action = jsonForward(action, NWJson.error(serviceFlag, "잘못된 요청 입니다."), req.getParameter("callback").toString());
//				return action;
//			}
			
			/* 3. 비즈니스 로직 분기 (serviceFlag로 업무별 분기) */
			switch(serviceFlag)
			{
			case CMDUSER_CALL_PAGE_INIT_V01:
				// http://115.92.190.211:12307/nwtns/asm/user.asm?serviceFlag=USER_LOGIN_V01&userId=kdbaek@nwtns.com&osFlag=10&callback=1111&deiceId=353168061804256&version=0.1&passwd=qwert12345&phoneNo=01082082258
				action = callPageInit(req);
				break;
			case CMDUSER_CUST_DIV_V01:
				action = getCustDivSearch(req);
				break;
			case CMDUSER_CUST_DEPT_V01:
				action = getCustDeptSearch(req);
				break;
			case CMDUSER_USER_LIST_INIT_V01:
				action = getUserListInit(req);
				break;
			default:
				action = jsonForward(action, NWJson.error(serviceFlag, "잘못된 서비스플래그 입니다."), param.get("callback").toString());
				break;
				
			}
		} catch (Exception e) {
			action = jsonForward(action, NWJson.error(serviceFlag, e.toString()), req.getParameter("callback").toString());
			logger.error(this.getClass(), e);
		}
		return action;
	}
	
	@SuppressWarnings("unchecked")
	private NWActionForward callPageInit(HttpServletRequest req) throws Exception {
		NWActionForward action = null;
		// 기본파라미터
		String userId     = req.getParameter("userId");
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = req.getParameter("callback").toString();
		String CODE_TYPE1    = req.getParameter("REPAIR_IN_TAG");
		String CODE_TYPE2    = req.getParameter("PARCEL_SERVICE");
		

		HashMap<String, Object> lparam = new HashMap<String, Object>();
		lparam.put("userId", userId);
		
		logger.debug(lparam);
		
		// Dao Connection
		DbDao dao = new DbDao(userId);

		//접수유형
		lparam.put("CODE_TYPE", CODE_TYPE1);
		ArrayList<HashMap<String, Object>> REPAIR_IN_TAG = dao.queryForList("ASCall.getCommonSearch", lparam);
		
		//택배사
		lparam.put("CODE_TYPE", CODE_TYPE2);
		ArrayList<HashMap<String, Object>> PARCEL_SERVICE = dao.queryForList("ASCall.getCommonSearch", lparam);
		
		//고객사정보
		ArrayList<HashMap<String, Object>> CUST_MASTER = dao.queryForList("ASCall.getCustSearch", lparam);
		
		if(dao.IsResult() == false) {
			logger.error(req);
			logger.error(String.format("%s[%s] %s", Conf.PRJ_NM, userId, dao.getDbOutMsg()));
			return jsonForward(action, NWJson.error(serviceFlag, dao.getDbOutMsg()), callback);
		}
		// SELECT 결과 없음.
//		if(REPAIR_IN_TAG == null) {
//			logger.error(req);
//			logger.error(String.format("%s[%s] 등록정보가 없습니다.", Conf.PRJ_NM, userId));
//			return jsonForward(action, NWJson.error(serviceFlag, "등록정보가 없습니다."), callback);
//		}
		
		return jsonForward(action, NWJson.success(serviceFlag, new Object[] {REPAIR_IN_TAG,PARCEL_SERVICE,CUST_MASTER}, new String[] {"REPAIR_IN_TAG","PARCEL_SERVICE","CUST_MASTER"}), callback);
	}
	//고객사-사업부
		@SuppressWarnings("unchecked")
		private NWActionForward getCustDivSearch(HttpServletRequest req) throws Exception {
			NWActionForward action = null;
			// 기본파라미터
			String userId     = req.getParameter("userId");
			String serviceFlag = req.getParameter("serviceFlag");
			String callback    = req.getParameter("callback").toString();
			String CUST_CODE   = req.getParameter("CUST_CODE");
			HashMap<String, Object> lparam = new HashMap<String, Object>();
			lparam.put("CUST_CODE", CUST_CODE);
			
			logger.debug(lparam);
			
			// Dao Connection
			DbDao dao = new DbDao(userId);

			//
			ArrayList<HashMap<String, Object>> RES_DATA = dao.queryForList("ASCall.getCustDivSearch", lparam);
			ArrayList<HashMap<String, Object>> RES_DATA2 = dao.queryForList("ASCall.getCustProjSearch", lparam);
			
			if(dao.IsResult() == false) {
				logger.error(req);
				logger.error(String.format("%s[%s] %s", Conf.PRJ_NM, userId, dao.getDbOutMsg()));
				return jsonForward(action, NWJson.error(serviceFlag, dao.getDbOutMsg()), callback);
			}
			// SELECT 결과 없음.
//			if(RES_DATA == null) {
//				logger.error(req);
//				logger.error(String.format("%s[%s] 등록정보가 없습니다.", Conf.PRJ_NM, userId));
//				return jsonForward(action, NWJson.error(serviceFlag, "등록정보가 없습니다."), callback);
//			}
			
			return jsonForward(action, NWJson.success(serviceFlag, new Object[] {RES_DATA,RES_DATA2}, new String[] {"RES_DATA","RES_DATA2"}), callback);
		}
	//고객사-사업부-영업조직
	@SuppressWarnings("unchecked")
	private NWActionForward getCustDeptSearch(HttpServletRequest req) throws Exception {
		NWActionForward action = null;
		// 기본파라미터
		String userId     = req.getParameter("userId");
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = req.getParameter("callback").toString();
		String CUST_CODE   = req.getParameter("CUST_CODE");
		String CUST_DIV   = req.getParameter("CUST_DIV");
		HashMap<String, Object> lparam = new HashMap<String, Object>();
		lparam.put("CUST_CODE", CUST_CODE);
		lparam.put("CUST_DIV", CUST_DIV);
		
		logger.debug(lparam);
		
		// Dao Connection
		DbDao dao = new DbDao(userId);

		//
		ArrayList<HashMap<String, Object>> RES_DATA = dao.queryForList("ASCall.getCustDeptSearch", lparam);
		
		if(dao.IsResult() == false) {
			logger.error(req);
			logger.error(String.format("%s[%s] %s", Conf.PRJ_NM, userId, dao.getDbOutMsg()));
			return jsonForward(action, NWJson.error(serviceFlag, dao.getDbOutMsg()), callback);
		}
		// SELECT 결과 없음.
		if(RES_DATA == null) {
			logger.error(req);
			logger.error(String.format("%s[%s] 등록정보가 없습니다.", Conf.PRJ_NM, userId));
			return jsonForward(action, NWJson.error(serviceFlag, "등록정보가 없습니다."), callback);
		}
		
		return jsonForward(action, NWJson.success(serviceFlag, new Object[] {RES_DATA}, new String[] {"RES_DATA"}), callback);
	}
	
	//사용자 관리 페이지 초기화
	@SuppressWarnings("unchecked")
	private NWActionForward getUserListInit(HttpServletRequest req) throws Exception {
		NWActionForward action = null;
		
		// 기본파라미터
		String userId = req.getParameter("userId");
		String serviceFlag = req.getParameter("serviceFlag");
		String callback = req.getParameter("callback").toString();
		String CODE_TYPE = req.getParameter("CODE_TYPE");
		String CODE_VALUE = req.getParameter("CODE_VALUE");
		
		HashMap<String, Object> lparam = new HashMap<String, Object>();
		lparam.put("userId", "");
		lparam.put("CODE_TYPE", CODE_TYPE);
		lparam.put("CODE_VALUE", CODE_VALUE);
		
		logger.debug(lparam);
		
		// Dao Connection
		DbDao dao = new DbDao(userId);

		ArrayList<HashMap<String, Object>> RES_DATA = dao.queryForList("ASCall.getCommonSearch", lparam);
		
		if(dao.IsResult() == false) {
			logger.error(req);
			logger.error(String.format("%s[%s] %s", Conf.PRJ_NM, userId, dao.getDbOutMsg()));
			return jsonForward(action, NWJson.error(serviceFlag, dao.getDbOutMsg()), callback);
		}
		// SELECT 결과 없음.
		if(RES_DATA == null) {
			logger.error(req);
			logger.error(String.format("%s[%s] 부서 정보가 없습니다.", Conf.PRJ_NM, userId));
			return jsonForward(action, NWJson.error(serviceFlag, "부서 정보가 없습니다."), callback);
		}
		
		return jsonForward(action, NWJson.success(serviceFlag, new Object[] {RES_DATA}, new String[] {"RES_DATA"}), callback);
	}
}