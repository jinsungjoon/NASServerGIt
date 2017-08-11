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
	<script type="text/javascript" src="./js/rpt110.js?version=<%= System.currentTimeMillis() %>"></script>
	<script type="text/javascript" src="../com/js/com_function.js?version=<%= System.currentTimeMillis() %>"></script>
	
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
				<h3 class="mt10">일별 장애접수 현황 </h3>
			</div><!-- // padding_area -->
			<div class="horizonBox mt5">
				<div class="searchElement">
					<table width="100%" border="0">
						<tr>
							<td class="tac" width="24%">일자</td>
							<td class="tal" width="38%">
								<input type="text" name="ymdDate" id="ymdDate" value="" readonly onclick="$.showDatePickerDialog(this, 'DATE_YMD', 'callbackSearchDate');" class="tac" style="width: 30%;min-width: 80px; max-width: 100px;line-height: 18px;" />
								<input type="text" name="ymdYm"   id="ymdYm"   value="" readonly onclick="$.showDatePickerDialog(this, 'DATE_YM' , 'callbackSearchDate');" class="tac" style="width: 30%;min-width: 80px; max-width: 100px;line-height: 18px; display:none;" />
							<!-- 
								<input type="text" name="frDate" id="frDate" value="" readonly onclick="$.showDatePickerDialog(this, 'DATE_YMD', 'callbackSearchDate');" class="tac" style="width: 30%;min-width: 80px; max-width: 100px;line-height: 18px;" />
								~
								<input type="text" name="toDate" id="toDate" value="" readonly onclick="$.showDatePickerDialog(this, 'DATE_YMD', 'callbackSearchDate');" class="tac" style="width: 30%;min-width: 80px; max-width: 100px;line-height: 18px;" />
							 -->
							</td>
							<td class="tal" width="38%">
								<input type="checkbox" id="ymdTag" style="vertical-align:middle; width: 20px; height: 20px;">월단위(접수)
							</td>
						</tr>
						<tr>
							<td class="tac" width="24%">고객사</td>
							<td class="tal" width="38%">
								<select style="min-width: 85px; max-width: 300px; width: 95%" id="idSeltCust" tag="고객사">
									<option value="">-- 고객사 --</option>
								</select>
							</td>
							<td class="tal" width="38%">
								<select style="min-width: 85px; max-width: 300px; width: 95%" id="idSeltDiv" tag="사업부">
									<option value="">-- 사업부 --</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="tac" width="24%">검색</td>
							<td class="tal" width="38%">
								<input type="text"   id="searchValue" maxlength="20" placeholder="제품코드" style="width: 91%;min-width: 81px; max-width: 300px;">
							</td>
							<td class="tal" width="38%">
								<button class="large rnd3 bg_blue pl2" onclick="$.fn.getDDList();" style="width: 64px;"><span class="ui-icon ui-state-white ui-icon-search mr2"></span>조회</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="padding_area">
				<table class="tab_4 mt10" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="25%" />
						<col width="35%" />
						<col width="*%" />
					</colgroup>
					<thead>
						<tr>
							<th class='lineb th_t'>NO</th>
							<th class='lineb th_t'>처리일자</th>
							<th class='lineb th_t'>제품번호(SN)</th>
						</tr>
						<tr>
							<th class='      th_b ' colspan="3">고객사 / 접수내역 <font color="blue">(조치내역)</font></th>
						</tr>
					</thead>
				</table>
				
			</div><!-- // padding_area -->
		</div><!-- // con_gojung -->
		
		<!-- 스크롤되는 영역을  <div id=con_scroll></div>에 선언 합니다.  -->
		<div id="con_scroll_wrap" class="con_scroll_wrap">
			<div class="con_scroll" id="con_scroll">
				<table class="linet tab_7" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<colgroup>
						<col width="25%" />
						<col width="35%" />
						<col width="*%" />
					</colgroup>
					<tbody id="dataList">
						<tr id="emptyList">
							<td colspan="3" style="position: relative;padding:0px 7px;text-align: center;line-height: 50px;">
								<!-- <img src="../res/img/ajax-loader.gif" style="display: inline-block;vertical-align: middle;" /> -->
								장애접수현황을 조회 하세요.
							</td>
						</tr>
					</tbody>
				</table>
				<br>
				<div class="tac">
					<span class='ui-icon ui-state-red ui-icon-bullet mr2'></span> 완료, 
					<span class='ui-icon ui-state-blue ui-icon-close mr2'></span> 접수취소
				</div>
				<br>
			</div><!-- con_scroll -->
		</div>
		
		
	</div><!-- //container -->
	
<%-- 	<%@ include file="../res/fuc/msgbox.jsp"%> --%>
</body>
</html>