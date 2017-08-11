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
import com.nwtns.framework.util.Encoding;
import com.nwtns.framework.util.NWLog;
import com.nwtns.framework.util.NWUtil;



/**
 * 비즈니스 로직 처리 클래스
 * @author cjj
 *
 */
public class MasterCommand extends NWSuperCommand implements NWICommand {

	private final String MASTER_GETUSERLIST_V01 = "MASTER_GETUSERLIST_V01";		// 사용자목록 조회
	private final String MASTER_GETVERSION_V01 	= "MASTER_GETVERSION_V01";		// 버전정보 조회
	private HttpSession session;
	private Logger logger = null;	
	
	public MasterCommand() {
		logger = Logger.getLogger(MasterCommand.class.getName());
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
			case MASTER_GETVERSION_V01:
				action = getVersionInfo(req, param);
				break;
			case MASTER_GETUSERLIST_V01:
				action = getUserList(req, param);
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

	private NWActionForward getVersionInfo(HttpServletRequest req, HashMap<String, Object> param) throws Exception {
		NWActionForward action = null;
		// 기본파라미터
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = req.getParameter("callback").toString();

		DbDao dao = new DbDao("version");

		ArrayList<HashMap<String, Object>> mapResult = dao.queryForList("ASMaster.getVersionInfo", param);

		logger.debug("getVersionInfo : ");
		logger.debug(mapResult);

		JSONObject jsonRoot = null;
		jsonRoot = NWJson.success(serviceFlag, mapResult, "DATA");

		// 버전체크는 콜백 리턴하지 않는다.
		return jsonForward(action, jsonRoot, callback);
		//return jsonForward(action, jsonRoot);
	}
	
	private NWActionForward getUserList(HttpServletRequest req, HashMap<String, Object> param) throws Exception {
		NWActionForward action = null;
		// 기본파라미터
		String userId      = req.getParameter("userId");
		String serviceFlag = req.getParameter("serviceFlag");
		String callback    = req.getParameter("callback").toString();
		String roleCode    = req.getParameter("roleCode").toString();
		String useYn       = req.getParameter("useYn").toString();
		String searchValue = req.getParameter("searchValue").toString();

		param.put("roleCode", roleCode);
		param.put("useYn", useYn);
		try {
			searchValue = Encoding.decodeURL(searchValue,"UTF-8");
			param.put("searchValue", searchValue);
		} catch (Exception e) {
			throw new Exception("자료변환 실패");
		}
		
		DbDao dao = new DbDao(userId);

		ArrayList<HashMap<String, Object>> mapResult = dao.queryForList("ASMaster.getUserList", param);
		
		JSONObject jsonRoot = null;
		if(dao.IsResult().equals(false)) {
			jsonRoot = NWJson.error(serviceFlag, dao.getDbOutMsg());
			return jsonForward(action, jsonRoot, callback);
		}
		
		jsonRoot = NWJson.success(serviceFlag, mapResult, "DATA");
		
		return jsonForward(action, jsonRoot, callback);
	}
}