var dataList = "";
var preFlag  = "";
var currFlag  = "";
var currIdx = 0;

$(document).ready(function() {
	$.fn.pageInit();
	$.fn.calInit();
	//고객사 변경시 사업부 조회
	$("#custCode").change(function(){
		$.fn.getCustDivSearch();
	});
	$(".thead").click(function(){
		if($(".thead").eq($(this).index()).hasClass("active")){
			$(".thead").eq($(this).index()).removeClass("active");
			$(".thead").eq($(this).index()).find(".tablet").css({"display":"none"});
			$(".thead").eq($(this).index()).find(".pc").css({"display":"none"});
		}else{
			$(".thead").eq($(this).index()).addClass("active");
			$(".thead").eq($(this).index()).find("span").css({"width":"50%","display":"flex"});
		}
	});
	$(document).on('click',".tbody" , function(){
		if($(".tbody").eq($(this).index()).hasClass("active")){
			$(".tbody").eq($(this).index()).removeClass("active");
			$(".tbody").eq($(this).index()).find(".tablet").css({"display":"none"});
			$(".tbody").eq($(this).index()).find(".pc").css({"display":"none"});
		}else{
			$(".tbody").eq($(this).index()).addClass("active");
			$(".tbody").eq($(this).index()).find("span").css({"width":"50%","display":"flex"});
		}
	});
	$("#btnSearch").click(function(){
		$.fn.searchList(1);
	});
	$(window).bind("resize", function() { // 화면 리사이즈 처리
		$.fn.resizeSearchArea();
	});
	
});
$(window).ready(function() {
});
$.fn.extend({	
	//페이지 초기화
	
	pageInit:function(){
		$("#pageTitleSpan").text("일별 장애접수 현황 조회");
		//$.fn.resizeSearchArea();
		$("#create_by").append("<option value=\""+ $("#menuUserId").val() +"\">"+ $("#menuUserName").val() +"</option>");
		var data = {
				serviceFlag:G_SF_CALL_PAGE_INIT,
				callback:"abcd",
				userId:$("#menuUserid").val(),
				userName:$("#menuUserName").val(),
		};
			data.REPAIR_IN_TAG = "REPAIR_IN_TAG";
			data.PARCEL_SERVICE= "PARCEL_SERVICE";
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
	      			if(data.REPAIR_IN_TAG.length != 0) {
	    				$(data.REPAIR_IN_TAG).each(function(index,item) {
    						$("#REPAIR_IN_TAG").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
	    				});
	    			}
	      			if(data.PARCEL_SERVICE.length != 0) {
	    				$(data.PARCEL_SERVICE).each(function(index,item) {
    						$("#PARCEL_SERVICE").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
	    				});
	    			}
	      			if(data.CUST_MASTER.length != 0) {
	    				$(data.CUST_MASTER).each(function(index,item) {
    						$("#custCode").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
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
		
		$.fn.searchList(1);
	},
	searchList:function(index){
		//alert(index);
		var param = new Object();
			param.serviceFlag = G_SF_RPT_DDLIST_V01;
			param.callback    = "abcd";
			param.userId      = $("#menuUserid").val();
			param.userName    = $("#menuUserName").val();
			param.ymd         = $("#ymd").val();
			param.ym          = "";
			param.custCode    = $("#custCode").val();    //고객사
			param.custDiv     = $("#custDiv").val();     //사업부
			param.makerCode   = $("#makerCode").val();   //제조사
			param.groupCode   = $("#groupCode").val();   //제품군
			param.modelCode   = $("#modelCode").val();   //모델
			param.searchValue = $("#searchValue").val(); //검색조건
			param.sortTag     = $("#sortTag").val();     //정렬
			param.currPage    = index;
			param.rowCnt      = m_nOnePageCnt;
		$.ajax({
			global: false,
			timeout : 30000,
			type : "POST",
			url : G_JSONP_URL_RPT,
			data : param,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			success : function(data){
				if(data.RETURN_CODE=="200"){
					var html = "";
					dataList = data.RES_MDATA;
						preFlag = "";
						$.fn.drawTable(index);
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
	//고객사-사업부
	getCustDivSearch:function(){
		$("#custDiv").empty();
		$("#custDiv").append("<option value=''>사업부 선택</option>");
		var data = {
				serviceFlag:G_SF_CUST_DIV,
				callback:"abcd",
				userId:$("#menuUserid").val(),
				userName:$("#menuUserName").val(),
				CUST_CODE:$("#custCode").val(),
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
    						$("#custDiv").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
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
	//고객사-사업부-영업조직
	getCustDeptSearch:function(){
		$("#CUST_MASTER4").empty();
		$("#CUST_MASTER4").append("<option value=''>영업조직 선택</option>");
		var data = {
				serviceFlag:G_SF_CUST_DEPT,
				callback:"abcd",
				userId:$("#menuUserid").val(),
				userName:$("#menuUserName").val(),
				CUST_CODE:$("#CUST_MASTER").val(),
				CUST_DIV:$("#CUST_MASTER2").val(),
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
    						$("#CUST_MASTER4").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
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
	resizeSearchArea:function(){
		$.fn.drawTable(0);
	}
	,drawTable:function(index){
		
		
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
			$.fn.setTotalCount(dataList[0].TOTCNT);
			$(dataList).each(function(index,item) {
				if($(window).width()>960){//PC
					html +='<tr>';
					html +='  <td data-label="No" class="mobile tablet tar">'+Number(item.RNUM)+'</td>';
					html +='  <td data-label="입고" class="nMobile nTablet tac">'+item.IN_YMD+'</td>';
					html +='  <td data-label="발송"class="nMobile nTablet tac">'+item.EX_YMD+'</td>';
					html +='  <td data-label="제품번호" class="mobile tablet tal">'+item.PROD_CODE+'</td>';
					html +='  <td data-label="고객사"   class="mobile tablet tal">'+item.CUST_INFO+'</td>';
					html +='  <td data-label="제품정보" class="mobile tablet tal">'+item.MODEL_NAME+'</td>';
					html +='  <td data-label="진생상태" class="nMobile nTablet tac">'+item.STATUS_VALUE+'</td>';
					html +='  <td data-label="접수내역" class="nMobile tablet tal">'+item.TROUBLE_VALUE+'</td>';
					html +='  <td data-label="조치내역" class="nMobile tablet tal">'+item.REPAIR_VALUE+'</td>';
					html +='  <td data-label="부품비"   class="nMobile nTablet tar">'+$.fn.commaSeparateNumber(item.REPAIR_AMT)+'</td>';
					html +='</tr>';
				}else if( $(window).width() < 960 && $(window).width() > 767){//TABLET
					var i = Number(index)+1;   
					i = i%2;
					if(i==0){
						html +='<tr>';
						html +='  <td data-label="No" class="mobile tablet tar" style="border-bottom:none">'+Number(item.RNUM)+'</td>';
						html +='  <td data-label="제품번호" class="mobile tablet tal" style="border-bottom:none">'+item.PROD_CODE+'</td>';
						html +='  <td data-label="고객사"   class="mobile tablet tal" style="border-bottom:none">'+item.CUST_INFO+'</td>';
						html +='  <td data-label="제품정보" class="mobile tablet tal" style="border-bottom:none">'+item.MODEL_NAME+'</td></tr>';
						html +='  <tr><td data-label="진생상태" class="nMobile nTablet tac" style="border-top:none">'+item.STATUS_VALUE+'</td>';
						html +='  <td data-label="접수내역" class="nMobile tablet tal" style="border-top:none">'+item.TROUBLE_VALUE+'</td>';
						html +='  <td data-label="조치내역" class="nMobile tablet tal" style="border-top:none">'+item.REPAIR_VALUE+'</td>';
						html +='  <td data-label="부품비"   class="nMobile nTablet tar" style="border-top:none">'+$.fn.commaSeparateNumber(item.REPAIR_AMT)+'</td>';
						html +='</tr>';
					}else{
						html +='<tr>';
						html +='  <td data-label="No" class="mobile tablet tar" style="background-color:#dff0d8 !important">'+Number(item.RNUM)+'</td>';
						html +='  <td data-label="제품번호" class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.PROD_CODE+'</td>';
						html +='  <td data-label="고객사"   class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.CUST_INFO+'</td>';
						html +='  <td data-label="제품정보" class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.MODEL_NAME+'</td></tr>';
						html +='  <tr style="background-color:#dff0d8 !important"><td data-label="진생상태" class="nMobile nTablet tac" style="background-color:#dff0d8 !important">'+item.STATUS_VALUE+'</td>';
						html +='  <td data-label="접수내역" class="nMobile tablet tal" style="background-color:#dff0d8 !important">'+item.TROUBLE_VALUE+'</td>';
						html +='  <td data-label="조치내역" class="nMobile tablet tal" style="background-color:#dff0d8 !important">'+item.REPAIR_VALUE+'</td>';
						html +='  <td data-label="부품비"   class="nMobile nTablet tar" style="background-color:#dff0d8 !important">'+$.fn.commaSeparateNumber(item.REPAIR_AMT)+'</td>';
						html +='</tr>';
					}
				}else{
					var i = Number(index)+1;   
					i = i%2;
					if(i==0){
						html +='<tr>';
						html +='  <td data-label="No" class="mobile tablet tar" style="border-bottom:none">'+Number(item.RNUM)+'</td>';
						html +='  <td data-label="제품번호" class="mobile tablet tal" style="border-bottom:none">'+item.PROD_CODE+'</td>';
						html +='  <td data-label="고객사"   class="mobile tablet tal" style="border-bottom:none">'+item.MODEL_NAME+'</td></tr>';
						html +='  <tr><td data-label="진생상태" class="nMobile nTablet tac" style="border-top:none" colspan="3">'+item.STATUS_VALUE+'</td>';
						html +='</tr>';
					}else{
						html +='<tr>';
						html +='  <td data-label="No" class="mobile tablet tar" style="background-color:#dff0d8 !important">'+Number(item.RNUM)+'</td>';
						html +='  <td data-label="제품번호" class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.PROD_CODE+'</td>';
						html +='  <td data-label="제품정보" class="mobile tablet tal" style="background-color:#dff0d8 !important">'+item.MODEL_NAME+'</td></tr>';
						html +='  <tr style="background-color:#dff0d8 !important"><td data-label="진생상태" class="nMobile nTablet tac" style="background-color:#dff0d8 !important" colspan="3">'+item.STATUS_VALUE+'</td>';
						html +='</tr>';
					}
				}
			});
			$("#tbody").html(html);
			$("#pagination").css("display","flex");
		}else{
			$("#pagination").css("display","none");
		}
		$.fn.drawNavi(index);
	},
});