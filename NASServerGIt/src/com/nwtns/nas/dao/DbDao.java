package com.nwtns.nas.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.nwtns.nas.base.SqlMapManager;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;


public class DbDao {

	private Logger logger;
	private SqlMapClient sqlMap = null;

	private final String RTN_MSSG = "err_msg";
	private final String RTN_CODE = "err_code";

	private String userId = "";
    private String rtnCode = "T"; // T:성공, F:실패 (지역변수)
	private String rtnMsg = "";
	private Boolean isResult = true;

	@SuppressWarnings("rawtypes")
	public DbDao(String id)
	{
		userId = id;

		logger = Logger.getLogger(this.getClass());

		/* 2. DB 커넥션 생성 */
		sqlMap = SqlMapManager.getSqlMapInstanceBase();
	}

//	@SuppressWarnings("rawtypes")
//	public DbDao(int connMap, String id)
//	{
//		userId = id;
//
//		logger = Logger.getLogger(this.getClass());
//
//		/* 2. DB 커넥션 생성 */
//		if(connMap == SqlMapManager.MAPCONNECT_BASE)
//			sqlMap = SqlMapManager.getSqlMapInstanceBase();
//		else
//		if(connMap == SqlMapManager.MAPCONNECT_TEST)
//			sqlMap = SqlMapManager.getSqlMapInstanceTest();
////		else
////		if(connMap == SqlMapManager.MAPCONNECT_DB2400)
////			sqlMap = SqlMapManager.getSqlMapInstanceDb2400();
//		else
//			sqlMap = SqlMapManager.getSqlMapInstanceBase();
//	}

	public Boolean IsResult() {
		return isResult;
	}

	/********************************************************************
	 * DB Error 검색 : Query 또는 Stored Procedure(오라클은 Package 형태로 커서 리턴) 호출 후 AV_ERROR_MSG 확인
	 *********************************************************************/
	public String  getDbOutCode(){
		return rtnCode;
	}
	public String  getDbOutMsg(){
		return rtnMsg;
	}

	public void beginTrans() {
		try {
			sqlMap.startTransaction();
		} catch (SQLException e) {
			isResult = false;
			rtnMsg = "[Transaction] beginTrans Transaction Exception";
			logger.error(rtnMsg);
			logger.error(e);
			e.printStackTrace();

			return;
		}
	}

	public void rollbackTrans() {
		try {
			sqlMap.getCurrentConnection().rollback();
		} catch (SQLException e) {
			isResult = false;
			rtnMsg = "[Transaction] rollbackTrans Transaction Exception";
			logger.error(rtnMsg);
			logger.error(e);
			e.printStackTrace();

			return;
		}
	}

	public void endTrans() {
		try {
			sqlMap.endTransaction();
		} catch (SQLException e) {
			isResult = false;
			rtnMsg = "[Transaction] endTrans Transaction Exception";
			logger.error(rtnMsg);
			logger.error(e);
			e.printStackTrace();

			return;
		}
	}

	public void commitTrans() {
		try {
			sqlMap.getCurrentConnection().commit();
		} catch (SQLException e) {
			isResult = false;
			rtnMsg = "[Transaction] Commit Transaction Exception";
			logger.error(rtnMsg);
			logger.error(e);
			e.printStackTrace();

			return;
		}
	}

