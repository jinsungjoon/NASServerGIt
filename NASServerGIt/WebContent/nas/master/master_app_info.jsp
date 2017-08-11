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
<script src="./js/master_app_info.js"></script>
<style >
	/* .file_input_div {
		position: relative;
		width: 100px;
		height: 25px;
		overflow: hidden;
	} */
	.file_input_hidden {
		font-size: 45px;
		position: absolute;
		right: 0px;
		top: 0px;
		opacity: 0;
	}
</style>
<title>일별 장애접수 현황 조회</title>
</head>
<body>
	<div class="flex_menu">
		<%@ include file="../res/menu.jsp"%>
	</div>
	
	<div class='bodyContent flex_contents' id='bodyContent' style="max-width:1200px; float: left; ">
		<h1 class="subject tal" >■ 안드로이드</h1>
		<div class="subjectr">
			<button class="btn_normal" onclick="$.fn.uploadFile('1')">저장</button>
		</div>
			
		<div class="searchArea">
			<input type="hidden" id="app_gubun1" value="10"/>
			<div class="input-group50">
				<span class="input-group-addon"> 현재버전 </span>
				<span class="form-control" id="lblVerInfo1"></span>
				
			</div>
			<div class="input-group50 pc_layout">
				<span class="input-group-addon"> <font color="red">*</font> 변경버전 </span> 
				<input class="form-control"  type="text" id="txtNewVerInfo1" style="width: 40% !important" placeholder="신규버전 입력">
			</div>
			<div class="input-group50">
				<span class="input-group-addon"> 최초 등록일 </span> 
				<span class="form-control" id="lblCreateDate1">&nbsp;</span>
			</div>

			<div class="input-group50 removeArea">
				<span class="input-group-addon"> 마지막변경일</span> 
				<span class="form-control" id="lblUpdateDate1">&nbsp;</span>
			</div>

			<div class="input-group50 removeArea pc_layout">
				<span class="input-group-addon"> <font color="red">*</font> 파일선택 </span>
				<input type="text"	class="form-control" id="file_addr1" style="width: 40% !important" placeholder="설치파일 첨부" readonly="readonly">
				<button class="btn-down" style="width: 20% !important" name="btn_file_upload" id="btn_file_upload1" onclick="$.fn.onClick_FileAdd('file_upload1')">첨부</button>
				<input type="file" class="file_input_hidden" name="file_upload" id="file_upload1">

<!-- 				<div class="form-control"> -->
<!-- 					<input class="form-control" type="text" style="float: left; width: 200px;" id="file_addr1" value="" readonly="readonly"/>&nbsp; -->
<!-- 					<div class="file_input_div"> -->
<!-- 						<button class="btn_normal" type="button" name="btn_file_upload" id="btn_file_upload1">첨부</button> -->
<!-- 						<input type="file" class="file_input_hidden" name="file_upload" id="file_upload1"> -->
<!-- 					</div> -->
<!-- 				</div> -->
			</div>

			<div class="input-group50 removeArea">
				<span class="input-group-addon"> 기타 </span> 
				<!-- <span class="form-control" id="lblMemo1">&nbsp;</span> -->
				<input class="form-control"  type="text" id="txtMemo1">
			</div>
		</div>
		
		
		<h1 class="subject tal" >■ iOS</h1>
		<div class="subjectr">
			<button class="btn_normal" onclick="$.fn.uploadFile('2')">저장</button>
		</div>
		
		<div class="searchArea">
			<input type="hidden" id="app_gubun2" value="20"/>
			<div class="input-group50">
				<span class="input-group-addon"> 현재버전 </span> 
				<span class="form-control" id="lblVerInfo2">-</span>
				
			</div>
			<div class="input-group50 pc_layout">
				<span class="input-group-addon"> <font color="red">*</font> 변경버전 </span> 
				<input class="form-control"  type="text" id="txtNewVerInfo2" style="width: 40% !important" placeholder="신규버전 입력">
			</div>
			<div class="input-group50">
				<span class="input-group-addon"> 최초 등록일 </span> 
				<span class="form-control" id="lblCreateDate2">&nbsp;</span>
			</div>

			<div class="input-group50 removeArea">
				<span class="input-group-addon">마지막변경일</span> 
				<span class="form-control" id="lblUpdateDate2">&nbsp;</span>
			</div>

			<div class="input-group50 removeArea pc_layout">
				<span class="input-group-addon"> <font color="red">*</font> 파일선택 </span>
				
				<input type="text"	class="form-control" id="file_addr2" style="width: 40% !important" placeholder="설치파일 첨부" readonly="readonly">
				<button class="btn-down" style="width: 20% !important" name="btn_file_upload" id="btn_file_upload2" onclick="$.fn.onClick_FileAdd('file_upload2')">첨부</button>
				<input type="file" class="file_input_hidden" name="file_upload" id="file_upload2">
				
				<!-- <div class="form-control">
					<input class="form-control" type="text" style="float: left; width: 200px;" id="file_addr2" value="" readonly="readonly"/>&nbsp;
					<div class="file_input_div">
						<button class="btn_normal" type="button" name="btn_file_upload" id="btn_file_upload2">첨부</button>
						<input type="file" class="file_input_hidden" name="file_upload" id="file_upload2">
					</div>
				</div> -->
			</div>

			<div class="input-group50 removeArea">
				<span class="input-group-addon"> 기타 </span> 
				<!-- <span class="form-control" id="lblMemo2">&nbsp;</span> -->
				<input class="form-control"  type="text" id="txtMemo2">
			</div>
		</div>
	</div>
	
</body>
<script>

</script>
</html>