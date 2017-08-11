<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, width=device-width ,minimum-scale=1.0">
<title>menu</title>
<!-- <link href="../res/css/reset.css" rel="stylesheet"> -->
<link href="../res/css/menu.css" rel="stylesheet">
<script type="text/javascript" src="../res/bean/bean-user.js"></script>
<script type="text/javascript" src="../res/js/menu.js"></script>
<script>
<%-- var G_USER_ID = "<%= session.getAttribute("userId") %>"; --%>
var G_ASM_USER = <%= session.getAttribute("ASM_USER") %>;
</script>

</head>
	<body>
		<header>
			<div id='header'>
				<div id='menuBtnArea'>
					<div id="menuBtn">
						<span></span>
						<span></span>
						<span></span>
					</div>
				</div>
				<div id="topHeader">
					<span id="pageTitle">
						<span id="pageTitleSpan"></span>
					</span>
				</div>
			</div>
		</header>
		<div class='sideBar'>
			<div class="leftMenu">
				<div class='userInfo'>
					<div class='userPicture'></div>
					<div class='userDetail'>
						<div class='userId'><%= session.getAttribute("userId") %></div>
						<div class='userName'><%= session.getAttribute("userName") %></div>
					</div>
				</div>
				<div class='fastMenu'>
					<div class="iconMenu">
						<span>장애접수</span>
					</div>
					<div class="iconMenu">
						<span>접수현황</span>
					</div>
					<div class="iconMenu">
						<span>월별접수<br>현황조회</span>
					</div>
				</div>
					<ul id="nav"></ul>
				<div id='logo'>
					<span></span>
				</div>
				<div class='buttomMenu'>
					<div class="biconMenu" data="http://mail.nwtns.com/">
						<span>메일</span>
					</div>
					<div class="biconMenu" data="http://www.nwtns.com">
						<span>홈페이지</span>
					</div>
				</div>
			</div>
		</div>
			<div class='boxArea'>
<!-- 				<div class='boxAreaBlock'> -->
					<div class="sk-fading-circle">
					  <div class="sk-circle1 sk-circle"></div>
					  <div class="sk-circle2 sk-circle"></div>
					  <div class="sk-circle3 sk-circle"></div>
					  <div class="sk-circle4 sk-circle"></div>
					  <div class="sk-circle5 sk-circle"></div>
					  <div class="sk-circle6 sk-circle"></div>
					  <div class="sk-circle7 sk-circle"></div>
					  <div class="sk-circle8 sk-circle"></div>
					  <div class="sk-circle9 sk-circle"></div>
					  <div class="sk-circle10 sk-circle"></div>
					  <div class="sk-circle11 sk-circle"></div>
					  <div class="sk-circle12 sk-circle"></div>
					</div>
				</div>
<!-- 				<span class='lodingTitle'>로딩중..</span> -->
<!-- 			</div> -->
				
		<input type="hidden" id="menuUserId" value="<%= session.getAttribute("userId") %>">
		<input type="hidden" id="menuUserName" value="<%= session.getAttribute("userName") %>">
	</body>
	<script>
		<%	String id = (String)session.getAttribute("userId");
		if(id == null){%>
			alert("time out");
			$.fn.goMenuList("","menu000");
		<%	}else{	%>
		<%} %>
	</script>
</html>