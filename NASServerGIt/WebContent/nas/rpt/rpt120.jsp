<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="not-ie no-js" lang="en" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0,user-scalable=no" />
	
	<title>AS관리시스템</title>
	
	<link rel="stylesheet" href="../res/css/style.css?version=<%= System.currentTimeMillis() %>" media="screen"/>
	<link rel="stylesheet" href="../res/css/theme.css" media="screen"/>
	<link rel="stylesheet" href="../../dist/jquery-ui.css" media="screen"/>
	
	<script type="text/javascript" src="../../dist/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="../../dist/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../res/js/def_variable.js"></script>
	<script type="text/javascript" src="../res/js/def_string.js"></script>
	<script type="text/javascript" src="../res/js/def_date.js"></script>
	<script type="text/javascript" src="../res/js/protocol.js"></script>
	<script type="text/javascript" src="../res/js/common.js"></script>
	<script type="text/javascript" src="../res/js/android.js"></script>
	<script type="text/javascript" src="./js/rpt120.js?version=<%= System.currentTimeMillis() %>"></script>
	
</head>
<body>
	<!-- CONTAINER -->
	<div id="container">
		<div id="btnTopWrap">
			<button class="large bg_blue rnd3 pl3 pr10" onclick="$('#con_scroll_wrap').animate({scrollTop:0}, 0, function(){ $('#btnTopWrap').hide(); });"><span class="ui-icon ui-state-white ui-icon-arrowthickstop-1-n mr2"></span>TOP</button>
		</div>
		<!-- 고정하려는 영역을 <div id=con_gojung></div>에 선언 합니다.  -->
		<div class="con_gojung" id="con_gojung">
			<div class="padding_area">
				<h3 class="mt10">월별 장애접수 현황 </h3>
				
			</div><!-- // padding_area -->
		</div><!-- // con_gojung -->
		
		<!-- 스크롤되는 영역을  <div id=con_scroll></div>에 선언 합니다.  -->
		<div id="con_scroll_wrap" class="con_scroll_wrap">
			<div class="con_scroll" id="con_scroll">
				
			</div>
		</div><!-- con_scroll -->
	</div><!-- //container -->
	
	<!-- <%@ include file="../res/fuc/msgbox.jsp"%> -->
</body>
</html>