<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="../res/stylesheets/nwtns.css">
<link href="../res/css/reset.css" rel="stylesheet">
<link href="../res/css/menu.css" rel="stylesheet">
<link href="../res/css/content.css" rel="stylesheet">
<script src="../res/js/jquery.min.js"></script>
<script type="text/javascript" src="../res/js/def_variable.js"></script>
<script src="../res/js/common.js"></script>
<script type="text/javascript" src="../res/js/def_date.js"></script>
<script type="text/javascript" src="../res/js/def_string.js"></script>
<script type="text/javascript" src="../res/js/def_private.js"></script>
<script type="text/javascript" src="../res/js/android.js"></script>
<script src="../res/js/protocol.js"></script>
<style type="text/css">

.tableWrap{
	
}


table {
	border: 1px solid #ccc;
	border-collapse: collapse;
	margin: 0;
	padding: 0;
	width: 100%;
	table-layout: fixed;
	box-sizing: border-box;
}

table caption {
	font-size: 1.5em;
	margin: .5em 0 .75em;
}

table tr {
	background: #f8f8f8;
	border: 1px solid #ddd;
	padding: .35em;
}

table th {
	padding: .625em;
	text-align: center;
	border: 1px solid #ddd;
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(rgb(249, 249, 249)), color-stop(0.3, rgb(246, 246, 246)), color-stop(0.8, rgb(231, 234, 233)), to(rgb(221, 227, 228)));
}

table td {
	padding: .625em;
	text-align: center;
	border: 1px solid #ddd;
	background: #fff;
	min-height: 15px;
	font-size: 15px;
	text-overflow:ellipsis;
	white-space:nowrap;
	word-wrap:normal;
	overflow:hidden;
}

table th {
	font-size: .85em;
	letter-spacing: .1em;
	text-transform: uppercase;
	border: 1px solid #ddd;
}
#pagination{
    display: inline-block;
    background-color: #fff;
    position: absolute;
    left: 50%;
}
#pagination>li{
	display:inline;
}
#pagination>li>a{
	position: relative ;
    float: left ;
    padding: 6px 12px ;
    margin-left: -1px ;
    line-height: 1.42857143 ;
    color: #337ab7 ;
    text-decoration: none ;
    background-color: #fff ;
    border: 1px solid #ddd ;
}
@media screen and (max-width: 768px) {
	table {
		border: 0;
	}
	table caption {
		font-size: 1.3em;
	}
	table thead {
		border: none;
		clip: rect(0, 0, 0, 0);
		height: 1px;
		margin: -1px;
		overflow: hidden;
		padding: 0;
		position: absolute;
		width: 1px;
	}
	table tr {
		border-bottom: 3px solid #ddd;
		display: block;
		margin-bottom: .625em;
	}
	table td {
/* 		border-bottom: 1px solid #ddd; */
		border:none;
		display: block;
		font-size: .8em;
		text-align: right !important;
		text-overflow:ellipsis;
		white-space:nowrap;
		word-wrap:normal;
		overflow:hidden;
	}
	
	table td.nMobile {
		display: none;
	}
	table td.mobile:before {
		/*
    * aria-label has no advantage, it won't be read inside a table
    content: attr(aria-label);
    */
		content: attr(data-label);
		float: left;
		font-weight: bold;
		text-transform: uppercase;
	}
}
/* 태블릿용 CSS */
@media all and (max-width:960px){
	table {
		border: 0;
	}
	table caption {
		font-size: 1.3em;
	}
	table thead {
		border: none;
		clip: rect(0, 0, 0, 0);
		height: 1px;
		margin: -1px;
		overflow: hidden;
		padding: 0;
		position: absolute;
		width: 1px;
	}
	table tr {
		border-bottom: 3px solid #ddd;
		display: block;
		margin-bottom: .625em;
	}
	table td {
/* 		border-bottom: 1px solid #ddd; */
		border:none;
		display: block;
		font-size: .8em;
		text-align: right !important;
		text-overflow:ellipsis;
		white-space:nowrap;
		word-wrap:normal;
		overflow:hidden;
	}
	table td.nMobile {
	}
	table td.nTablet {
		display: none;
	}
	table td.tablet:before {
		/*
    * aria-label has no advantage, it won't be read inside a table
    content: attr(aria-label);
    */
		content: attr(data-label);
		float: left;
		font-weight: bold;
		text-transform: uppercase;
	}
	
}

