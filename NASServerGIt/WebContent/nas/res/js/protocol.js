/**
 * 
 */
var G_MOBILE_FLAG = true;
	G_MOBILE_FLAG = isMobile();
var G_DEVICEID = "";
var G_OS_FLAG  = "";
if(isMobile()){
	G_DEVICEID = window.userProxy.andrGetDeviceID();
	G_OS_FLAG = window.userProxy.andrGetOSFlag();
}else{
	G_DEVICEID = browserCheck();
}
var G_JSONP_URL_USER                = "/ASM_WEB/nas/user.asm?deviceId=" + G_DEVICEID + "&osFlag=" + G_OS_FLAG;
var G_SF_USER_LOGIN 				= "USER_LOGIN_V01";					// 로그인


var G_JSONP_URL_CALL       			= "/ASM_WEB/nas/call.asm?deviceId=" + G_DEVICEID + "&osFlag=" + G_OS_FLAG;
var G_SF_CALL_PAGE_INIT 			= "CALL_PAGE_INIT_V01";				// 장애접수 패이지 초기화
var G_SF_CUST_DIV     			    = "CUST_DIV_V01";				    // 고객사-사업부
var G_SF_CUST_DEPT      			= "CUST_DEPT_V01";				    // 고객사-사업부-영업조직
var G_SF_USER_LIST_INIT             = "USER_LIST_INIT_V01";             // 사용자 관리 페이지 초기화


var G_JSONP_URL_RPT                 = "/ASM_WEB/nas/rpt.asm?deviceId=" + G_DEVICEID + "&osFlag=" + G_OS_FLAG;
var G_SF_RPT_DDLIST_V01      		= "RPT_DDLIST_V01";				    // 일별 장애접수 현황


var G_JSONP_URL_MASTER              = "/ASM_WEB/nas/master.asm?deviceId=" + G_DEVICEID + "&osFlag=" + G_OS_FLAG;
var MASTER_GETUSERLIST_V01          = "MASTER_GETUSERLIST_V01";         // 사용자목록 조회
var MASTER_GETVERSION_V01      		= "MASTER_GETVERSION_V01";			// 버전정보 조회
var MASTER_SETVERSION_V01      		= "MASTER_SETVERSION_V01";			// 버전정보 변경

