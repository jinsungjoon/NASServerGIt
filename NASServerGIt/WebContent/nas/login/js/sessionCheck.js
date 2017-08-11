
// include common.js 
var D_SESSION_YMD     = "SESSION_YMD";      // 최종 로그인일자      (YYYYMMDD) 
var D_SESSION_USERID  = "SESSION_USERID";   // 최종 로그인 아이디   (12345678) 
var D_SESSION_LOGINYN = "SESSION_LOGINYN";  // 로그인 여부          (Y / N)
var D_SESSION_SYNCYN  = "SESSION_SYNCYN";   // 당일동기화 여부      (Y / N)

	
$.fn.extend({
	
	// [어플리케이션이 처음 구동되면서] 
	//  - 로그인 / 메인메뉴 분기 처리
	startApplication : function() {
		window.sharedProxy.andrClearActingData();
		
		var sessionYmd     = window.sharedProxy.andrGetSession(D_SESSION_YMD);
		var sessionUserId  = window.sharedProxy.andrGetSession(D_SESSION_USERID);
		var sessionLoginYn = window.sharedProxy.andrGetSession(D_SESSION_LOGINYN);
		var sessionSyncYn  = window.sharedProxy.andrGetSession(D_SESSION_SYNCYN);
		
		console.log("startApplication - D_SESSION_YMD     : " + window.sharedProxy.andrGetSession(D_SESSION_YMD));
		console.log("startApplication - D_SESSION_LOGINYN : " + window.sharedProxy.andrGetSession(D_SESSION_LOGINYN));
		
		var currYmd = $.fn.getCurrDate();
		
		// App에서 로그인정보를 사용하기 위해서 저장
		window.userProxy.andrSetLogin(sessionUserId);
		
		console.log("startApplication - currYmd : " + currYmd);
		
		//console.log("startApplication - D_SESSION_YMD 1 ");
				
		// 메인메뉴로 이동 : 로그인상태 && 최종로그인일자가 현재일자 
		if(sessionLoginYn == 'Y' && sessionYmd == currYmd) {
			//console.log("startApplication - D_SESSION_YMD 2 ");
			// 로그인 권한별 메뉴구성
			var sysGubun = window.sharedProxy.andrSPGetItem(S_LOGIN, "SYS_GUBUN");
			$.fn.goMenuPageRefresh(sysGubun);
			
			//console.log("startApplication - D_SESSION_YMD 3 ");
			var testTmp = window.userProxy.andrGetTestServerCheck();
			if(testTmp != "") {
				$.andrToastShow("테스트서버입니다.\n관리자에게 문의하십시오.");
				//alert("테스트서버입니다.\n관리자에게 문의하십시오.");
			}
			
			if(sessionSyncYn == "Y") {
				//console.log("startApplication - D_SESSION_YMD 4 ");
				// 로그인 권한별 첫 페이지 이동
				if(sysGubun == V_SYS_01){
					window.menuProxy.andrPageRefresh("inc/inc_menu_1000.jsp");
				}
//				$.fn.gotoFistMenuPage();
				$.fn.goMenuList("분석","일별 장애접수 현황 조회");
			} else {
				//console.log("startApplication - D_SESSION_YMD 5 ");
				// 자동동기화를 위한 파라미터
				window.sharedProxy.andrHashAddItem(H_TMP, 0, "TMP_AUTOSYNC_FLAG", "AUTO");
				// 동기화 실행
				$.fn.goMenuList("mnu_inb100");
				
				window.nativeProxy.andrToastShow("동기화부분이 완료되면 여기 주석처리 해야 한다.", 0);
				// 동기화 여부 (동기화 완료 후 설정해야 한다.)
				window.sharedProxy.andrSetSession(D_SESSION_SYNCYN, "Y");
				window.sharedProxy.andrSetSession(D_SESSION_YMD, currYmd);
			}
			return;
		}
		//console.log("startApplication - D_SESSION_YMD 6 ");
		// 로그인으로 이동 : 로그인상태가 아니거나 최종로그인일자가 현재일자가 아닌경우
		if(sessionLoginYn != 'Y' || sessionYmd != currYmd) {
			//console.log("startApplication - D_SESSION_YMD 7 ");
			if(sessionYmd != currYmd) {
				//console.log("startApplication - D_SESSION_YMD 8 ");
				// 최종로그인일자가 현재일자가 아닌경우에는 동기화를 다시 받아야 한다.
				window.sharedProxy.andrSetSession(D_SESSION_SYNCYN, "N");
			}
			
			//console.log("startApplication - D_SESSION_YMD 9 ");
			$.fn.goMenuList("mnu_login", "");
			return;
		}
	},
	
	// [로그인화면에서 로그인성공후 처리]
	//  - 메인메뉴 / 동기화화면 분기 처리
	doSessionLogin : function(loginId) {
		var sessionYmd     = window.sharedProxy.andrGetSession(D_SESSION_YMD);
		var sessionUserId  = window.sharedProxy.andrGetSession(D_SESSION_USERID);
		var sessionSyncYn  = window.sharedProxy.andrGetSession(D_SESSION_SYNCYN);
		
		var currYmd = $.fn.getCurrDate();
		
		// 로그인정보를 초기화 한다.
		$.fn.setSessionClear();
		
		// 로그인정보를 기록한다.
		window.sharedProxy.andrSetSession(D_SESSION_USERID, loginId);
		// App에서 로그인정보를 사용하기 위해서 저장
		window.userProxy.andrSetLogin(loginId);
		// 로그인 성공 플래그 처리
		window.sharedProxy.andrSetSession(D_SESSION_LOGINYN, "Y");
		
		// 로그인 권한별 메뉴구성
		var sysGubun = window.sharedProxy.andrSPGetItem(S_LOGIN, "SYS_GUBUN");
		// 사용자별 데이터가 다르지 않기 때문에 사용자가 바뀔경우에는 기준정보 수신을 다시 받지 않게 한다.
		//if(sessionUserId != loginId || sessionSyncYn != "Y" || sessionYmd != currYmd) {
//		if(sessionSyncYn != "Y" || sessionYmd != currYmd) {
//			$.fn.goMenuPageRefresh(sysGubun);
//			
//			// 첫 페이지 지정 (권한이 다른 사용자로 변경하여 사용할 경우 첫 페이지가 맞지 않은 경우가 생겨서 추가함.)
//			$.fn.setFirstMenuPage(sysGubun);
//			// 자동동기화를 위한 파라미터
//			window.sharedProxy.andrHashAddItem(H_TMP, 0, "TMP_AUTOSYNC_FLAG", "AUTO");
//			// 동기화 실행
//			$.fn.goMenuList("mnu_inb100");
//			
//			
//			//window.nativeProxy.andrToastShow("동기화부분이 완료되면 여기 주석처리 해야 한다.", 0);
//			//// 동기화 여부 (동기화 완료 후 설정해야 한다.)
//			//window.sharedProxy.andrSetSession(D_SESSION_SYNCYN, "Y");
//			//window.sharedProxy.andrSetSession(D_SESSION_YMD, currYmd);
//
//			return;
//		}
//		$.fn.goMenuPageRefresh(sysGubun);

		// 로그인 시간 기록
		window.sharedProxy.andrSetSession(D_SESSION_YMD, currYmd);
		
		window.sharedProxy.andrSetSession(D_SESSION_SYNCYN, "Y");
		
		console.log("doSessionLogin - D_SESSION_YMD     : " + window.sharedProxy.andrGetSession(D_SESSION_YMD));
		console.log("doSessionLogin - D_SESSION_LOGINYN : " + window.sharedProxy.andrGetSession(D_SESSION_LOGINYN));
		
		// 로그인 권한별 첫 페이지 이동
//		$.fn.gotoFistMenuPage();
//		location.href = '../delivery/list.jsp';
		$.fn.goMenuList("일별 장애접수 현황 조회");
	},
	
	// [로그아웃 처리]
	doSessionLogout : function() {
		// 로그인상태일경우 서버에 로그아웃 처리를 한다.
		var sessionLoginYn = window.sharedProxy.andrGetSession(D_SESSION_LOGINYN);
		if (sessionLoginYn == "Y") {
			var sessionUserId  = window.sharedProxy.andrGetSession(D_SESSION_USERID);
			
			$.ajax({
				type : "POST",
				url : G_JSONP_URL_USER,
				data : "callback=abcd&serviceFlag="+G_SF_USER_LOGOUT+"&callback=abcd&userId="+sessionUserId,
				dataType : "jsonp",
				jsonp : "callback",
				crossDomain : true,
				success : function(data){
					// 해쉬 초기화
					window.sharedProxy.andrHashClearAllContent();
					
					// 2016.08.29[KDBAEK] 로그인 HASH 데이터 모두 삭제
					window.sharedProxy.andrSPClearContent(S_LOGIN);
				},
				error: function(request,status,error) {
				  
				}
			});
		}
		
		console.log("doSessionLogout");
		
		window.sharedProxy.andrSetSession(D_SESSION_LOGINYN, "N");
	},
	
	doSessionPageRefresh : function() {
		
	},
	
	// [동기화시 프로그램업데이트가 되었을때, 업데이트 후 로그인하였을때 자동으로 기준정보 수신을 받기위한 처리]
	//  - 로그인시간과 로그인여부를 초기화 한다.
	doSessionAutoSyncInit : function(loginId) {
		console.log("doSessionAutoSyncInit");
		
		window.sharedProxy.andrSetSession(D_SESSION_USERID  , loginId);
		window.sharedProxy.andrSetSession(D_SESSION_YMD     , "19990101");
		window.sharedProxy.andrSetSession(D_SESSION_LOGINYN , "N");
	},
	
	// [로그인 정보 모두 초기화]
	setSessionClear : function() {
		// 로그인 세션정보 모두 삭제
		window.sharedProxy.andrClearSession();
	},
	
	// [GET 로그인 아이디]
	getSessionUserId : function() {
		var sessionUserId  = window.sharedProxy.andrGetSession(D_SESSION_USERID);
		return sessionUserId;
	},
	
	// [동기화 성공 후 처리]
	//  - 동기화 성공 : Y
	//  - 동기화 성공시 현재시간 셋팅
	setSessionSyncYn : function(syncYn) {
		if(syncYn == "Y") {
			var currYmd = $.fn.getCurrDate();
			window.sharedProxy.andrSetSession(D_SESSION_YMD, currYmd);
		}
		window.sharedProxy.andrSetSession(D_SESSION_SYNCYN, syncYn);
	},
	
	// [메뉴간 이동할때 로그인시간 기준으로 일자가 변경됬는지를 체크한다.]
	//  - 리턴 true : 세션유지시간이 지났음. 로그아웃.
	//  - 실제로그아웃은 함수를 호출하는 부분에서 처리한다.
	getSessionTimeOutCheck : function() {
		var currYmd    = $.fn.getCurrDate();
		var sessionYmd = window.sharedProxy.andrGetSession(D_SESSION_YMD);
		
		if(sessionYmd != currYmd) {
			$.fn.doSessionLogout();
			window.nativeProxy.andrToastShow("로그인 유지기간이 지나서 로그아웃 합니다.\n마지막 로그인 일자 : [ " + sessionYmd + " ]", 0);
			return true;
		}
		return false;
	},
	
}); //END extend







