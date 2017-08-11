<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="../res/stylesheets/nwtns.css">
<link href="../res/css/reset.css" rel="stylesheet">
<link href="../res/css/menu.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../res/stylesheets/content.css">
<script src="../res/js/jquery.min.js"></script>
<script src="../res/js/common.js"></script>
<script type="text/javascript" src="../res/js/def_variable.js"></script>
<script type="text/javascript" src="../res/js/def_date.js"></script>
<script type="text/javascript" src="../res/js/def_string.js"></script>
<script type="text/javascript" src="../res/js/def_private.js"></script>
<script type="text/javascript" src="../res/js/android.js"></script>
<script src="../res/js/protocol.js"></script>
<style>
	.table{
		box-sizing:conten;
		display: flex;
		flex-flow:row wrap;
		justify-content:center;
		width: 100%;
		border-collapse:collapse; 
	}
	.table > .thead{
		display: flex;
		flex-flow:row wrap;
		justify-content:flex-start;
		width: 100%;
		border-collapse:collapse; 
		background-color: #f5f5f5;
		border: 1px solid #ddd;
	}
	.table > .thead > span{
		font-size: 15px;
		font-weight: bold;
		padding: 5px;
		width: 33%
	}
	.table > .thead > span:FIRST-CHILD{
		display: flex;
		justify-content:center;
		border-left: 1px solid #ddd;
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		border-collapse:collapse; 
		background-color: #f5f5f5
	}
	.table > .thead > span:last-CHILD{
		display: flex;
		justify-content:center;
		border-top: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		border-collapse:collapse; 
		background-color: #f5f5f5
	}
	.table > .thead > span{
		display: flex;
		justify-content:center;
		border-left: 1px solid #ddd;
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		border-collapse:collapse; 
		background-color: #f5f5f5;
		box-sizing: border-box;
		
	}
	.table > .thead > span:nth-child(3n){
		display: flex;
		justify-content:center;
		border-left: 1px solid #ddd;
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		border-collapse:collapse; 
		background-color: #f5f5f5;
		box-sizing: border-box;
		width: 34%;
		
	}
	/* ÅÂºí¸´¿ë CSS */
@media all and (min-width:768px){
	.table > .thead > span{
		font-size: 15px;
		font-weight: bold;
		padding: 5px;
		width: 25%;
		box-sizing: border-box;
	}
	.table > .thead > span:nth-child(3n){
		display: flex;
		justify-content:center;
		border-left: 1px solid #ddd;
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		border-collapse:collapse; 
		background-color: #f5f5f5;
		box-sizing: border-box;
		width: 25%;
	}
}
/* PC¿ë CSS */
@media all and (min-width:960px){
	.table > .thead > span{
		font-size: 15px;
		font-weight: bold;
		padding: 5px;
		width: 10%;
		box-sizing: border-box;
	}
	.table > .thead > span:nth-child(3n){
		display: flex;
		justify-content:center;
		border-left: 1px solid #ddd;
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		border-collapse:collapse; 
		background-color: #f5f5f5;
		box-sizing: border-box;
		width: 10%;
	}
}
</style>
<title>Insert title here</title>
</head>
<body>
<%@ include file="../res/menu.jsp"%>
<div class='bodyContent'>
	<div class="searchArea">
		<div class="input-group">
			<span class="input-group-addon" >
			접수일자
			</span>
			<input type="text" class="form-control" >
		</div>
		<div class="input-group" >
			<span class="input-group-addon" >
			접수자
			</span>
			<select class="form-control" id="create_by" tag="접수자 선택">
				<option value="">접수자 선택</option>
			</select>
		</div>
		<div class="input-group" >
			<span class="input-group-addon" >
			접수유형
			</span>
			<select class="form-control" id="REPAIR_IN_TAG" tag="접수유형">
				<option value="">접수유형 선택</option>
			</select>
		</div>
		<div class="input-group" >
			<span class="input-group-addon" >
			택배사
			</span>
			<select class="form-control" id="PARCEL_SERVICE" tag="택배사">
				<option value="">택배사 선택</option>
			</select>
		</div>
		<div class="input-group" >
			<span class="input-group-addon" >
			송장번호
			</span>
			<input type="text" class="form-control" >
		</div>
		<div class="input-group" >
			<span class="input-group-addon" >
			연락처
			</span>
			<input type="text" class="form-control" >
		</div>
		
		<div class="input-group " >
			<span class="input-group-addon" >
			임시연락처
			</span>
			<input type="text" class="form-control" >
		</div>
		
		
		<div class="input-group" >
			<span class="input-group-addon" >
			장비수량
			</span>
			<input type="text" class="form-control" >
		</div>
		
		
		
		
		<div class="input-group" >
			<span class="input-group-addon" style="width: 100%;border-right: 1px solid #ccc">
			고객정보
			</span>
		</div>
		<div class="input-group" >
			<select class="form-control" id="CUST_MASTER" tag="고객사 선택">
				<option value="">고객사 선택</option>
			</select>
			<select class="form-control" id="CUST_MASTER2" tag="사업부 선택">
				<option value="">사업부 선택</option>
			</select>
		</div>
		<div class="input-group" >
			<select class="form-control" id="CUST_MASTER3" tag="프로젝트 선택">
				<option value="">프로젝트 선택</option>
			</select>
			<select class="form-control" id="CUST_MASTER4" tag="영업조직 선택">
				<option value="">영업조직 선택</option>
			</select>
		</div>
		
		<div class="input-group" >
			<button class="btn-default" >저장</button>
		</div>
	</div>
	
	<div class="table">
		<div class='thead'>
			<span class='col'>col1</span>
			<span class='col'>col1</span>
			<span class='col'>col1</span>
			<span class='col'>col1</span>
			<span class='col'>col1</span>
			<span class='col'>col1</span>
			<span class='col'>col1</span>
			<span class='col'>col1</span>
			<span class='col'>col1</span>
			<span class='col'>col1</span>
		</div>
	</div>
</div>
</body>
<script>
$(document).ready(function() {
	$.fn.pageInit();
	//고객사 변경시 사업부 조회
	$("#CUST_MASTER").change(function(){
		$.fn.getCustDivSearch();
	});
	//고객사 사업부 변경시 프로젝트 조회
	$("#CUST_MASTER2").change(function(){
		$.fn.getCustDeptSearch();
	});
});
$(window).ready(function() {
});
$.fn.extend({	
	//페이지 초기화
	pageInit:function(){
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
    						$("#CUST_MASTER").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
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
	//고객사-사업부
	getCustDivSearch:function(){
		$("#CUST_MASTER2").empty();
		$("#CUST_MASTER2").append("<option value=''>사업부 선택</option>");
		$("#CUST_MASTER3").empty();
		$("#CUST_MASTER3").append("<option value=''>프로젝트 선택</option>");
		$("#CUST_MASTER4").empty();
		$("#CUST_MASTER4").append("<option value=''>영업조직 선택</option>");
		var data = {
				serviceFlag:G_SF_CUST_DIV,
				callback:"abcd",
				userId:$("#menuUserid").val(),
				userName:$("#menuUserName").val(),
				CUST_CODE:$("#CUST_MASTER").val(),
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
    						$("#CUST_MASTER2").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
	    				});
	    			}
	      			if(data.RES_DATA2.length != 0) {
	    				$(data.RES_DATA2).each(function(index,item) {
    						$("#CUST_MASTER3").append("<option value=\""+ item.CODE_VALUE +"\">"+ item.CODE_NAME +"</option>");
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
});
</script>
</html>