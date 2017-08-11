package com.nwtns.nas.common;

import java.text.*;


public class CCKBCCalendar {

	java.util.Date			curDate;
	java.sql.Date			delDate;
	String		curDateText;
	String		delDateText;
	
	public CCKBCCalendar() {
		// curDate is today.
		curDate 		= new java.util.Date();
		Format	formatter	= new SimpleDateFormat("yyyy-MM-dd");
		curDateText			= formatter.format(curDate);
	}
	
	/**
	 * Initialize with a given current date text in 'yyyy-mm-dd' format.
	 */
	public CCKBCCalendar(String curDateText) throws ParseException {
		curDateText		=  getReplaceString(curDateText, "-", ""); 
		this.curDateText		= curDateText;
	}
	
	public String	getCurrentDateText() {
		return curDateText;		
	}
	
	/*public java.sql.Date getDeliveryDate() throws SQLException,  Exception{
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs 		= null;
			
		StringBuffer	sb 	= new StringBuffer();
		String	tempDate	= "";
		
		*//**
		*  Get Delivery Date.
		*//*
		conn = getConnection();
		stmt = conn.createStatement();
			        				        
		rs = stmt.executeQuery("select pxdel2  from pxi03 where pxdate = TO_DATE('" + curDateText + "', 'yyyy-mm-dd')");
       	
		if (rs.next()) {
			delDate = rs.getDate(1);
		} else {
			throw new Exception("Delivery Date Retrieval Exception");
		}
		
		if (rs != null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (conn != null) 
			conn.close();			
	
		return delDate;
	}
	
	public java.sql.Date getDeliveryDate(String deldat2) throws SQLException,  Exception{
		
		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs 		= null;
			
		StringBuffer	sb 	= new StringBuffer();
		String	tempDate	= "";
		
		*//**
		*  Get Delivery Date.
		*//*
		conn = getConnection();
		stmt = conn.createStatement();
			        				        
		rs = stmt.executeQuery("select pxdel2  from pxi03 where pxdel1 = TO_DATE('" + deldat2 + "', 'yyyymmdd')");
       	
		if (rs.next()) {
			delDate = rs.getDate(1);
		} else {
			throw new Exception("Delivery Date Retrieval Exception");
		}
		
		if (rs != null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (conn != null) 
			conn.close();			
	
		return delDate;
	}*/
	
	public String getReplaceString(String str, String oldWord, String newWord){ 

		String result= ""; 
		int index = 0; 
		int oldLength = oldWord.length();	 
	
	
		while(true){
			index = str.indexOf(oldWord);
	
			if (index != -1){
			result += str.substring(0, index) + newWord;
	
			str = str.substring(index + oldLength);
	
			if (str.length() == oldLength) {
				result += newWord;
				break;
			}
			}else{
		
			result += str;
			break;
			}
		}
	
		return result;  
	}
}