/* PC용 CSS */
@media all and (min-width:20000px){
	table {
	border: 1px solid #ccc;
	border-collapse: collapse;
	margin: 0;
	padding: 0;
	width: 100%;
	table-layout: fixed;
	box-sizing: border-box;
}

table caption {
	font-size: 1.5em;
	margin: .5em 0 .75em;
}

table tr {
	background: #f8f8f8;
	border: 1px solid #ddd;
	padding: .35em;
}

table th {
	padding: .625em;
	text-align: center;
	border: 1px solid #ddd;
}

table td {
	padding: .625em;
	text-align: center;
	border: 1px solid #ddd;
	background: #fff;
	min-height: 15px;
	font-size: 15px;
	text-overflow:ellipsis;
	white-space:nowrap;
	word-wrap:normal;
	overflow:hidden;
}

table th {
	font-size: .85em;
	letter-spacing: .1em;
	text-transform: uppercase;
	border: 1px solid #ddd;
}

}
</style>
<title>일별 장애접수 현황 조회</title>
</head>
<body>
	<%@ include file="../res/menu.jsp"%>
	<div class='bodyContent'>
		<div class="searchArea">
			<div class="input-group">
				<span class="input-group-addon"> 일자 </span> 
				<input type="text"	class="form-control" id="ymd">
			</div>
			<div class="input-group">
				<span class="input-group-addon"> 고객사 </span> 
				<select	class="form-control" id="custCode" tag="고객사 선택">
					<option value="">고객사 선택</option>
				</select>
			</div>
			<div class="input-group">
				<span class="input-group-addon"> 사업부 </span> 
				<select	class="form-control" id="custDiv" tag="사업부 선택">
					<option value="">사업부 선택</option>
				</select>
			</div>

			<div class="input-group removeArea">
				<span class="input-group-addon"> 제조사 </span> 
				<select class="form-control" id="makerCode" tag="제조사">
					<option value="">제조사 선택</option>
				</select>
			</div>

			<div class="input-group removeArea">
				<span class="input-group-addon"> 제품군 </span> 
				<select class="form-control" id="groupCode" tag="제품군">
					<option value="">제품군 선택</option>
				</select>
			</div>

			<div class="input-group removeArea">
				<span class="input-group-addon"> 모델 </span> 
				<select class="form-control" id="modelCode" tag="사업부 선택">
					<option value="">모델 선택</option>
				</select>
			</div>

			<div class="input-group">
				<span class="input-group-addon"> 검색 </span> 
				<input type="text"	class="form-control" id="searchValue">
			</div>

			<div class="input-group">
				<span class="input-group-addon"> 조회형식 </span> <select
					class="form-control" id="sortTag" tag="조회형식 선택">
					<option value="">조회형식 선택</option>
					<option value="0">장비 선택</option>
					<option value="1">표 선택</option>
					<option value="2">그래프 선택</option>
				</select>
			</div>


			<div class="input-group">
				<button class="btn-default">액셀 다운로드</button>
				<button class="btn-default" onclick="$.fn.searchList();">조회</button>
			</div>
		</div>
			<div class="tableWrap" style="overflow: auto; max-height: 500px;">
			<table>
				<thead>
					<tr>
						<th scope="col" style="width: 5%;">No</th>
						<th scope="col" style="width: 5%;">입고</th>
						<th scope="col" style="width: 5%;">발송</th>
						<th scope="col" style="width: 10%;">제품번호(SN)</th>
						<th scope="col" style="width: 20%;">고객사</th>
						<th scope="col" style="width: 10%;">제품정보</th>
						<th scope="col" style="width: 5%;">진행상태</th>
						<th scope="col" style="width: 15%;">접수내역</th>
						<th scope="col" style="width: 15%;">조치내역</th>
						<th scope="col" style="width: 10%;">부품비</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			</div>
			<footer	style="width: 100%;height: 50px;margin-top:10px;">
				<ul id="pagination">
					<li><a>1</a></li>
					<li><a>2</a></li>
					<li><a>3</a></li>
					<li><a>4</a></li>
					<li><a>5</a></li>
				</ul>
			</footer>
	</div>
</body>
<script>
$(document).ready(function() {
	$.fn.pageInit();
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
	$(window).bind("resize", function() { // 화면 리사이즈 처리
		$.fn.resizeSearchArea();
	});
	
});
$(window).ready(function() {
});
$.fn.extend({	
	//페이지 초기화
	
	pageInit:function(){
		$.fn.resizeSearchArea();
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
	},
	
	searchList:function(){
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
					if(data.RES_MDATA.length > 0){
						
	    				$(data.RES_MDATA).each(function(index,item) {		
	    					html +='<tr>';
	    					html +='  <td data-label="No" class="mobile tablet tar">'+(Number(index)+1)+'</td>';
    						html +='  <td data-label="입고" class="nMobile nTablet tac">'+item.IN_YMD+'</td>';
   							html +='   <td data-label="발송"class="nMobile nTablet tac">'+item.EX_YMD+'</td>';
							html +='  <td data-label="제품번호" class="mobile tablet tal">'+item.PROD_CODE+'</td>';
							html +='  <td data-label="고객사"   class="mobile tablet tal">'+item.CUST_INFO+'</td>';
							html +='  <td data-label="제품정보" class="mobile tablet tal">'+item.MODEL_NAME+'</td>';
							html +='  <td data-label="진생상태" class="nMobile nTablet tac">'+item.STATUS_VALUE+'</td>';
							html +='  <td data-label="접수내역" class="nMobile tablet tal">'+item.TROUBLE_VALUE+'</td>';
							html +='  <td data-label="조치내역" class="nMobile tablet tal">'+item.REPAIR_VALUE+'</td>';
							html +='  <td data-label="부품비"   class="nMobile nTablet tar">'+$.fn.commaSeparateNumber(item.REPAIR_AMT)+'</td>';
							html +='</tr>';
	    					
// 						    html += "<div class='tbody'>";
// 							html += "<span class='col'>"+Number(index)+1+"</span>";
// 							html += "<span class='col'>"+item.IN_YMD+"</span>";
// 							html += "<span class='col tablet'>"+item.IN_YMD+"</span>";
// 							html += "<span class='col tablet'>"+item.PROD_CODE+"</span>";
// 							html += "<span class='col pc'>"+item.TROUBLE_VALUE+"</span>";
// 							html += "<span class='col pc'>"+item.STATUS_VALUE+"</span>";
// 							html += "<span class='col pc'>"+item.TROUBLE_VALUE+"</span>";
// 							html += "<span class='col pc'>"+item.TROUBLE_VALUE+"</span>";
// 							html += "<span class='col pc'>"+item.TROUBLE_VALUE+"</span>";
// 							html += "<span class='col pc'>"+item.TROUBLE_VALUE+"</span>";
// 							html += "</div>	";
	    				});
	    				$("tbody").html(html);
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
		if($(window).width()>960){
			$(".removeArea").show();
		}else{
			$(".removeArea").hide();
		}
	}
});
</script>
</html>