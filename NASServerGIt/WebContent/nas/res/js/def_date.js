/* ********************************************************************************
 * BY 최정주
 * 1. 공통처리를 위한 스크립트 파일
 * 2. 특정 페이지만을 위한 스크립트는 해당 폴더내 js 파일을 생성해서 정의하세요.
 *
 * [2013-11-26] 최초작성 - 최정주
 * ******************************************************************************** */


//===========================================================================================
/* 전역변수 선언 규칙
 * 1. 대문자로 선언
 * 2. G_변수명으로 선언
 * */


//===========================================================================================
/* 사용자 정의 함수
 * 호출시 $.getList(); 로 호출 가능
 * */
$.fn.extend({
	//----------------------------------------------------------------------------------------
	// app에서 오늘날짜 세팅을 하기 위한 처리
	getCurrentDateTime : function (type){

		var yyyy  = "2014";
		var mm = "01";
		var dd = "01";
		var hh = "12";
		var mi = "00";
		var ss = "00";

		var d = new Date();
		yyyy = d.getFullYear();
		mm = (d.getMonth() + 1);
		dd = d.getDate();
		hh = d.getHours();
		mi = d.getMinutes();
		ss = d.getSeconds();

		if(mm < 10) mm = "0" + mm;
		if(dd < 10) dd = "0" + dd;
		if(hh < 10) hh = "0" + hh;
		if(mi < 10) mi = "0" + mi;
		if(ss < 10) ss = "0" + ss;

		if(type == "DATETIME")
		{
			return yyyy + "-" + mm + "-" +dd + " " +hh + ":" +mi + ":" + ss;
		}
		else  if(type == "DATE_YM")
		{
			return  yyyy + "-" +mm;
		}
		else  if(type == "DATE_YMD")
		{
			return  yyyy + "-" +mm + "-" +dd;
		}
		else  if(type == "DATE_YYMD")
		{
			return  yyyy + "" +mm + "" +dd;
		}
		else  if(type == "DATE_MD")
		{
			return  mm + "" +dd;
		}
		else  if(type == "DATE_YMDHIS")
		{
			return  yyyy + "" +mm + "" +dd+ "" +hh+ "" +mm+ "" +ss;
		}
		else  if(type == "DATE_HMS")
		{
			return  hh+ "" + mm + "" + ss;
		}
		else  if(type == "TIME")
		{
			return  hh + "" +mi + "" +ss;
		}
	},

	// 현재날짜
	getCurrDate : function(){
		var currDate = new Date();

		var yyyy = currDate.getFullYear().toString();
	    var mm = (currDate.getMonth() + 1).toString();
	    var dd = currDate.getDate().toString();

	    return yyyy + (mm[1] ? mm : '0'+mm[0]) + (dd[1] ? dd : '0'+dd[0]);
	},

	// 현재요일
	getCurrWeekDay : function(){
		var currDate = new Date();
	    return currDate.getDay();
	},

	getDateList : function(addDateCnt){
		var dateList = new Array();
		var dateOutputList = new Array();

		var myDate = new Date();
		var year = myDate.getFullYear();
		var month = myDate.getMonth() + 1;
		if(month <= 9){month= "0" + month;}

		var date = myDate.getDate();
		if(date <= 9){date= "0" + date;}
		var startDate =  year +"/"+ month +"/"+ date;

		var dayOfMonth = myDate.getDate();
		myDate.setDate(dayOfMonth + addDateCnt);

		year = myDate.getFullYear();
		month = myDate.getMonth() + 1;
		if(month <= 9){month= "0" + month;}
		date = myDate.getDate();
		if(date <= 9){date= "0" + date;}

		for(var i = 0; i <= addDateCnt; i++){
			var currentDate = new Date(startDate);
			currentDate.setDate(currentDate.getDate() + i);
			year = currentDate.getFullYear();
			month = currentDate.getMonth() + 1;
			if(month <= 9){month= "0" + month;}
			date = currentDate.getDate();
			if(date <= 9){date= "0" + date;}
			dateList[i] = year +""+ month +""+ date;
			dateOutputList[i] = year +"년 "+ month +"월"+ date+"일";
		}

		 return new Array(dateList, dateOutputList);
	},


	SetWeek : function () {

		var now = new Date();
	    var nowDayOfWeek = now.getDay();
	    var nowDay = now.getDate();
	    var nowMonth = now.getMonth();
	    var nowYear = now.getYear();
	    nowYear += (nowYear < 2000) ? 1900 : 0;

	    var weekStartDate = $.fn.formatDate(new Date(nowYear, nowMonth, nowDay - nowDayOfWeek));
	    var weekEndDate = $.fn.formatDate(new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek)));

	    return new Array(weekStartDate, weekEndDate);
	},

	formatDate : function (date) {
	    var mymonth = date.getMonth() + 1;
	    var myweekday = date.getDate();
	    return (date.getFullYear() + "-" + ((mymonth < 10) ? "0" : "") + mymonth + "-" + ((myweekday < 10) ? "0" : "") + myweekday);
	},

	SetNextday : function () {

		var mydate = new Date();
	    mydate.setDate(mydate.getDate() + 1);

	    return $.fn.formatDate(mydate);
	},

	SetYesterday : function () {

		var mydate = new Date();
	    mydate.setDate(mydate.getDate() - 1);

	    return $.fn.formatDate(mydate);
	},

	isLeapYear : function(y) {
	   if (y < 100)
	   y = y + 1900;
	   if ( (y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0) ) {
	           return true;
	   } else {
	           return false;
	   }
	},

	getNumberOfDate : function(yy, mm) {
	   month = new Array(29,31,28,31,30,31,30,31,31,30,31,30,31);
	   if (mm == 2 && $.fn.isLeapYear(yy)) mm = 0;
	   return month[mm];
	},
	 /**
	* 1. 함수명 : getFormatDate
	* 2. 주기능 : 날짜 Format
	* 3. Input Parameters
	*	1) dt (대상 날짜)
	*	2) format (날짜 포맷)
	* 4. Output Parameters
	* 	1) rtn (포맷 날짜)
	*/
    getFormatDate : function(dt, format){
    	var rtn = dt;
    	//dt = dt.replace(/[-/.]/gi, "");
    	dt = dt.replace(/[-\.]/gi, "");
    	if(dt != null && dt != ""){
    		switch (format) {
	    		case "YYYYMMDD" :
					if(dt.length >= 8) rtn = dt.substr(0,4)+dt.substr(4,2)+dt.substr(6,2);
				break;
    			case "YYYY-MM-DD" :
    				if(dt.length >= 8) rtn = dt.substr(0,4)+"-"+dt.substr(4,2)+"-"+dt.substr(6,2);
    			break;
    			case "YY.MM.DD" :
    				if(dt.length >= 8) rtn = dt.substr(2,2)+"."+dt.substr(4,2)+"."+dt.substr(6,2);
    			break;
    			case "YY-MM-DD" :
    				if(dt.length >= 8) rtn = dt.substr(2,2)+"-"+dt.substr(4,2)+"-"+dt.substr(6,2);
    			break;
    			case "YY-MM-DD HH:MI" :
    				if(dt.length >= 12) rtn = dt.substr(2,2)+"-"+dt.substr(4,2)+"-"+dt.substr(6,2)+" "+dt.substr(8,2)+":"+dt.substr(10,2);
    			break;
    			case "YY/MM/DD HH:MI" :
    				if(dt.length >= 12) rtn = dt.substr(2,2)+"/"+dt.substr(4,2)+"/"+dt.substr(6,2)+" "+dt.substr(8,2)+":"+dt.substr(10,2);
    			break;
    			case "YYYY-MM-DD HH:MI" :
    				if(dt.length >= 8) rtn = dt.substr(0,4)+"-"+dt.substr(4,2)+"-"+dt.substr(6,2)+" "+dt.substr(8,2)+":"+dt.substr(10,2);
    			break;
    			case "YYYY-MM-DD HH:MI:SS" :
    				if(dt.length >= 14) rtn = dt.substr(0,4)+"-"+dt.substr(4,2)+"-"+dt.substr(6,2)+" "+dt.substr(8,2)+":"+dt.substr(10,2)+":"+dt.substr(12,2);
				break;
    			case "YY.MM.DD HH:MI:SS" :
    				if(dt.length >= 14) rtn = dt.substr(2,2)+"."+dt.substr(4,2)+"."+dt.substr(6,2)+" "+dt.substr(8,2)+":"+dt.substr(10,2)+":"+dt.substr(12,2);
				break;
    			case "HH:MI" :
    				if(dt.length >= 4) rtn =  dt.substr(0,2)+':'+dt.substr(2,2);
    			break;
    		}
    	}
    	return rtn;
    },

	/**
	* 1. 함수명 : getSearchDate
	* 2. 주기능 : 날짜 구하기
	* 3. Input Parameters
	*	1) dt (대상 날짜)
	*	2) addDay (추가 일수)
	* 4. Output Parameters
	* 	1) dt (추가일수 적용된 날짜)
	*/
	getSearchDate : function(dt, addDay){
		//dt = dt.replace(/[-/.]/gi, "");
		dt = dt.replace(/[-\.]/gi, "");
		if(dt.length >= 8){
			var resultDt = new Date(dt.substr(0,4), (dt.substr(4,2))-1, dt.substr(6,2));
			resultDt.setDate(resultDt.getDate() + addDay);

			var yyyy = resultDt.getFullYear().toString();
		    var mm = (resultDt.getMonth() + 1).toString();
		    var dd = resultDt.getDate().toString();

		    resultDt = yyyy + (mm[1] ? mm : '0'+mm[0]) + (dd[1] ? dd : '0'+dd[0]);
		    dt = resultDt;
		}
		return dt;
	},

	//월차이구하기 - start, end format: yyyymmdd
	getDifMonths:function(start, end) {
		var startYear = start.substring(0, 4);
		var endYear = end.substring(0, 4);
		var startMonth = start.substring(4, 6) - 1;
		var endMonth = end.substring(4, 6) - 1;
		var startDay = start.substring(6, 8);
		var endDay = end.substring(6, 8);

		// 연도 차이가 나는 경우
		if (eval(startYear) < eval(endYear))	{
			// 종료일 월이 시작일 월보다 수치로 빠른 경우
			if (eval(startMonth) > eval(endMonth))	{
				var newEnd = startYear + "1231";
				var newStart = endYear + "0101";

				return (eval($.fn.getDifMonths(start, newEnd)) + eval($.fn.getDifMonths(newStart, end))).toFixed(2);
			// 종료일 월이 시작일 월보다 수치로 같거나 늦은 경우
			} else									{
				var formMonth = eval(startMonth) + 1;
				if (eval(formMonth) < 10)	formMonth = "0" + formMonth;

				var newStart = endYear + "" + formMonth + "" + startDay;
				var addMonths = (eval(endYear) - eval(startYear)) * 12;

				return (eval(addMonths) + eval($.fn.getDifMonths(newStart, end))).toFixed(2);
			}
		} else									{
			// 월별 일수차 (30일 기준 차이 일수)
			var difDaysOnMonth = new Array(1, -2, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1);
			var difDaysTotal = $.fn.getDifDays(start, end);

			for (i = startMonth; i < endMonth; i++)	{
				if (i == 1 && $.fn.isLeapYear(startYear))	difDaysTotal -= (difDaysOnMonth[i] + 1);
				else									difDaysTotal -= difDaysOnMonth[i];
			}

			// because view this function
			//window.alert("- run getDifMonths()\n- " + start + " ~ " + end + " => " + (difDaysTotal / 30).toFixed(2));

			return (difDaysTotal / 30).toFixed(2);
		 }
	},

	//일차이구하기 - start, end format: yyyymmdd
	getDifDays:function(start, end) {
		var dateStart = new Date(start.substring(0, 4), start.substring(4, 6) - 1, start.substring(6, 8));
		var dateEnd = new Date(end.substring(0, 4), end.substring(4, 6) - 1, end.substring(6, 8));
		var difDays = (dateEnd.getTime() - dateStart.getTime()) / (24 * 60 * 60 * 1000);

		// because view this function
		//window.alert("- run getDifDays()\n- " + start + " ~ " + end + " => " + Math.ceil(difDays));

		return Math.ceil(difDays);
	}
}); //END extend





