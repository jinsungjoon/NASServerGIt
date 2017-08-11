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
<script src="./js/master_maker_list.js"></script>
<style type="text/css">
</style>

<title>마스터 관리 > 사용자 관리</title>
</head>
<body>
	<div class="flex_menu">
		<%@ include file="../res/menu.jsp"%>
	</div>
	<div class='bodyContent flex_contents'>
		<div class="searchArea">
			<div class="input-group50" style="width:80%">
				<select	class="form-control" id="roleCode" tag="전체"  style="max-width:150px;">
					<option value="">사용유무</option>
					<option value="">사용</option>
					<option value="">미사용</option>
				</select>
				
				<input class="form-control" type="text"	class="form-control" id="searchValue" style="max-width:150px;" placeholder="제조사 명/코드">
				&nbsp;&nbsp;
				<button class="btn-default" id="btnSearch" style="max-width:100px;">조회</button>
			</div>
			<div class="input-group50 ftar" style="width:20%">
				<button class="btn-default" id="btnSearch" style="max-width:100px;">추가</button>
			</div>
		</div>
		
			<div class="input-half-group" style="margin-top:5px; max-height: 500px;">
				<!-- <div class="tableWrap" style="width:95%; overflow: auto; min-width:450px; max-height: 500px;"> -->
					<table >
						<thead id="makerListHeader" >
							<tr>
								<th scope="col" style="width: 60%; line-height: 19px;">제조사명</th>
								<th scope="col" style="width: 25%;">제조사코드</th>
								<th scope="col" style="width: 15%;">사용여부</th>
							</tr>
						</thead>
						<tbody id="makerListBody">
						</tbody>
					</table>
				<!-- </div> -->
			</div>
			<div class="input-half-group" style="margin-top:5px;">
				<div class="input-group50">
					<span class="input-group-addon">제조사명 </span>
					<span class="form-control" id="lblVerInfo1"></span>
				</div>
				<div class="input-group50">
					<span class="input-group-addon">제조사코드 </span>
					<span class="form-control" id="lblVerInfo1"></span>
				</div>
				<div class="input-group50">
					<span class="input-group-addon">대표자명 </span>
					<span class="form-control" id="lblVerInfo1"></span>
				</div>
				<div class="input-group50">
					<span class="input-group-addon">사업자번호 </span>
					<span class="form-control" id="lblVerInfo1"></span>
				</div>
			</div>
		
		
		
		
		
		<!-- <div class="searchArea">
			<div class="input-group50" style="margin-top:5px; max-height: 500px;">
				<div class="tableWrap" style="width:95%; overflow: auto; min-width:450px; max-height: 500px;">
					<table >
						<thead id="makerListHeader" >
							<tr>
								<th scope="col" style="width: 60%; line-height: 19px;">제조사명</th>
								<th scope="col" style="width: 25%;">제조사코드</th>
								<th scope="col" style="width: 15%;">사용여부</th>
							</tr>
						</thead>
						<tbody id="makerListBody">
						</tbody>
					</table>
				</div>
			</div>
			<div class="input-group50" style="margin-top:5px;">
				<div class="input-group50">
					<span class="input-group-addon">제조사명 </span>
					<span class="form-control" id="lblVerInfo1"></span>
				</div>
				<div class="input-group50">
					<span class="input-group-addon">제조사코드 </span>
					<span class="form-control" id="lblVerInfo1"></span>
				</div>
				<div class="input-group50">
					<span class="input-group-addon">대표자명 </span>
					<span class="form-control" id="lblVerInfo1"></span>
				</div>
				<div class="input-group50">
					<span class="input-group-addon">사업자번호 </span>
					<span class="form-control" id="lblVerInfo1"></span>
				</div>
			</div>
		</div> -->
		
		<footer	style="height: 50px;">
			<ul id="pagination" style="margin-top:10px;">
			</ul>
		</footer>
	</div>
</body>
</html>