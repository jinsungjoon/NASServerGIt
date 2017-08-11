
$(document).ready(function() {
	// 로그인페이지가 호출되면 로그인이 안된 상태로 플래그 처리
	$.fn.doSessionLogout();
	
	$("#loginBtn").click(function (){
		$.fn.doLoginEvent();
	});
});

$(window).ready(function() {	
	
});

$.fn.extend({	
	
	SetAppInit : function() {
		// 버전설정
		var version = window.userProxy.andrGetAppVersion();
		var testTmp = window.userProxy.andrGetTestServerCheck();
		// [01.00]형식을 [1.00]형식으로 표현하기 위함. 
		$("#lblAppVersion").html((testTmp == '' ? '' : '<font color="red" style="font-style: italic;">(' + testTmp + ')</font>') + "Version : " + parseFloat(version).toFixed(2));
		
		// 로그인아이디 설정
		var sessionUserId = $.fn.getSessionUserId();
		if(sessionUserId != "") {
			$("#userId").val(sessionUserId);
		}
		if(testTmp != "") {
			$.andrToastShow("테스트서버입니다.\n관리자에게 문의하십시오.");
			//(new MsgBox("테스트서버입니다.<br>관리자에게 문의하십시오.", "알림")).alert();
		}
	},
	
	doLoginEvent : function() {
		var userId = $("#userId").val();
		var passWd = $("#passWd").val();
		var sessionUserId = $.fn.getSessionUserId();
		if(userId!=sessionUserId){
		}
		/**
		 * 2014.04.18 폰넘버 추가.
		 * phoneNo
		 * */
		var phone_number = window.userProxy.andrGetPhoneNo();
		
		if(userId == ""){
			(new MsgBox("사용자ID를 입력하세요.", "알림")).alert();
			$("#userId").focus();
			return;
		}else if(passWd == ""){
			(new MsgBox("비밀번호를 입력하세요.", "알림")).alert();
			$("#passWd").focus();
			return;
		}
		//passWd = window.encProxy.andrEncriptPasswd(passWd);
		var version = window.userProxy.andrGetAppVersion();
		var data = {
				serviceFlag:G_SF_USER_LOGIN,
				callback:"abcd",
				userId:userId,
				version:version,
				passwd:passWd,
				phoneNo:phone_number
		};

		$.ajax({
			type : "POST",
			url : G_JSONP_URL_USER,
			data : data,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			success : function(data){
				console.log("login sucessData:"+JSON.stringify(data));
				//로그인 성공
				if(data.RETURN_CODE=="200"){
					$.fn.doLoginSuccess(data.RES_LDATA);
				}
				//로그인 실패
				else{
					(new MsgBox(data.RETURN_MSG, "알림")).alert();
				}
			},
			error: function(request,status,error) {
				(new MsgBox("요청실패<br>"+error, "알림")).alert();
			}
		});
	},
	
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
		
		window.sharedProxy.andrSPAddItem(S_LOGIN, "S_CHECK_FLAG"  , "true");

		// PUSH ID 처리
		console.log("PUSH처리 해야 한다...");
		//$.fn.regPushId();
		
		$.fn.doSessionLogin(G_LOGIN_USERID);
	},
	
	VersionCheck : function(RES_LDATA) {
		var version = window.userProxy.andrGetAppVersion();
		if (RES_LDATA.CURR_VERSION != version && RES_LDATA.CURR_VERSION != "00.00") {
			$.fn.doSessionAutoSyncInit(G_LOGIN_USERID);
			
			window.nativeProxy.andrToastShow("신규프로그램이 업데이트 되었습니다.\n현재버전 : " + version + "\n신규버전 : " + RES_LDATA.CURR_VERSION, 0);
			window.nativeProxy.andrAppUpgrade("");
			return true;
		}
		
		return false;
	},
	
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
		divData = '{"'+S_LOGIN+'":[' + JSON.stringify(divData) + ']}';
		
		// 로그인 정보를 로컬에 저장
		window.sharedProxy.andrSPAddContent(S_LOGIN, divData);
	},
	
	PasswordCheck : function(RES_LDATA) {
		if (Number(RES_LDATA.PASSWD_EXP_DAYS) < 0) {
			//alert("비밀번호가 만료하였습니다.\n(" + (0-RES_LDATA.PASSWD_EXP_DAYS).toString() + "일경과). \n영업담당에게 문의하세요\n임시로 사용 가능하게 합니다.");
			//alert("비밀번호가 만료하였습니다.\n[기준정보-사용자정보]에서 비밀번호를 변경 후 사용하십시오.\n(" + (0-RES_LDATA.PASSWD_EXP_DAYS).toString() + "일경과).");
			
//			alert("비밀번호가 만료하였습니다.\n(" + (0-RES_LDATA.PASSWD_EXP_DAYS).toString() + "일경과). \n비밀번호를 변경 후 사용하십시오.");
//			$.fn.showChildView("mnu_inb511");
//			// 비밀번호 입력란 초기화
//			$("#passWd").val('');
//			return;
			
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
//		else
//		if (Number(RES_LDATA.PASSWD_EXP_DAYS) < 20) {
//			$.andrToastShow("비밀번호 만료 ["+RES_LDATA.PASSWD_EXP_DAYS+"]일전 입니다." , 0);
//		} else
//		if (Number(RES_LDATA.PASSWD_EXP_DAYS) <= 5) {
//			alert("비밀번호 만료 ["+RES_LDATA.PASSWD_EXP_DAYS+"]일전 입니다.");
//		}
		
		return false;
	},
	
	regPushId : function(oldRegId, regId, agencyCode, agencyEmpCode){
		
		var userId = $("#userId").val();
		//G_JSONP_URL_PUSH = G_SERVER_URL+"/nwtns/asm/push.asm" + "?userId=" + G_LOGIN_USERID + "&deviceId=" + G_DEVICEID + "&osFlag=" + G_OS_FLAG;
		
		
		var regId = "";
		var oldRegId = "";
		try{
			regId = window.sharedProxy.andrGetRegId();
		}catch(e){
			regId = "";
		}
		if(regId == "")
			return;
		try{
			oldRegId = window.sharedProxy.andrGetOldRegId();
		}catch(e){
			oldRegId = "";
		}
		console.log("reg id : "+ regId);
		
		//////////////
		
		var data = {
				serviceFlag:G_SF_REG_PUSH_REGID_V01,
				callback:"abcd",
				regId:regId,
				oldRegId : oldRegId,
				userId:userId
		};
		
		$.ajax({
			type : "POST",
			url : G_JSONP_URL_PUSH,
			data : data,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			success : function(data){
				console.log("login sucessData:"+JSON.stringify(data));

				//로그인 성공
				if(data.RETURN_CODE=="200"){
					$.fn.doLoginSuccess(data.RES_LDATA);
				}
				//로그인 실패
				else{
					(new MsgBox(data.RETURN_MSG, "알림")).alert();
				}
			},
			error: function(request,status,error) {
				(new MsgBox("요청실패<br>"+error, "알림")).alert();
			}
		});
	},
	
	UserId_KillFocus : function() {
		var userId = $("#userId").val();
		if(userId != '' && userId != "admin") {
			var aTmp = userId.split("@");
			if(aTmp.length == 1) {
				userId += "@nwtns.com";
			} else {
				console.log("aTmp[1] : " + aTmp[1]);
				if((aTmp[1].split(".")).length <= 1) {
					userId = aTmp[0] + "@nwtns.com"
				}
			}
			
			$("#userId").val(userId);
		}
	}
});












