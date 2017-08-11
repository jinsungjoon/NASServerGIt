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
public class UserCommand extends NWSuperCommand implements NWICommand {

	private final String CMDUSER_LOGIN_V01 		= "USER_LOGIN_V01";			// 로그인
	private final String CMDUSER_VERSION_V01 	= "USER_VERSION_V01";		// 버전체크
	private HttpSession session;
	private Logger logger = null;	
	
	public UserCommand() {
		logger = Logger.getLogger(UserCommand.class.getName());
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
			case CMDUSER_LOGIN_V01:
				// http://115.92.190.211:12307/nwtns/asm/user.asm?serviceFlag=USER_LOGIN_V01&userId=kdbaek@nwtns.com&osFlag=10&callback=1111&deiceId=353168061804256&version=0.1&passwd=qwert12345&phoneNo=01082082258
				action = doSmartLogin(req);
				break;
			case CMDUSER_VERSION_V01:
				action = getSmartVersion(req, param);
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
	private NWActionForward doSmartLogin(HttpServletRequest req) throws Exception {
		NWActionForward action = null;
		// 기본파라미터
		String userId     = req.getParameter("userId");
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = req.getParameter("callback").toString();
		// 로그인 입력 파라미터
		String passwd      = req.getParameter("passwd");
		// DEVICE ID가 NULL인경우 'A1111'로 체크한다.
		String deviceId    = (req.getParameter("deviceId") == null ? "A1111" : req.getParameter("deviceId").toString());
		String phoneNo     = (req.getParameter("phoneNo" ) == null ? "B1111" : req.getParameter("phoneNo").toString());
		if(deviceId.compareTo("") == 0)  deviceId = "a1111";
		if(phoneNo.compareTo("")  == 0)  phoneNo  = "b1111";
		String userName = "";

		//LinkedHashMap<String, Object> lparam = new LinkedHashMap<String, Object>();
		HashMap<String, Object> lparam = new HashMap<String, Object>();
		lparam.put("userId", userId);
		lparam.put("passwd", passwd);
		lparam.put("phoneNo", phoneNo);
		lparam.put("deviceId", deviceId);
		
		
		logger.debug("UserCommand - connect dao");
		logger.debug(lparam);
		
		// Dao Connection
		DbDao dao = new DbDao(userId);

		// 1) 사용자 정보가 존재하는지 확인한다.
		HashMap<String, Object> resMap = dao.queryForObject("ASUser.getUserInfo", lparam);
//		HashMap<String, Object> resMap = dao.queryForObject("ASUser2.getUserInfo", lparam);
		logger.debug(resMap);
		if(dao.IsResult() == false) {
			logger.error(req);
			logger.error(String.format("%s[%s] %s", Conf.PRJ_NM, userId, dao.getDbOutMsg()));
			return jsonForward(action, NWJson.error(serviceFlag, dao.getDbOutMsg()), callback);
		}
		// SELECT 결과 없음.
		if(resMap == null) {
			logger.error(req);
			logger.error(String.format("%s[%s] 아이디나 패스워드가 올바르지 않습니다. (사용자 없음)", Conf.PRJ_NM, userId));
			return jsonForward(action, NWJson.error(serviceFlag, "아이디나 패스워드가 올바르지 않습니다.(CODE:001)"), callback);
		}
		boolean ruleCheck = true;
//		if((
//				   deviceId.equals("353168061804256") // 백 C
//				)) {
//			ruleCheck = false;
//			
//			// 비밀번호 만기 체크하지 않음.
//			resMap.put("PASSWD_EXP_DAYS", "10");
//		}
		
		// 2) 비밀번호 체크
		//  - 2016.02.29[KDBAEK] null 체크조건 추가
		if(resMap.get("PWD_CHECK").toString().equals("Y") == false) {
			if(ruleCheck) {
				logger.error(req);
				logger.error(String.format("%s[%s] 아이디나 패스워드가 올바르지 않습니다. (비밀번호 불일치)", Conf.PRJ_NM, userId));
				return jsonForward(action, NWJson.error(serviceFlag, "아이디나 패스워드가 올바르지 않습니다.(CODE:002)"), callback);
			}
		}
		// 대소문자때문에 실제 DB에 입력된 값으로 변경 한다.
		userId   = resMap.get("USER_ID").toString();
		userName = resMap.get("USER_NAME").toString();
		
		lparam.put("userId", userId);
		session.setAttribute("userId", userId);
		session.setAttribute("userName", userName);
		session.setAttribute("ASM_USER", NWJson.toHashmap(resMap));
		System.out.println("session.getAttribute:"+session.getAttribute("userId"));
		// 3) 
//		// 5) Validation 체크
//		String loginIp = req.getRemoteAddr();
//		String deptCd  = resMap.get("DEPT_ID").toString();
//		ValidationCommand validation = new ValidationCommand();
//		// Validation Key 생성 / 저장
//		String sEncValkey = validation.regValidationKey(userId, deptCd, deviceId, phoneNo);
//		if(validation.IsResult() == false) {
//			logger.error(req);
//			logger.error(String.format("%s[%s] %s", Conf.PRJ_NM, userId, validation.getOutMsg()));
//			return jsonForward(action, NWJson.error(serviceFlag, validation.getOutMsg()), callback);
//		}
//		
//		// 6) 로그인 로그 기록
//		validation.regLoginLog(userId, loginIp);
		
		// 6) 사용자 테이블에 로그인 시간 업데이트
		if(ruleCheck) {
			//upLoginDate(userId);
		}

		
		// 9) 사용자정보 리턴
		resMap.put("SERVER_DATE"     , NWUtil.getDateType("D"));
		
		logger.info("로그인결과 >>>>>>>>>>>>>>>>");
		logger.info(resMap);
		logger.info("로그인결과 <<<<<<<<<<<<<<<<");
		
		
		
		return jsonForward(action, NWJson.success(serviceFlag, resMap, "RES_LDATA"), callback);
	}

	private NWActionForward getSmartVersion(HttpServletRequest req, HashMap<String, Object> param) throws Exception {
		NWActionForward action = null;
		// 기본파라미터
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = req.getParameter("callback").toString();
		//String osFlag      = req.getParameter("osFlag");

		DbDao dao = new DbDao("version");

		HashMap<String, Object> mapResult = dao.queryForObject("ASUser.getSmartVersion", param);

		logger.debug("getSmartVersion : ");
		logger.debug(mapResult);

		JSONObject jsonRoot = null;
		jsonRoot = NWJson.success(serviceFlag, mapResult, "RES_LDATA");

		// 버전체크는 콜백 리턴하지 않는다.
		//return jsonForward(action, jsonRoot, callback);
		return jsonForward(action, jsonRoot);
	}
}