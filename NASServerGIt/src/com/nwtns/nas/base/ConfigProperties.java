package com.nwtns.nas.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import com.nwtns.framework.util.NWFile;
import com.nwtns.framework.util.NWLog;
import com.nwtns.nas.base.Conf.DirConfig;

/**
 * conf/config.properties 매핑 클래스
 * @author choijungju
 */
public class ConfigProperties extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ConfigProperties config = new ConfigProperties();

	private void setConf(Properties p){

		// DirConfig
		DirConfig.MAX_UPLOAD_SIZE 		= Integer.parseInt(p.getProperty("MAX_UPLOAD_SIZE").toString().trim())* 1024 * 1024;
		DirConfig.PATH_DIR_UPLOAD 		= p.getProperty("PATH_DIR_UPLOAD").toString().trim();
		DirConfig.PATH_DIR_DEFAULT		= DirConfig.PATH_DIR_UPLOAD + DirConfig.FILE_TYPE_DEFAULT 	+File.separator;
		DirConfig.PATH_DIR_TEMP 		= DirConfig.PATH_DIR_UPLOAD + DirConfig.FILE_TYPE_TEMP  	+File.separator;
//		DirConfig.PATH_DIR_SIGN 		= DirConfig.PATH_DIR_UPLOAD + DirConfig.FILE_TYPE_SIGN  	+File.separator;
//		DirConfig.PATH_DIR_PIC 			= DirConfig.PATH_DIR_UPLOAD + DirConfig.FILE_TYPE_PIC 		+File.separator;
//		DirConfig.PATH_DIR_CLAIM		= DirConfig.PATH_DIR_UPLOAD + DirConfig.FILE_TYPE_CLAIM 	+File.separator;
//		DirConfig.PATH_DIR_MARKET		= DirConfig.PATH_DIR_UPLOAD + DirConfig.FILE_TYPE_MARKET 	+File.separator;
//		DirConfig.PATH_DIR_FESTIVAL		= DirConfig.PATH_DIR_UPLOAD + DirConfig.FILE_TYPE_FESTIVAL	+File.separator;
//		DirConfig.PATH_DIR_SUGGEST		= DirConfig.PATH_DIR_UPLOAD + DirConfig.FILE_TYPE_SUGGEST	+File.separator;
		DirConfig.PATH_DIR_APPLOG 		= p.getProperty("PATH_DIR_LOG").toString().trim() + DirConfig.FILE_TYPE_APPLOG  +File.separator;
		DirConfig.PATH_DIR_ORDERLOG 	= p.getProperty("PATH_DIR_LOG").toString().trim() + DirConfig.FILE_TYPE_ORDER_LOG  +File.separator;
		DirConfig.PATH_APP_FILE			= p.getProperty("PATH_APP_FILE").toString().trim();

		NWFile.makeDirectory(DirConfig.PATH_DIR_UPLOAD);
		NWFile.makeDirectory(DirConfig.PATH_DIR_DEFAULT);
		NWFile.makeDirectory(DirConfig.PATH_DIR_TEMP);
		NWFile.makeDirectory(DirConfig.PATH_APP_FILE);
//		NWFile.makeDirectory(DirConfig.PATH_DIR_SIGN);
//		NWFile.makeDirectory(DirConfig.PATH_DIR_PIC);
//		NWFile.makeDirectory(DirConfig.PATH_DIR_CLAIM);
//		NWFile.makeDirectory(DirConfig.PATH_DIR_MARKET);
//		NWFile.makeDirectory(DirConfig.PATH_DIR_FESTIVAL);
//		NWFile.makeDirectory(DirConfig.PATH_DIR_SUGGEST);
		NWFile.makeDirectory(DirConfig.PATH_DIR_APPLOG);
		NWFile.makeDirectory(DirConfig.PATH_DIR_ORDERLOG);

		NWLog.d(this.getClass(), "======================== Configuration Start ============================");
		NWLog.d(this.getClass(), "DirConfig.MAX_UPLOAD_SIZE 	:"+DirConfig.MAX_UPLOAD_SIZE );
		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_UPLOAD 	:"+DirConfig.PATH_DIR_UPLOAD );
		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_DEFAULT 	:"+DirConfig.PATH_DIR_DEFAULT );
		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_TEMP 		:"+DirConfig.PATH_DIR_TEMP );
		NWLog.d(this.getClass(), "DirConfig.PATH_APP_FILE 		:"+DirConfig.PATH_APP_FILE );
//		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_SIGN 		:"+DirConfig.PATH_DIR_SIGN );
//		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_PIC 		:"+DirConfig.PATH_DIR_PIC );
//		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_CLAIM 		:"+DirConfig.PATH_DIR_CLAIM );
//		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_MARKET		:"+DirConfig.PATH_DIR_MARKET );
//		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_FESTIVAL	:"+DirConfig.PATH_DIR_FESTIVAL );
//		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_SUGGEST	:"+DirConfig.PATH_DIR_SUGGEST );
		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_APP_LOG 	:"+DirConfig.PATH_DIR_APPLOG );
		NWLog.d(this.getClass(), "DirConfig.PATH_DIR_ORDERLOG 	:"+DirConfig.PATH_DIR_ORDERLOG);
		NWLog.d(this.getClass(), "======================== Configuration End ============================");
	}

	public ConfigProperties() {
		Properties p = new Properties();
	    try {
	    	FileInputStream fis = new FileInputStream(new File(Conf.CONFIG_PROPERTIES));
	    	p.load(new java.io.BufferedInputStream(fis));
	    	setConf(p);
	      	fis.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

	public static ConfigProperties getInstance(){
		return config;
	}
}