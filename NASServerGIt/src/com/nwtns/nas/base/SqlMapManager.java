package com.nwtns.nas.base;

import java.io.Reader;
import java.nio.charset.Charset;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


public class SqlMapManager {

	public static final int MAPCONNECT_BASE = 1;	// Main System
	public static final int MAPCONNECT_TEST = 2;
		
	private static final SqlMapClient sqlMap_Base;
//	private static final SqlMapClient sqlMap_TEST;
	
	
	static {
		try {
			
			String resource = "SqlMapConfig_Nwtns.xml";
//			String resource = "SqlMapConfig_Nwtns2.xml";
			Charset charset = Charset.forName("UTF-8");
	        Resources.setCharset(charset);
			Reader reader = Resources.getResourceAsReader(resource);
			
			sqlMap_Base = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error initializing SqlMapManager class. Cause:\n" + e);
		}
		
//		try {
//			
//			String resource = "SqlMapConfigCCB_oracleSis.xml";
//			Charset charset = Charset.forName("UTF-8");
//	        Resources.setCharset(charset);
//			Reader reader = Resources.getResourceAsReader(resource);
//			
//			sqlMap_SIS = SqlMapClientBuilder.buildSqlMapClient(reader);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("Error initializing SqlMapManager class. Cause:\n" + e);
//		}
//
//		try {
//			
//			String resource = "SqlMapConfigCCB_db2400.xml";
//			Charset charset = Charset.forName("UTF-8");
//	        Resources.setCharset(charset);
//			Reader reader = Resources.getResourceAsReader(resource);
//			
//			sqlMap_DB2400 = SqlMapClientBuilder.buildSqlMapClient(reader);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("Error initializing SqlMapManager class. Cause:\n" + e);
//		}
	}
	
	public static SqlMapClient getSqlMapInstanceBase() {
		return sqlMap_Base;
	}
	public static SqlMapClient getSqlMapInstanceTest() {
		return null;
	}
	
//	public static SqlMapClient getSqlMapInstanceSis() {
//		return sqlMap_SIS;
//	}
//	
	
//	private static String status = "Y";
//	public static synchronized void setStatus(String stat) {
//		status = stat;
//	}
}