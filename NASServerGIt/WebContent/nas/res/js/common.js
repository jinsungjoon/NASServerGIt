/**
 * 
 */
$(document).ready(function() {
});
$(window).ready(function() {
});
$.fn.extend({	
	goMenuList : function (parentId, menuId){
		var menuUrl = "";
//		var showTitle = 1;
//		if($.fn.checkActingData(menuId, depth) == false) {
//			return;
//		}
//		window.sharedProxy.andrClearActingData();
		switch(menuId){
			case "menu000"                 : menuUrl = "../login/login.jsp";             break; // 로그인
			case "일별 장애접수 현황 조회" : menuUrl = "../rpt/rpt200.jsp";              break; // 일별 장애접수 현황 조회
			case "월별 장애접수 현황 조회" : menuUrl = "../rpt/rpt202.jsp";              break; // 월별 장애접수 현황 조회
			case "사용자 관리"             : menuUrl = "../master/master_user_list.jsp"; break;	// 사용자 관리
			case "제조사 관리"             : menuUrl = "../master/master_maker_list.jsp"; break;	// 제조사 관리
			case "앱관리"                  : menuUrl = "../master/master_app_info.jsp";  break; // 앱관리
			default 		               : menuUrl = "../sample/sample.jsp";           break; // 에러화면
			
		}
		//브라우져 로컬스토리지 사용

		localStorage.setItem('menuTitle', parentId);
		localStorage.setItem('menuId'   , menuId);
		location.href = menuUrl;
//		window.menuProxy.andrWebviewUrl(menuUrl,showTitle);
	},
	//달력 초기화
	calInit:function(){
		  $('.datepicker').pickadate({
			    selectMonths: true, // Creates a dropdown to control month
			    today: 'Today',
			    clear: 'Clear',
			    close: 'Ok',
			    closeOnSelect: false, // Close upon selecting a date,
			    monthsFull: ['1월달', '2월달', '3월달', '4월달', '5월달', '6월달', '7월달', '8월달', '9월달', '10월달', '11월달', '12월달'],
			    monthsShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
			    weekdaysFull: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
			    weekdaysShort: ['일', '월', '화', '수', '목', '금', '토'],
			    format: 'yyyy-mm-dd'
//	 	    	format: 'mm/dd/yyyy'

		  });	
		},
});
$.ajaxSetup({
	global: false,
	timeout : G_MTERM_TIMEOUT,
	beforeSend : function() {	// ajax 요청전
		$.fn.showLoading();
	},
	complete : function() {		// ajax 요청완료
		setTimeout(function(){
			$.fn.hideLoading();
		}, 500);
	}
});
//스마트폰 체크
function isMobile() {
	var UserAgent = navigator.userAgent;
	if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null
			|| UserAgent.match(/LG|SAMSUNG|Samsung/) != null) {
		return true;
	} else {
		return false;
	}
}

function browserCheck(){
	var browser = "";
	var agent = navigator.userAgent.toLowerCase();

	if (agent.indexOf("chrome") != -1) {
		browser = "chrome";
	}else if (agent.indexOf("safari") != -1) {
		browser = "safari";
	}else if (agent.indexOf("firefox") != -1) {
		browser = "firefox";
	}else if (agent.indexOf("msie") != -1) {
		browser = "msie";
	}
	return browser;
}
