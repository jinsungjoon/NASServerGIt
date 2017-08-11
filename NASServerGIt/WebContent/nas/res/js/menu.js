$(document).ready(function(){
		$.fn.resizeCheck();
		//메뉴아이콘 토글
		$('#menuBtn').click(function(){
			$(this).toggleClass('open');
		});
		//메뉴 타이틀 클릭시 서브메뉴 이벤트
		$(document).on('click', '.menuTitle', function(e) {
			if($(".icon").eq($(this).index()).hasClass("active")){
				$(".icon").eq($(this).index()).removeClass("active");
				$(".menuSub").eq($(this).index()).hide();
				if(e.target.className=="menuSub_sub"){
					$(".sideBar").hide();
					$("#menuBtnArea").css("left","10px");
					$("#menuBtn").toggleClass('open');
				}
			}else{
				$(".icon").removeClass("active");
				$(".menuSub").hide();
				$(".icon").eq($(this).index()).addClass("active");
				$(".menuSub").eq($(this).index()).css("display","flex").css("justify-content","flex-start").css("flex-flow","row wrap");
			}
		});
		
		$(document).on('click', '.menuSub_sub', function(e) {
			$.fn.goMenuList($(this).parents(".menuTitle").find("a").text(),$(this).text());
		});
		//메뉴버튼 클릭시 메뉴영역 보이기 이벤트
		$("#menuBtn").click(function(){
			if($("#menuBtn").hasClass("open")){
				$(".sideBar").show();
				$("#menuBtnArea").css("left","310px");
			}else{
				$(".sideBar").hide();
				$("#menuBtnArea").css("left","10px");
			}
		});
		$(".biconMenu").click(function(){
			window.open($(this).attr('data'), '_blank'); 
		});
	});
	$(window).bind("resize", function() { // 화면 리사이즈 처리
		$.fn.resizeCheck();
	});
	$.fn.extend({
		resizeCheck:function(){
			$.fn.menuDraw(function(){
				if($(window).width()>960){
					$(".sideBar").show();
				}else{
					$(".sideBar").hide();
					$('#menuBtn').removeClass('open');
					$("#menuBtnArea").css("left","10px");
				}
			});
		},
		//로딩창 띄우기
		showLoading:function(){
			$(".boxArea").show();
		},
		//로딩창 닫기
		hideLoading:function(){
			$(".boxArea").hide();
		},
		menuDraw:function(callback){
			  var menu1 = '[{"title":"분석","item":["일별 장애접수 현황 조회","월별 장애접수 현황 조회"]},';
		  	  menu1+= '{"title":"납품관리","item":["납품현황 조회"]},';
		  	  menu1+= '{"title":"기준관리","item":["기준정보수신","사용자정보 조회","제조사정보조회","고객사정보조회","고객사 영업조직 조회","고객사 부품단가 조회","고객사 장비 조회"]}';
		  	  menu1+= ']';
			 
		//PC 일반
		  var menu2 = '[{"title":"분석","item":["장애처리현황 조회","월별 장애접수 현황 조회"]},';
		  	  menu2+= '{"title":"납품관리","item":["납품현황 조회","납품현황 등록"]},';
		  	  menu2+= '{"title":"기준관리","item":["사용자정보 조회","제조사정보조회","고객사정보조회","고객사 영업조직 조회","고객사 부품단가 조회","고객사 장비 조회"]}';
		  	  menu2+= ']';
		
		//PC 관리자	
		  var menu3 = '[{"title":"분석","item":["일별 장애접수 현황 조회","월별 장애접수 현황 조회"]},';
		 	  menu3+= '{"title":"납품관리","item":["납품현황 조회","납품현황 등록"]},';
		  	  menu3+= '{"title":"기준관리","item":["사용자정보 조회","제조사정보조회","고객사정보조회","고객사 영업조직 조회","고객사 부품단가 조회","고객사 장비 조회"]},';
		  	  menu3+= '{"title":"마스터 관리","item":["사용자 관리","제조사 관리","코드 관리","고객사 관리","장비관리","앱관리"]}';
		  	  menu3+= ']';
		
		  var menu = "";
			  if(G_MOBILE_FLAG)menu = menu1;
			  else{
				  menu = menu3;
			  }
			  menu = $.parseJSON(menu);
			  var menuHtml = "";
			  $.each(menu,function(index,item){
				 var parentId = localStorage.getItem('menuTitle');
				 var menuId   = localStorage.getItem('menuId');
				 if(parentId == item.title) menuHtml += "<li class='menuTitle'><a href='#'>"+item.title+"</a><span class='icon active'></span><ul class='menuSub' style='display:flex;justify-content:flex-start;flex-flow:row wrap'>";
				 else                       menuHtml += "<li class='menuTitle'><a href='#'>"+item.title+"</a><span class='icon'></span><ul class='menuSub'>";
				  for(var i in item.item){
					  menuHtml +="<li class='menuSub_sub'>"+item.item[i]+"</li>";
				  }
				  menuHtml +="</ul></li>";
			  });
			  $("#nav").html(menuHtml);
			  
			  callback();
		}
	});
	