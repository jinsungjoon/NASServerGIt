/* ********************************************************************************
 * BY 최정주
 * 1. 안드로이드 웹뷰와 데이터 통신을 처리하기 위한 스크립트 모음
 * 2.Webview Interface 정버
 * 		nweb.setScriptInterface(new JS_DefaultInterface(), "jsDefault");
		nweb.setScriptInterface(new JS_UserInterface(ACT_Webview.this, nweb), "userProxy");
		nweb.setScriptInterface(new JS_WidgetInterface(ACT_Webview.this, nweb), "jsWidget");
		nweb.setScriptInterface(new JS_MasterInterface(ACT_Webview.this, nweb), "jsMaster");
 * [2013-11-26] 최초작성 - 최정주
 * ******************************************************************************** */
$(document).ready(function() {
});

$.extend({
	// ----------------------------------------------------------
	// 1.안드로이드 Webview에서 호출하는 함수 / 2.해당 Element에 값을 세팅한다.
	andrResult : function (elementId, data, callbackFunction){

		// elementId가 있을 경우 값세팅
		if(elementId != null && elementId != "" && document.getElementById(elementId) != null) {
			$("#" + elementId).val(data);
		}

		// 추가 작업 필요시 callback함수 활용
	    if ($.isFunction(callbackFunction)){
	    	callbackFunction.call(this, data);
	    }

	},

	// ----------------------------------------------------------
	// 1.안드로이드 Webview에서 호출하는 함수 / 2.alert을 띄운다.
	andrResultMsg : function (msg){
		alert(msg);
	},


	// ----------------------------------------------------------
	// 사진첨부하기
	andrSelectPicture : function (div_preview, el_picture_path, type){
		window.nativeProxy.andrSelectPicture(div_preview, el_picture_path, type);
	},

	// 사진미리보기
	setPicturePreview : function (div_preview_id, el_picture_path_id, sdcardPath){
		console.log("setPicturePreview              start");
		console.log(div_preview_id);
		console.log(el_picture_path_id);

		if("IMG_DELETE" == sdcardPath){	// 첨부한 파일 삭제
			$("#"+el_picture_path_id).val("");
			$("#"+div_preview_id).css("display","none");
			$("#"+div_preview_id).html("");
		}else{
			console.log("setPicturePreview          ===== else =====");
			console.log("div_preview_id     "+div_preview_id);
			console.log("el_picture_path_id     "+el_picture_path_id);
//			$.fn.setPicturePreviewUrlapv211(sdcardPath);
			$("#"+el_picture_path_id).val(sdcardPath);
			$("#"+div_preview_id).css("display","block");
			$("#"+div_preview_id).html("<img src='file://"+sdcardPath+"' />");

		}
	},
	setPicturePreviewUrl: function (div_preview_id, el_picture_path_id, sUrl){
		$("#"+el_picture_path_id).val(sdcardPath);
		$("#"+div_preview_id).css("display","block");
		$("#"+div_preview_id).html("<img src='"+sUrl+"' />");
	},
	// 첨부파일 초기화
	andrClearTempFile : function (){
		window.jsWidget.andrClearTempFile();
	},

	// 사진 업로드
	andrFileUpload : function (el_picture_path, upload_type){
		var sdcardPath = $("#"+el_picture_path).val();

		if("" != sdcardPath){
			window.jsWidget.andrFileUpload(sdcardPath, upload_type);
		}else{
			alert("파일을 선택해주세요.");
		}
	},

	//----------------------------------------------------------------------------------------
	// Toast 메세지 출력
	andrToastShow : function (msg, length){
		window.nativeProxy.andrToastShow(msg, length);
	},
	//----------------------------------------------------------------------------------------
	// 로딩중 처리
	andrProgressShow : function (){
		window.widgetProxy.andrProgressShow();
	},
	andrProgressShowMsg : function (msg){
		window.widgetProxy.andrProgressShowMsg(msg);
	},
	andrProgressHide : function (){
		window.widgetProxy.andrProgressHide();
	},

	// ----------------------------------------------------------
	// 사인하기
	andrSign : function (preview_id, element_id){
		window.widgetProxy.andrSign(preview_id, element_id);
	},

	andrSign2 : function (preview_id, element_id, file_name){
		window.widgetProxy.andrSign2(preview_id, element_id, file_name);
	},

	andrReadFileStream : function (path, filename){
		return window.widgetProxy.andrReadFileStream(path, filename);
	},

	// ----------------------------------------------------------
	// 바코드 스캔
	andrBarcodeScan : function (id){
		window.widgetProxy.andrBarcodeScan(id);
	},

	andrBarcodeScan : function (id, callbackEvent){
		window.widgetProxy.andrBarcodeScan(id, callbackEvent);
	},

	// ----------------------------------------------------------
	// 네트워크 상태 체크
	andrisNetwork : function (){
		return window.widgetProxy.andrisNetwork();
	},

	// ----------------------------------------------------------
	// 키보드 엔터 버튼 클릭시 next tabindex 호출
	OnFocusOut : function (currentElementId){
		var nextCnt = 1;
		while(true) {
			var ctl  = $(":input:not(:hidden)")[$(":input:not(:hidden)").index(document.activeElement) + nextCnt];
			console.log(ctl);
			if(ctl == null) {
				$(":input")[$(":input").index(document.activeElement)].blur();
				break;
			}
			if(ctl.type == 'number') {
				if($(ctl).attr("disabled")=="disabled"){//input disable check
					ctl  = $(":input:not(:hidden)")[$(":input:not(:hidden)").index(document.activeElement) + nextCnt+1];
//					break;
				}
				ctl.focus();
				break;
			}
			nextCnt++;
		}

		/**
		//$(":input:not(:hidden)")[$(":input:not(:hidden)").index(document.activeElement) + 1].focus();
		var ctl = $(":input:not(:hidden)")[$(":input:not(:hidden)").index(document.activeElement) + 1];
		if(ctl != null)
			ctl.focus();
		**/

		return false;
	},

	// ----------------------------------------------------------
	// 가상키보드 SHOW
//	andrShowNumberPad : function (focusElement){
//		console.log("id:"+$(focusElement).attr('id')+" "+"value:"+$(focusElement).val());
//		$(focusElement).css("border","1px solid red");
//		$(focusElement).css("background-color","#f6efa5");
//		window.vkeyProxy.andrShowNumberPad($(focusElement).attr('id'));
//	},
	andrShowNumberPad : function (focusElement, callbackEvent){
		if(callbackEvent == undefined)
			callbackEvent = "";
		console.log("id:"+$(focusElement).attr('id')+" "+"value:"+$(focusElement).val());
		$(focusElement).css("border","1px solid red");
		$(focusElement).css("background-color","#f6efa5");
		window.vkeyProxy.andrShowNumberPad($(focusElement).attr('id'), callbackEvent);
	},
	andrVisibleNumberPad : function (focusElement, callbackEvent, callbackVisibleEvent){
		$(focusElement).css("border", "1px solid red");
		$(focusElement).css("background-color","#f6efa5");
		$.elementPositionAnim(focusElement);
		window.vkeyProxy.andrVisibleNumberPad($(focusElement).attr('id'), callbackEvent, callbackVisibleEvent);
	},

	// InputText의 색상만 반전시킨다.
	andrHideNumberRevert : function (blurElement){
		$(blurElement).css("border","1px solid #ccc");
		$(blurElement).css("background-color","#fff");
		window.vkeyProxy.andrHideNumberRevert($(blurElement).attr('id'));
	},
	// 가상키보드 HIDDEN
	andrHideNumberPad : function (blurElement){
		$(blurElement).css("border","1px solid #ccc");
		$(blurElement).css("background-color","#fff");
		window.vkeyProxy.andrHideNumberPad($(blurElement).attr('id'));
	},
//	// by cjj 20140306 가상키보드 열때 webview height 조정을 위한 percent 파라미터 추
//	andrShowNumberPad1 : function (focusElement, per, bool_line){
//		$(focusElement).css("border",bool_line+"px solid red");
//		$(focusElement).css("background-color","#f6efa5");
//		$.elementPositionAnim(focusElement);
//		window.jsWidget.andrShowNumberPad1($(focusElement).attr('id'), per);
//	},
//	// by cjj 20140306 가상키보드 열때 webview height 조정을 위한 percent 파라미터 추
//	andrShowNumberPad2 : function (focusElement, callbackEvent, per, bool_line){
//		$(focusElement).css("border",bool_line+"px solid red");
//		$(focusElement).css("background-color","#f6efa5");
//		$.elementPositionAnim(focusElement);
//		window.jsWidget.andrShowNumberPad2($(focusElement).attr('id'), callbackEvent, per);
//	},
//	// 가상키보드 HIDDEN
//	andrHideNumberPad : function (blurElement){
//		$(blurElement).css("border","1px solid #ccc");
//		$(blurElement).css("background-color","#fff");
//		window.jsWidget.andrHideNumberPad($(blurElement).attr('id'));
//	},
//	// 가상키보드 HIDDEN
//	andrHideNumberPad : function (blurElement){
//		$(blurElement).css("border","1px solid #ccc");
//		$(blurElement).css("background-color","#fff");
//		window.jsWidget.andrHideNumberPad($(blurElement).attr('id'));
//	},
//	// by cjj 20140306 가상키 보드 HIDDEN
//	andrHideNumberPad1 : function (blurElement, bool_line){
//		$(blurElement).css("border",bool_line+"px solid #ccc");
//		if(bool_line > 0 )$(blurElement).css("background-color","#fff");
//		window.jsWidget.andrHideNumberPad($(blurElement).attr('id'));
//
//	},
//	// by cjj 20140306 가상키 보드 HIDDEN
//	andrHideNumberPad2 : function (blurElement, bool_line){
//		$(blurElement).css("border",bool_line+"px solid #ccc");
//		if(bool_line > 0 )$(blurElement).css("background-color","#fff");
//		window.jsWidget.andrHideNumberPad2($(blurElement).attr('id'));
//
//	},
	// 해당 엘리먼트 위치로 이동
	elementPositionAnim  : function(obj){
		/**
		if(obj != null){
			var val = $(obj).offset();
			$('body,html').animate({scrollTop:val.top-165},100);
		}
		*/
	},


	// 가상키보드 값제거
	andrSetDelete : function (elementId, val){
		$("#"+elementId).val(val);
	},

	// 가상키보드 값세팅
	andrSetNumber : function (elementId, val){

		console.log("아이디 값 : " + elementId);
		var valCnt = val.length;
		var maxlength = $("#"+elementId).attr("maxlength");

		if($("#"+elementId).attr("maxlength") == null){
			maxlength = 50;
		}

		if(valCnt <= maxlength){
			$("#"+elementId).val(val);
		}else{
			$(elementId).css("border","1px solid #ccc");
			window.vkeyProxy.andrLastValue($("#"+elementId).val());
		}
	},
	// ----------------------------------------------------------
//	// 로그인 아이디/날짜/로그인여부 app에 저장
//	andrSetLogin : function (elementId, userId){
//		window.userProxy.andrSetLogin(elementId, userId);
//	},

	// ----------------------------------------------------------
//	// 로그아웃 - 로그인 여부를 N으로 변경
//	andrLogout : function (){
//		window.userProxy.andrLogout();
//	},

	// 마스터정보 수신
	andrSyncServer : function (arrString, callbackFunction){
		window.dbProxy.andrSyncServer(arrString, callbackFunction);
	},

	// ----------------------------------------------------------
	// 안드로이드 datePicker를 호출한다
	showDatePickerDialog : function (element, type, callBackFunction){

		var id = $(element).attr("id");
		var val = $("#"+id).val();

		if(val == null || val ==""){
			val = $.fn.getCurrentDateTime(type);
		}
		window.widgetProxy.showDatePickerDialog(id, val, type, callBackFunction);
	},


//	// 로그인아이디 반환
//	andrLoginUserId : function (elementId, callbackFunction){
//		window.userProxy.andrLoginUserId(elementId);
//	},

	andrEncriptPasswd : function(passwd, callbackFunction) {
		window.encProxy.andrEncriptPasswd(passwd, callbackFunction);
	},

//	andrLoginSetData : function(emp_no) {
//		window.userProxy.andrLoginSetData(emp_no);
//	},


	// ----------------------------------------------------------
	//  트랜젝션 처리
	andrBeginTransaction : function (){
		return window.dbProxy.andrBeginTransaction();
		console.log("1. andrBeginTransaction ========================================== ");
	},
	andrCommit : function (){
		return window.dbProxy.andrCommit();
		console.log("2. andrCommit ========================================== ");
	},
	andrEndTransaction : function (){
		return window.dbProxy.andrEndTransaction();
		console.log("3. andrEndTransaction ========================================== ");
	},

	// ----------------------------------------------------------
	// 로그출력
	logd : function (msg){
		if(true) console.log(msg);
	},

	andrGetDeviceID: function() {
		return window.userProxy.andrGetDeviceID();
	},

	andrGetPhoneNo: function() {
		return window.userProxy.andrGetPhoneNo();
	},

	hideMsgBox:function() {
		if ($("#id-containLayer-msg").css("display") == "block") {
			$("#id-containLayer-msg").css("display", "none");
		} else {
			if ($(".white-popup").css("display") == "block") {
				try { $.magnificPopup.close(); } catch(e){}
			}
		}
	},

	sendMsgBoxStatus: function() {
		window.sharedProxy.andrHashClearContent("h_msg");
		
		//레이어버튼창이 떠있는지 확인
		var msgDisplay = $("#id-containLayer-msg").css("display");
		
		if (msgDisplay != "block") {
			//레이어 팝업창이 떠있는지 확인
			msgDisplay = $(".white-popup").css("display");
		}
		window.sharedProxy.andrHashAddItem("h_msg", 0, "MSG_DISPLAY", msgDisplay);
	},
	
	andrKeyboardHide_MenuClick : function() {
		$("*:focus").blur();
	}

}); //END extend