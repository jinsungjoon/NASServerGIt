<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<title>NWTNS 로그인</title>
<link rel="stylesheet" type="text/css" href="../res/css/nwtns.css">
<link rel="stylesheet" type="text/css" href="../res/css/login.css">
<script src="../res/js/jquery.min.js"></script>
<script src="../res/js/common.js"></script>
<script type="text/javascript" src="../res/js/def_variable.js"></script>
<script type="text/javascript" src="../res/js/def_date.js"></script>
<script type="text/javascript" src="../res/js/def_string.js"></script>
<script type="text/javascript" src="../res/js/def_private.js"></script>
<script type="text/javascript" src="../res/js/android.js"></script>
<script src="../res/js/protocol.js"></script>
<script src="js/sessionCheck.js"></script>
<script src="js/login.js"></script>
</head>
<body>
	<div class='wrap'>
		<header class="header">
			<div class='logo'>
			</div>
		</header>
		<div class="content">
			
			<div class="leftMain">
				<ul>
					<li>
						<input type="text" placeholder="아이디" id='userId' name='userId' autocomplete="off">
					</li>
					<li>
						<input type="password" placeholder="패스워드" id='userPass' name='userPass' autocomplete="off">
					</li>
					<li style="padding-top:10px;">
						<button id="userBtn" onclick="$.fn.loginProc()">
							로그인
						</button>
					</li>
					<li class='liGroupBtn'>
						<button class='groupBtn'>
						회원가입
						</button>
						<button class='groupBtn'>
						아이디 찾기
						</button>
						<button class='groupBtn'>
						비밀번호 찾기
						</button>
					</li>
				</ul>
			</div>
			<div class="rightMain">
			</div>
		</div>
		<div class="footer">
			<p>주소 : 서울특별시 구로구 디지털로 273 에이스트윈타워2차 1302호 (주)나우티앤에스 </p>
			<p>TEL : 02-865-6600 FAX : 02-865-4455</p>
			<p>Copyright (c) 2015. Nawoo Technology & Service Co,.Ltd. All Rights Reserved.</p>
			<p id='version' style="justify-content: center;"></p>
		</div>
	</div>
</body>
</html>