	/**
	 * [GetString]
	 *  - isResult로 처리결과를 체크해야 한다.
	 * @param rfcName
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryForString(String rfcName, HashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		String result="";
		try{
			result = (String) sqlMap.queryForObject(rfcName, param);
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForString Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String queryForString(String rfcName, LinkedHashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		String result="";
		try{
			result = (String) sqlMap.queryForObject(rfcName, param);
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForString Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String queryForString(String rfcName, String param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			String result = (String) sqlMap.queryForObject(rfcName, param);

			return result;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForString Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return "";
	}

	@SuppressWarnings("unchecked")
	public Integer queryForInteger(String rfcName, HashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			int result = (Integer) sqlMap.queryForObject(rfcName, param);

			return result;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForInteger Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return -1;
	}

	@SuppressWarnings("unchecked")
	public Integer queryForInteger(String rfcName, String param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			int result = (Integer) sqlMap.queryForObject(rfcName, param);

			return result;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForInteger Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return -1;
	}

	@SuppressWarnings("unchecked")
	public long queryForLong(String rfcName, String param){
		// 프로시저나 쿼리의 명 또는 iBatis의 경우 xml query의 id
		String spName = rfcName;
		logger.info("spName : " + spName);

		try{
			long result = (long) sqlMap.queryForObject(rfcName, param);

			return result;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForLong Exception ("+userId+")";
			e.printStackTrace();
			logger.error(rtnMsg);
			logger.error(e);
		}

		return -1;
	}

	@SuppressWarnings("unchecked")
	public boolean queryForInsert(String rfcName, LinkedHashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			sqlMap.insert(rfcName, param);
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForInsert Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return isResult;
	}

	@SuppressWarnings("unchecked")
	public Object queryForInsert2(String rfcName, LinkedHashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		Object object = null;

		try{
			object = sqlMap.insert(rfcName, param);
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForInsert Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return object;
	}

	@SuppressWarnings("unchecked")
	public boolean queryForInsert(String rfcName, HashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			sqlMap.insert(rfcName, param);
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForInsert Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return isResult;
	}
	@SuppressWarnings("unchecked")
	public boolean queryForUpdate(String rfcName, HashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			sqlMap.update(rfcName, param);
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForUpdate Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return isResult;
	}

	@SuppressWarnings("unchecked")
	public boolean queryForDelete(String rfcName, String param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			sqlMap.delete(rfcName, param);
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForDelete Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return isResult;
	}

	@SuppressWarnings("unchecked")
	public boolean queryForDelete(String rfcName, LinkedHashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			sqlMap.delete(rfcName, param);
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForDelete Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return isResult;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> queryForObject(String rfcName, HashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			HashMap<String, Object> resultMap = (HashMap<String, Object>) sqlMap.queryForObject(rfcName, param);

			return resultMap;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForObject Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> queryForObject(String rfcName){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			HashMap<String, Object> resultMap = (HashMap<String, Object>) sqlMap.queryForObject(rfcName);

			return resultMap;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForObject Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> queryForObject(String rfcName, String param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			HashMap<String, Object> resultMap = (HashMap<String, Object>) sqlMap.queryForObject(rfcName, param);

			return resultMap;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForObject Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> queryForList(String rfcName, HashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			ArrayList<HashMap<String, Object>> resulList = (ArrayList<HashMap<String, Object>>) sqlMap.queryForList(rfcName, param);

			return resulList;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForList Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return null;
	}
	@SuppressWarnings("unchecked")
	public LinkedList<LinkedHashMap<String, Object>> queryForLinkedList(String rfcName, LinkedHashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			LinkedList<LinkedHashMap<String, Object>> resulList = (LinkedList<LinkedHashMap<String, Object>>) sqlMap.queryForList(rfcName, param);

			return resulList;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForList Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> queryForList(String rfcName, String param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			ArrayList<HashMap<String, Object>> resulList = (ArrayList<HashMap<String, Object>>) sqlMap.queryForList(rfcName, param);

			return resulList;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForList Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return null;
	}

	/**
	 * [프로시져 - 프로시져로부터 결과를 Object로 리턴받는다.]
	 * @param rfcName
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> queryProcObject(String rfcName, HashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			HashMap<String, Object> resultMap = (HashMap<String, Object>) sqlMap.queryForObject(rfcName, param);
			// default output message
			if(param.get(RTN_CODE) != null && (int)param.get(RTN_CODE) != 0) {
				isResult = false;
				rtnCode = String.valueOf((int)param.get(RTN_CODE));
			}
			if(param.get(RTN_MSSG) != null) {
				rtnMsg += rtnCode + ":" + (String) param.get(RTN_MSSG);
			}

			return resultMap;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryProcObject Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return null;
	}


	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> queryProcList(String rfcName, HashMap<String,Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			ArrayList<HashMap<String, Object>> resulList = (ArrayList<HashMap<String, Object>>) sqlMap.queryForList(rfcName, param);

			// default output message
			if(param.get(RTN_CODE) != null && (int)param.get(RTN_CODE) != 0) {
				isResult = false;
				rtnCode = String.valueOf((int)param.get(RTN_CODE));
			}
			if(param.get(RTN_MSSG) != null) {
				rtnMsg += rtnCode + ":" + (String) param.get(RTN_MSSG);
			}

			return resulList;
		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryProcList Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return null;
	}

	public boolean queryForExecute(String rfcName, LinkedHashMap<String, Object> param){
		isResult = true;
		rtnCode = "T";
		rtnMsg  = "";

		try{
			// 호출시 리턴 메시지를 패러미터로 받게 되어 있는 경우라면
			// 리턴 메시지를 받은 후 그 값이 공백이면 성공, 실패이면 해당 실패사유를 리턴 메시지로 받는다.
			sqlMap.queryForObject(rfcName, param);

			// default output message
			if(param.get(RTN_CODE) != null && (int)param.get(RTN_CODE) != 0) {
				isResult = false;
				rtnCode = String.valueOf((int)param.get(RTN_CODE));
			}
			if(param.get(RTN_MSSG) != null) {
				rtnMsg += rtnCode + ":" + param.get(RTN_MSSG);
			}

		}catch(Exception e){
			isResult = false;
			rtnMsg = "queryForExecute Exception ("+userId+")";
			e.printStackTrace();
			logger.error(String.format("[%s] spName:[%s], MSG:[%s]", userId, rfcName, rtnMsg));
			logger.error(e);
		}

		return isResult;
	}



}
