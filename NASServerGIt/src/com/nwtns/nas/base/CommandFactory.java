package com.nwtns.nas.base;

import java.util.HashMap;
import java.util.Map;

import com.nwtns.framework.mvc.NWICommand;
import com.nwtns.framework.util.NWLog;


public class CommandFactory {
	private Map<String, String> map = new HashMap<String, String>();

	private static CommandFactory factory = new CommandFactory();
	private final String  DEFAULT_PKG = "com.nwtns.nas.command."; 
	private final String  URL_EXT = ".asm"; 
	private CommandFactory() {
		
		/* URL별 Command Factory 등록 - 개발 */
//		map.put("/ASM_WEB/nas/util"    + URL_EXT, DEFAULT_PKG + "UtilCommand");
		map.put("/ASM_WEB/nas/user"    + URL_EXT, DEFAULT_PKG + "UserCommand");
//		map.put("/ASM_WEB/nas/sync"    + URL_EXT, DEFAULT_PKG + "SyncCommand");
//		map.put("/ASM_WEB/nas/maker"   + URL_EXT, DEFAULT_PKG + "MakerCommand");
//		map.put("/ASM_WEB/nas/emp"     + URL_EXT, DEFAULT_PKG + "EmpCommand");
//		map.put("/ASM_WEB/nas/cust"    + URL_EXT, DEFAULT_PKG + "CustCommand");
		map.put("/ASM_WEB/nas/rpt"     + URL_EXT, DEFAULT_PKG + "RptCommand");
		map.put("/ASM_WEB/nas/call"    + URL_EXT, DEFAULT_PKG + "CallCommand");
		map.put("/ASM_WEB/nas/upload"  + URL_EXT, DEFAULT_PKG + "UploadCommand");
		map.put("/ASM_WEB/nas/master"  + URL_EXT, DEFAULT_PKG + "MasterCommand");
	}

	public static CommandFactory getInstance() {
		return factory;
	}

	public NWICommand createCommand(String path) {
		String p = (String) map.get(path);
		System.out.println(path);
		//NWLog.i(this.getClass(),"CommandFactory.createCommand:"+ p);
		NWICommand command = null;
		try {
//			NWLog.i(this.getClass(), "====>"+Class.forName(p));
			Class commandclass = Class.forName(p);
			command = (NWICommand) commandclass.newInstance();
		} catch (Exception e) {
			NWLog.d(this.getClass(), "[error]");
			e.printStackTrace();
		}
		return command;
	}
}
