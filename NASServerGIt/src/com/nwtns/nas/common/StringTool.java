package com.nwtns.nas.common;

import java.util.Calendar;

import com.nwtns.framework.util.NWLog;


// 작성자.. : 박경찬.  작성일자.. : 2003_01_01 최종수정일.. : 2003_03_03
// 수정일자.. : 2003_07_22   convDatey3m2d2 method 추가.
//                 : 2004_05_24   getHHMMSS() method 추가 (송진규)

public class StringTool {
	public StringTool() {
	}
	
	public String convDatey3m2d2(String date) {				//yyyymmdd의 format을 cyymmdd의 format으로 변환
		String newdate = new String();
		String year = null;
		String day = null;
			
		year = date.substring(0,4);
		int years = Integer.parseInt(year);
		 
		if ( years >= 2000) {
			newdate += (years - 1900);
		} else if ( years < 2000) {
			newdate += "0";			newdate += (years - 1900);	
		}
		newdate += date.substring(4, 8);
		return newdate;
	}

	public String getcurrenty4m2d2() { //yyyymmdd
		Calendar cl = Calendar.getInstance();		
		cl.add(cl.HOUR_OF_DAY, 9);
		int Anyear = cl.get(cl.YEAR);	
		int month = cl.get(cl.MONTH) + 1;
	    //원본int date = cl.get(cl.DATE);
		cl.add(cl.HOUR_OF_DAY, -9);
		int date = cl.get(cl.DATE);
		
        NWLog.dc(this.getClass(), "== stringTool.month ==");
		
		String smonth = Integer.toString(month);
		String sdate = Integer.toString(date);
		
		NWLog.dc(this.getClass(), "stringTool.month:" + smonth);
		NWLog.dc(this.getClass(), "stringTool.date:" + sdate);
		
		
		return	 Integer.toString(Anyear) + 
					((month<10) ? "0" + Integer.toString(month) : Integer.toString(month)) +
					((date<10) ? "0" + Integer.toString(date) : Integer.toString(date)) ;
	}
	                                             
	public String getlasty4m2d2() {
		Calendar cl = Calendar.getInstance();
		cl.add(cl.HOUR_OF_DAY, 9);//24시간표시 +9시간후
		int Anyear = cl.get(cl.YEAR);	
		int month = cl.get(cl.MONTH);
		int date = cl.get(cl.DATE);
		cl.add(cl.HOUR_OF_DAY, -9);	//24시간으로표시 -9시간전
		
		if ( month == 0 ) {
			Anyear = Anyear - 1;
			month = 12;
		}
		return  Integer.toString(Anyear) + 
					((month<10) ? "0" + Integer.toString(month) : Integer.toString(month)) +
					((date<10) ? "0" + Integer.toString(date) : Integer.toString(date));
	}
	
	public String getcurrenty3m2d2() {
		
		String current = getcurrenty4m2d2();
		NWLog.dc(this.getClass(), "stringTool.getcurrenty4m2d2():" + current);
		
		String year = current.substring(0,4);
		int years = Integer.parseInt(year);
		String currentday = new String();

		if ( years >= 2000) {
			currentday += (years - 1900);
		} else if ( years < 2000) {
			currentday += "0";			currentday += (years - 1900);	
		}
		
		NWLog.dc(this.getClass(), "stringTool.currentday:" + currentday);
		
		currentday += current.substring(4, 8);
		return currentday;
	}
	
	public String getlasty3m2d2() {
		String current = getlasty4m2d2();
		String year = current.substring(0, 4);
		int cyear = Integer.parseInt(year);
		String last = new String();

		if ( cyear >= 2000) {
			last += (cyear - 1900);
		} else if ( cyear < 2000) {
			last += "0";			last += (cyear - 1900);	
		}	
		last += current.substring(4, 8);
		return last;
	}

	
}

