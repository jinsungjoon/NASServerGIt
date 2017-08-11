/**
 * 
 */

var version    = "";
$(document).ready(function() {
});
$(window).ready(function() {
	$.fn.SetAppInit();
});
$.fn.extend({	
	SetAppInit:function(){
		if(G_MOBILE_FLAG){
			version = window.userProxy.andrGetAppVersion();
			var testTmp = window.userProxy.andrGetTestServerCheck();
			$("#version").html((testTmp == '' ? '' : '<font color="red" style="font-style: italic;">(' + testTmp + ')</font>') + "Version : " + parseFloat(version).toFixed(2));
			
			// 로그인아이디 설정
//			var sessionUserId = $.fn.getSessionUserId();
//			if(sessionUserId != "") {
//				$("#userId").val(sessionUserId);
//			}
			if(testTmp != "") {
				alert("테스트서버입니다.\n관리자에게 문의하십시오.");
			}
		}
	},
	loginProc:function(){
		var userId ="",userPass="";
			userId = $("#userId").val();
			userPass = $("#userPass").val();
		if(userId == ""){
			alert("아이디를 입력하세요");
			$("#userId").focus();
			return;
		}
		if(userPass == ""){
			alert("비밀번호를 입력하세요");
			$("#userPass").focus();
			return;
		}
		var phoneNo = "";
		if(G_MOBILE_FLAG){
			phoneNo = window.userProxy.andrGetPhoneNo();
		}
		var data = {
				serviceFlag:G_SF_USER_LOGIN,
				callback:"abcd",
				userId:userId,
				passwd:userPass,
				version:version,
				phoneNo:phoneNo
		};
		$.ajax({
			global: false,
			timeout : 30000,
			type : "POST",
			url : G_JSONP_URL_USER,
			data : data,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			success : function(data){
//				alert(JSON.stringify(data));
				//로그인 성공
				if(data.RETURN_CODE=="200"){
//					if(G_MOBILE_FLAG)$.fn.doLoginSuccess(data.RES_LDATA);
////					else location.href = '../delivery/list.jsp';
//					else $.fn.goMenuList("일별 장애접수 현황 조회");
					
					// 모바일 구분 없이 페이지 이동.
					$.fn.goMenuList("분석","일별 장애접수 현황 조회");
				}
				//로그인 실패
				else{
					alert(data.RETURN_MSG);
				}
			},
			error: function(request,status,error) {
				alert(error);
			}
		});
	},
	
	//login callback
	doLoginSuccess : function (RES_LDATA) {
		G_LOGIN_USERID = $("#userId").val().trim();
		
		// 버전체크
		if($.fn.VersionCheck(RES_LDATA) == true) 
			return;
		
		// 로그인 정보를 JSON형식 데이터로 변경 / 저장
		$.fn.LoginDataDivision(RES_LDATA, "false");
		
		// 비밀번호 체크
		if($.fn.PasswordCheck(RES_LDATA) == true)
			return;
		
		// 해쉬 데이터 초기화
		window.sharedProxy.andrHashClearAllContent();
		
		window.sharedProxy.andrSPAddItem(G_LOGIN_USERID, "S_CHECK_FLAG"  , "true");

		// PUSH ID 처리
		console.log("PUSH처리 해야 한다...");
		//$.fn.regPushId();
		
		$.fn.doSessionLogin(G_LOGIN_USERID);
	},
	
	//스마트폰 버전체크
	VersionCheck : function(RES_LDATA) {
		var version = window.userProxy.andrGetAppVersion();
		if (RES_LDATA.CURR_VERSION != version && RES_LDATA.CURR_VERSION != "00.00") {
			$.fn.doSessionAutoSyncInit(G_LOGIN_USERID);
			
			alert("신규프로그램이 업데이트 되었습니다.\n현재버전 : " + version + "\n신규버전 : " + RES_LDATA.CURR_VERSION, 0);
			window.nativeProxy.andrAppUpgrade("");
			return true;
		}
		
		return false;
	},
	
	
	//로그인 정보저장
	LoginDataDivision : function(data, checkFlag) {
		// 대소문자때문에 서버에서 받은 값으로 변경한다.
		G_LOGIN_USERID = data.USER_ID;
		
		var divData = {
				USER_ID           : data.USER_ID,
				USER_NM           : data.USER_NAME,
				ROLE_CODE         : data.ROLE_CODE,
				SYS_GUBUN         : data.SYS_GUBUN,
				DEPT_CODE         : data.DEPT_CODE,
				DEPT_NAME         : data.DEPT_NAME,
				VALIDATION_KEY    : data.VALIDATION_KEY,
				CURR_VERSION      : data.CURR_VERSION,
				PASSWD_EXP_DAYS   : data.PASSWD_EXP_DAYS,
				SERVER_DATE       : data.SERVER_DATE
		}
		divData = '{"'+G_LOGIN_USERID+'":[' + JSON.stringify(divData) + ']}';
		
		// 로그인 정보를 로컬에 저장
		window.sharedProxy.andrSPAddContent(G_LOGIN_USERID, divData);
	},
	
	//비밀번호 체크
	PasswordCheck : function(RES_LDATA) {
		if (Number(RES_LDATA.PASSWD_EXP_DAYS) < 0) {
			
			var msgBox = new MsgBox("비밀번호가 만료하였습니다.<br>(" + (0-RES_LDATA.PASSWD_EXP_DAYS).toString() + "일경과).<br>비밀번호를 변경 후 사용하십시오.");
			msgBox.title("확인");
			msgBox.button1("이전", function(result) {});
			msgBox.button2("변경", function(result) {
				window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"userCode", RES_LDATA.USER_ID);
				window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"userName", RES_LDATA.USER_NAME);
				
				$.fn.showChildView("mnu_inb213");
				$("#passWd").val('');
			});
			msgBox.confirm();
			return true;
		}
		
		return false;
	},
});


