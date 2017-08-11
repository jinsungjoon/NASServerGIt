<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="../res/stylesheets/nwtns.css">
<link href="../res/css/reset.css" rel="stylesheet">
<link href="../res/css/menu.css" rel="stylesheet">
<link href="../res/css/content.css" rel="stylesheet">

<link rel="fluid-icon" href="https://github.com/fluidicon.png" title="GitHub">	
<script src="../res/js/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.1/css/materialize.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.1/js/materialize.min.js"></script>
<script src="../res/js/def_variable.js"></script>
<script src="../res/js/common.js"></script>
<script type="text/javascript" src="../res/js/def_date.js"></script>
<script type="text/javascript" src="../res/js/def_string.js"></script>
<script type="text/javascript" src="../res/js/def_private.js"></script>
<script type="text/javascript" src="../res/js/android.js"></script>
<script src="../res/js/protocol.js"></script>
<script src="../res/navibar.js"></script>
<script src="./js/rpt202.js"></script>
 <style type="text/css">



</style>
<title>일별 장애접수 현황 조회</title>
</head>
<body>
	<div class="flex_menu">
		<%@ include file="../res/menu.jsp"%>
	</div>
	<div class='bodyContent flex_contents'>
		<div class="searchArea">
			<div class="input-group">
				<span class="input-group-addon"> 일자 </span> 
				<input type="text"	class="form-control datepicker" id="ymd">
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
				<button class="btn-down">액셀 다운로드</button>
				<button class="btn-default" id="btnSearch">조회</button>
			</div>
		</div>
			<div class="tableWrap" style="overflow: auto; max-height: 500px;">
			<table>
				<thead id="theadPc" class="theadCom" style="display: none">
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
				<thead id="theadTablet" class="theadCom" style="display: none">
					<tr>
					<th scope="col" style="width: 25%;">No</th>
					<th scope="col" style="width: 25%;">제품번호(SN)</th>
					<th scope="col" style="width: 25%;">고객사</th>
					<th scope="col" style="width: 25%;">제품정보</th></tr>
					<tr><th scope="col" style="width: 25%;">진행상태</th>
					<th scope="col" style="width: 25%;">접수내역</th>
					<th scope="col" style="width: 25%;">조치내역</th>
					<th scope="col" style="width: 25%;">부품비</th>
					</tr>
				</thead>
				<thead id="theadMobile" class="theadCom" style="display: none">
					<tr>
						<th scope="col" style="width: 33%;">No</th>
						<th scope="col" style="width: 33%;">제품번호(SN)</th>
						<th scope="col" style="width: 34%;">제품정보</th></tr>
						<tr><th scope="col" colspan="3">진행상태</th>
					</tr>
				</thead>
				<tbody id="tbody">
				</tbody>
			</table>
			</div>
			<footer	style="height: 50px;">
				<ul id="pagination" style="margin-top:10px;">
<!-- 					<li><a>1</a></li> -->
<!-- 					<li><a>2</a></li> -->
<!-- 					<li><a>3</a></li> -->
<!-- 					<li><a>4</a></li> -->
<!-- 					<li><a>5</a></li> -->
<!-- 					<li><a>6</a></li> -->
<!-- 					<li><a>7</a></li> -->
<!-- 					<li><a>8</a></li> -->
<!-- 					<li><a>9</a></li> -->
<!-- 					<li><a>10</a></li> -->
				</ul>
			</footer>
	</div>
</body>
<script>

</script>
</html>