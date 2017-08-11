package com.nwtns.nas.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.nwtns.framework.util.NWUtil;
import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

public class SapDao implements java.lang.Cloneable{
	
	private final String name = "LGCARE";
	private final String host;
	private final String client;
	private final String systemNumber;
	private final String user;
	private final String password;
	private final String language;
	
	private JCoDestination destination;
	private JCoRepository repos;
	
	
	public SapDao( String host, String client, String systemNumber, String user, String password,String lang){

		this.host = host;
		this.client = client;
		this.systemNumber = systemNumber;
		this.user = user;
		this.password = password;
		this.language = lang;
	}
	
	public SapDao(String propertyFileName){
		this.host = NWUtil.getProperty(propertyFileName, "SAP_HOST");
		this.client = NWUtil.getProperty(propertyFileName, "SAP_CLIENT");
		this.systemNumber = NWUtil.getProperty(propertyFileName, "SAP_SYSNR");
		this.user  = NWUtil.getProperty(propertyFileName, "SAP_USER");
		this.password = NWUtil.getProperty(propertyFileName, "SAP_PWD");
		this.language = NWUtil.getProperty(propertyFileName, "SAP_LANG");
		
	}
	public String getName(){
		return this.name;
	}
	
	public String getHost(){
		return this.host;
	}
	
	public String getClient(){
		return this.client;
	}
	
	public String getSystemNumber(){
		return this.systemNumber;
	}
	
	public String getLanguage(){
		return this.language;
	}
	
	public boolean open(){
		
		MyDestinationDataProvider provider = new MyDestinationDataProvider();

//		File destCfg = new File("c:\\temp\\"+provider.SAP_SERVER+".jcoDestination");
//		if(!destCfg.exists()){
//			try{
//				FileOutputStream fos = new FileOutputStream(destCfg,false);
//				provider.getDestinationProperties("").store(fos, "");
//				fos.close();
//			}
//			catch(Exception e){
//				throw new RuntimeException("Unable to create he destination files",e);
//				
//			}
//		}else{
//			throw new RuntimeException(destCfg.getAbsolutePath());
//		}
		try{
			
			if(!com.sap.conn.jco.ext.Environment.isDestinationDataProviderRegistered()){
				com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(provider);
				//System.out.println("destination Registered");
			}else{
				
				//System.out.println("destination Already Registered");
			}
			destination = JCoDestinationManager.getDestination(provider.SAP_SERVER);
			repos = destination.getRepository();
			return true;
		}catch(JCoException e){
			throw new RuntimeException(e);
		}
	
	}
	
	public JCoFunction getFunction(String functionName){
		JCoFunction function = null;
		try{
			function = repos.getFunction(functionName);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(function == null){
			throw new RuntimeException("Not possible to receive function . ");
		}
		return function;
	}
	
	
	public void excute(JCoFunction function){
		try{
		
		JCoContext.begin(destination);
		function.execute(destination);
		}catch(JCoException e){
			e.printStackTrace();
		}finally{
			try{
				JCoContext.end(destination);
			}catch(JCoException e){
				e.printStackTrace();
			}
		}
		
	}
	
	
	private class MyDestinationDataProvider implements DestinationDataProvider {

		static final String SAP_SERVER = "SAP_SERVER";
		private final Properties ABAP_AS_PROPERTIES;
		MyDestinationDataProvider(){
			Properties properties = new Properties();
			properties.setProperty(DestinationDataProvider.JCO_ASHOST, host);
			properties.setProperty(DestinationDataProvider.JCO_SYSNR, systemNumber);
			properties.setProperty(DestinationDataProvider.JCO_CLIENT, client);
			properties.setProperty(DestinationDataProvider.JCO_USER, user);
			properties.setProperty(DestinationDataProvider.JCO_PASSWD, password);
			ABAP_AS_PROPERTIES = properties;
		}
		@Override
		public Properties getDestinationProperties(String arg0) throws DataProviderException {
			// TODO Auto-generated method stub
			return ABAP_AS_PROPERTIES;
		}

		@Override
		public void setDestinationDataEventListener(DestinationDataEventListener arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean supportsEvents() {
			// TODO Auto-generated method stub
			return false;
		}
		
		
	}

}
