var dataList = "";
var preFlag  = "";
var currFlag  = "";

$(document).ready(function() {
	$.fn.hideLoading();
	
	$(window).bind("resize", function() { // 화면 리사이즈 처리
		//$.fn.drawTable();
	});
	
	$("input[name=file_upload]").change(function() {
		var index = "";
		index = $(this).attr("id").replace("file_upload", "");
		var filename = $(this).val();
        if (filename.substring(3,11) == 'fakepath') {
            filename = filename.substring(12);
        }
        $("#file_addr"+index).val(filename);
	});
	
	$.fn.getVersionInfo();
});
$(window).ready(function() {
	$("#pageTitleSpan").text("마스터 관리 > 앱관리");
	
	
});
$.fn.extend({	
	//페이지 초기화
	
	pageInit:function(){
		
	},
	
	getVersionInfo: function() {
		var data = {
				serviceFlag:MASTER_GETVERSION_V01,
				callback:"abcd",
				userId:G_ASM_USER.USER_ID,
		};
		$.ajax({
			global: false,
			timeout : 30000,
			type : "POST",
			url : G_JSONP_URL_MASTER,
			data : data,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			success : function(data){
				//alert(data);
				if(data.RETURN_CODE=="200"){
					$.fn.drawVersion(data.DATA);
				}
				else{
					alert(data.RETURN_MSG);
				}
			},
			error: function(request,status,error) {
				alert(error);
			}
		});
	},
	
	drawVersion: function(data) {
		$(data).each(function(index, item){
			if(item.APP_GUBUN == '10') {
				$("#lblVerInfo1").text(item.VER_INFO);
				$("#lblCreateDate1").text(item.CREATE_DATE);
				$("#lblUpdateDate1").text(item.UPDATE_DATE);
				$("#txtMemo1").val(item.MEMO);
				
				$("#txtNewVerInfo1").val("");
			}
			if(item.APP_GUBUN == '20') {
				$("#lblVerInfo2").text(item.VER_INFO);
				$("#lblCreateDate2").text(item.CREATE_DATE);
				$("#lblUpdateDate2").text(item.UPDATE_DATE);
				$("#txtMemo2").val(item.MEMO);
				
				$("#txtNewVerInfo2").val("");
			}
		});
	},
	
	uploadFile: function(index) {
		
		var newVerInfo = $("#txtNewVerInfo"+index).val();
		if(newVerInfo == '') {
			alert("변경할 버전을 입력 하십시오.");
			$("#txtNewVerInfo"+index).focus();
			return;
		}
		var filename = $('#file_upload'+index).val();
		if(filename == '') {
			alert("신규버전 설치파일을 첨부 하십시오.")
		} else {
			if (filename.substring(3,11) == 'fakepath') {
	            filename = filename.substring(12);
	        }
		}
		
		var fChk = filename.split(".");
		if(fChk.length < 2) {
			alert("설치파일형식이 올바르지 않습니다[0].\n확인 후 다시 시도 하십시오.");
			return;
		}
		if(index == "2") {
			if(fChk[fChk.length-1] != "pdf") {
				alert("[iOS]설치파일형식이 올바르지 않습니다.\n확인 후 다시 시도 하십시오.");
				return;
			}
		} else {
			if(fChk[fChk.length-1] != "apk") {
				alert("[Android]설치파일형식이 올바르지 않습니다.\n확인 후 다시 시도 하십시오.");
				return;
			}
		}
		
		var extraData = {
				appGubun : $("#app_gubun"+index).val(),
				newVersion : newVerInfo,
				memo : $("#txtMemo"+index).val()
		}
        
		var form = "<form id='FILE_FORM' method='post' enctype='multipart/form-data'></form>";
        var formData = new FormData(form);
        formData.append("upload_type", "APK");
        formData.append("user_id", G_ASM_USER.USER_ID);
        formData.append("file_name", filename);
        formData.append("old_ver_info", $("#lblVerInfo"+index).text());
        formData.append("new_ver_info", newVerInfo);
        //formData.append("memo", $("#txtMemo"+index).val());
        //formData.append("app_gubun", $("#app_gubun"+index).val());
        formData.append("fileObj", $("#file_upload"+index)[0].files[0]);
        formData.append("extraData", JSON.stringify(extraData));
        
        $.ajax({
            url: '/ASM_WEB/nas/upload.asm',
           	processData: false,
           	contentType: false,
           	data: formData,
           	type: 'POST',
        	
			global: false,
			timeout : 30000,
			type : "POST",
			jsonp : "callback",
			crossDomain : true,

           	success: function(data){
           		//$.fn.appSubmit(index);
           		// data가 Object가 아닌 String 임.
           		data = JSON.parse(data);
           		
           		if(data.RETURN_CODE=="200"){
           			$.fn.drawVersion(data.DATA);
           			
					alert("신규버전 업데이터가 완료 되었습니다.")
				}
				//로그인 실패
				else{
					alert(data.RETURN_MSG);
				}
           	},
           	error: function(e) {
           		alert("실패");
           	}
        });
	},
	
	drawTable:function(){
		if($(window).width()>960){
			$(".pc_layout").show();
		}else if( $(window).width() < 960 && $(window).width() > 767){
			$(".pc_layout").hide();
		}else{
			$(".pc_layout").hide();
		}
		
		
	},
	
	onClick_FileAdd: function(element) {
		$("#" +element).trigger('click');
	},
});

