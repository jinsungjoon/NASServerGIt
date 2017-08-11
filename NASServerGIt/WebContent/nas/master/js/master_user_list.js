/**
 * 
 */
var dataList = "";
var preFlag  = "";
var currFlag  = "";
var currIdx = 0;

$(document).ready(function() {
	$("#pageTitleSpan").text("마스터 관리 > 사용자관리");
//	$.fn.hideLoading();
	$.fn.getRoleCodeSearch();
	$.fn.searchList();

	$("#btnSearch").click(function(){
		$.fn.searchList();
	});
	$(window).bind("resize", function() { // 화면 리사이즈 처리
		$.fn.resizeSearchArea();
	});
});

$(window).ready(function() {
});

$.fn.extend({
	getRoleCodeSearch:function(){
		$("#roleCode").empty();
		$("#roleCode").append("<option value=''>전체</option>");
		var data = {
				serviceFlag:G_SF_USER_LIST_INIT,
				callback:"abcd",
				userId:$("#menuUserid").val(),
				userName:$("#menuUserName").val(),
				CODE_TYPE:"ROLE_CODE",
				CODE_VALUE:"10",
		};
		$.ajax({
			global: false,
			timeout : 30000,
			type : "POST",
			url : G_JSONP_URL_CALL,
			data : data,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			success : function(data){
				if(data.RETURN_CODE=="200"){
	      			if(data.RES_DATA.length != 0) {
	    				$(data.RES_DATA).each(function(index,item) {
    						$("#roleCode").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
	    				});
	    			}
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
	
//	pageInit:function(){
//		$("#pageTitleSpan").text("마스터 관리 > 사용자관리");
//		$("#create_by").append("<option value=\""+ $("#menuUserId").val() +"\">"+ $("#menuUserName").val() +"</option>");
//		var data = {
//				serviceFlag:G_SF_USER_LIST_INIT,
//				callback:"abcd",
//				userId:$("#menuUserid").val(),
//				userName:$("#menuUserName").val(),
//		};
//		data.CODE_TYPE = "ROLE_CODE";
//		data.CODE_VALUE = "10";
//		
//		$.ajax({
//			global: false,
//			timeout : 30000,
//			type : "POST",
//			url : G_JSONP_URL_CALL,
//			data : data,
//			dataType : "jsonp",
//			jsonp : "callback",
//			crossDomain : true,
//			success : function(data){
//				if(data.RETURN_CODE=="200"){
//	      			if(data.RES_DATA.length != 0) {
//	    				$(data.RES_DATA).each(function(index,item) {
//    						$("#roleCode").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
//	    				});
//	    			}
//				}
//				//조회 실패
//				else{
//					alert(data.RETURN_MSG);
//				}
//			},
//			error: function(request,status,error) {
//				alert(error);
//			}
//		});
//		
//		$.fn.searchList(1);
//	},
	searchList:function(){
		var param = new Object();
			param.serviceFlag = MASTER_GETUSERLIST_V01;
			param.callback    = "abcd";
			param.userId      = $("#menuUserid").val();
			param.userName    = $("#menuUserName").val();
			param.roleCode    = $("#roleCode").val();//사용그룹
			param.useYn       = $("#useYn").val();//사용여부
			param.searchValue = encodeURIComponent($("#searchValue").val());//사용자검색
//			param.currPage    = index;
//			param.rowCnt      = m_nOnePageCnt;
		$.ajax({
			global: false,
			timeout : 30000,
			type : "POST",
			url : G_JSONP_URL_MASTER,
			data : param,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			success : function(data){
				if(data.RETURN_CODE=="200"){
					var html = "";
					dataList = data.DATA;
						preFlag = "";
						$.fn.drawTable();
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
	drawTable:function(){
//		$.fn.setTotalCount(dataList[0].TOTCNT);
		
		$(".theadCom").hide();
		if($(window).width()>960){
			currFlag = "PC";
			$(".removeArea").show();
			$("#theadPc").show();
		}else if( $(window).width() < 960 && $(window).width() > 767){
			currFlag = "TABLET";
			$(".removeArea").hide();
			$("#theadTablet").show();
		}else{
			currFlag = "MOBILE";
			$(".removeArea").hide();
			$("#theadMobile").show();
		}
		if(preFlag == currFlag){
			return;
		}
		$("#tbody").empty();
		var html = "";
		if(dataList.length > 0){
			$(dataList).each(function(index,item) {
				if($(window).width()>960){//PC
					html +='<tr>';
					html +='  <td data-label="사용자명" class="mobile tablet tar">'+item.USER_NAME+'</td>';
					html +='  <td data-label="로그인ID" class="nMobile nTablet tac">'+item.USER_ID+'</td>';
					html +='  <td data-label="비밀번호" class="nMobile nTablet tac">'+item.PASSWD+'</td>';
					html +='  <td data-label="사용그룹" class="mobile tablet tal">'+item.CODE_NAME1+'</td>';
					html +='  <td data-label="직급" class="mobile tablet tal">'+item.CODE_NAME2+'</td>';
					html +='  <td data-label="전화번호" class="mobile tablet tal">'+item.TEL_NO+'</td>';
					html +='  <td data-label="핸드폰번호" class="nMobile nTablet tac">'+item.MOBILE_NO+'</td>';
					html +='  <td data-label="이메일" class="nMobile tablet tal">'+item.EMAIL+'</td>';
					html +='  <td data-label="사용여부" class="nMobile tablet tal">'+item.USE_YN+'</td>';
					html +='</tr>';
				}else if( $(window).width() < 960 && $(window).width() > 767){//TABLET
					var i = Number(index)+1;   
					i = i%2;
					if(i==0){
						html +='<tr>';
						html +='  <td data-label="사용자명" class="mobile tablet tar" style="border-bottom:none">'+item.USER_NAME+'</td>';
						html +='  <td data-label="로그인ID" class="mobile tablet tal" style="border-bottom:none">'+item.USER_ID+'</td>';
						html +='  <td data-label="사용그룹" class="mobile tablet tal" style="border-bottom:none">'+item.CODE_NAME1+'</td>';
						html +='  <td data-label="직급" class="mobile tablet tal" style="border-bottom:none">'+item.CODE_NAME2+'</td>';
						html +='</tr>';
						html +='<tr>';
						html +='  <td data-label="핸드폰번호" class="nMobile nTablet tac" style="border-top:none">'+item.MOBILE_NO+'</td>';
						html +='  <td data-label="이메일" class="nMobile tablet tal" style="border-top:none">'+item.EMAIL+'</td>';
						html +='  <td data-label="사용여부" class="nMobile tablet tal" style="border-top:none">'+item.USE_YN+'</td>';
						html +='</tr>';
					}else{
						html +='<tr>';
						html +='  <td data-label="사용자명" class="mobile tablet tar" style="background-color:#dff0d8 !important">'+item.USER_NAME+'</td>';
						html +='  <td data-label="로그인ID" class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.USER_ID+'</td>';
						html +='  <td data-label="사용그룹"   class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.CODE_NAME1+'</td>';
						html +='  <td data-label="직급" class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.CODE_NAME2+'</td>';
						html +='</tr>';
						html +='<tr style="background-color:#dff0d8 !important">';
						html +='  <td data-label="핸드폰번호" class="nMobile nTablet tac" style="background-color:#dff0d8 !important">'+item.MOBILE_NO+'</td>';
						html +='  <td data-label="이메일" class="nMobile tablet tal" style="background-color:#dff0d8 !important">'+item.EMAIL+'</td>';
						html +='  <td data-label="사용여부" class="nMobile tablet tal" style="background-color:#dff0d8 !important">'+item.USE_YN+'</td>';
						html +='</tr>';
					}
				}else{
					var i = Number(index)+1;   
					i = i%2;
					if(i==0){
						html +='<tr style="background-color:#dff0d8 !important">';
						html +='  <td data-label="로그인ID" class="mobile tablet tar" style="border-bottom:none">'+item.USER_ID+'</td>';
						html +='</tr>';
						html +='<tr>';
						html +='  <td data-label="사용자명" class="mobile tablet tal" style="border-bottom:none">'+item.USER_NAME+'</td>';
						html +='  <td data-label="사용그룹"   class="mobile tablet tal" style="border-bottom:none">'+item.CODE_NAME1+'</td>';
						html +='  <td data-label="직급" class="nMobile nTablet tac" style="border-top:none" colspan="3">'+item.CODE_NAME2+'</td>';
						html +='</tr>';
					}else{
						html +='<tr style="background-color:#dff0d8 !important">';
						html +='  <td data-label="로그인ID" class="mobile tablet tar" style="background-color:#dff0d8 !important">'+item.USER_ID+'</td>';
						html +='</tr>';
						html +='<tr>';
						html +='  <td data-label="사용자명" class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.USER_NAME+'</td>';
						html +='  <td data-label="사용그룹" class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.CODE_NAME1+'</td>';
						html +='  <td data-label="직급" class="nMobile nTablet tac" style="background-color:#dff0d8 !important" colspan="3">'+item.CODE_NAME2+'</td>';
						html +='</tr>';
					}
				}
			});
			$("#tbody").html(html);
//			$("#pagination").css("display","flex");
		}else{
//			$("#pagination").css("display","none");
		}
//		$.fn.drawNavi(index);
	},
	resizeSearchArea:function(){
		$.fn.drawTable();
	}
	
});