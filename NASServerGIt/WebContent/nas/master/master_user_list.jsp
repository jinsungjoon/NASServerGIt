<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
<script src="./js/master_user_list.js"></script>
<style type="text/css">
</style>

<title>마스터 관리 > 사용자 관리</title>
</head>
<body>
	<%@ include file="../res/menu.jsp"%>
	<div class='bodyContent'>
		<div class="searchArea">
			<div class="input-group">
				<span class="input-group-addon"> 사용그룹 </span> 
				<select	class="form-control" id="roleCode" tag="전체">
					<option value="">전체</option>
				</select>
			</div>
			<div class="input-group">
				<span class="input-group-addon"> 사용여부 </span> 
				<select	class="form-control" id="useYn" tag="전체">
					<option value="">전체</option>
					<option value="Y">사용</option>
					<option value="N">미사용</option>
				</select>
			</div>
			<div class="input-group">
				<span class="input-group-addon"> 사용자검색 </span> 
				<input type="text"	class="form-control" id="searchValue">
			</div>
			<div class="input-group">
				<button class="btn-default" id="btnSearch">조회</button>
			</div>
		</div>
		<div class="tableWrap" style="overflow: auto; max-height: 500px;">
		<table>
			<thead id="theadPc" class="theadCom" style="display: none">
				<tr>
					<th scope="col" style="width: 10%;">사용자명</th>
					<th scope="col" style="width: 15%;">로그인ID</th>
					<th scope="col" style="width: 10%;">비밀번호</th>
					<th scope="col" style="width: 10%;">사용그룹</th>
					<th scope="col" style="width: 5%;">직급</th>
					<th scope="col" style="width: 10%;">전화번호</th>
					<th scope="col" style="width: 10%;">핸드폰번호</th>
					<th scope="col" style="width: 15%;">이메일</th>
					<th scope="col" style="width: 8%;">사용여부</th>
				</tr>
			</thead>
			<thead id="theadTablet" class="theadCom" style="display: none">
				<tr>
					<th scope="col" style="width: 25%;">사용자명</th>
					<th scope="col" style="width: 25%;">로그인ID</th>
					<th scope="col" style="width: 25%;">사용그룹</th>
					<th scope="col" style="width: 25%;">직급</th>
				</tr>
				<tr>
					<th scope="col" style="width: 25%;">핸드폰번호</th>
					<th scope="col" style="width: 25%;">이메일</th>
					<th scope="col" style="width: 25%;">사용여부</th>
				</tr>
			</thead>
			<thead id="theadMobile" class="theadCom" style="display: none">
				<tr>
					<th scope="col" colspan="3">로그인ID</th>
				</tr>
				<tr>
					<th scope="col" style="width: 33%;">사용자명</th>
					<th scope="col" style="width: 33%;">사용그룹</th>
					<th scope="col" style="width: 34%;">직급</th>
				</tr>
			</thead>
			<tbody id="tbody">
			</tbody>
		</table>
		</div>
		<footer	style="height: 50px;">
			<ul id="pagination" style="margin-top:10px;">
			</ul>
		</footer>
	</div>
</body>
</html>