
/*********************************************************
 * 
 * 1. 제목 : 
 * 
 * 2. 주요 기능
 *    1) 
 *    
 * 3. 최초 작성자 / 일시 : 
 * 
 * 4. 수정 이력
 *   1) 
 *   2)
 *   3)
 *   
 ********************************************************/

$(document).ready(function() {
	
});

$(window).ready(function() {	
	
	//화면스크롤링 관련처리
	$.fn.initPageScroll();
	
	$.fn.initProcess();
	
	// 고객사
	$.fn.codeAddOtion($("#idSeltCust"), $.fn.getSelCustList(), "");
//	setTimeout(function() {
//		$.fn.getSelCustList("#idSeltCust");
//	}, 200);

	setTimeout(function(){
		$.fn.getDDList();
	}, 100);
});

$.fn.extend({	
	/**
	 * 1. 함수명 : 
	 * 2. 주기능 : 
	 * 3. Input Parameters
	 *   1) 없음
	 * 4.Output Parameters
	 *   1) 없음
	 */
	initProcess : function() {
		//검색기간
		$("#ymdDate").val($.fn.getCurrentDateTime("DATE_YMD"));
		$("#ymdYm").val($.fn.getCurrentDateTime("DATE_YM"));
		
		// 고객사 변경
		$("#idSeltCust").change(function(){
			$.fn.selChangeCust(this);
		});
		// 사업부 변경
		$("#idSeltDiv").change(function(){
			//$.fn.selChangeDiv(this);
		});
		
		// ($('#ymdTag').is(':checked') ? 'Y' : 'N');
		$('#ymdTag').click(function(){
			if($(this).is(':checked')){
				$("#ymdDate").hide();
				$("#ymdYm").show();
			}else{
				$("#ymdDate").show();
				$("#ymdYm").hide();
			}
		});
	},
	
	//검색기간 선택시
	/*
	callbackSearchDate:function(elementId, val){

		var frDate;
		var toDate;

		if (elementId == "frDate") {
			frDate = $.fn.replaceAllstr(val, '-', '');
			toDate = $.fn.replaceAllstr($("#toDate").val(), '-', '');
		} else if (elementId == "toDate") {
			frDate = $.fn.replaceAllstr($("#frDate").val(), '-', '');
			toDate = $.fn.replaceAllstr(val, '-', '');
		}

		if (elementId == "frDate" || elementId == "toDate") {
			//월차이계산
			var difMonth = $.fn.getDifMonths(frDate, toDate);

			if (difMonth < 0) {
				if (elementId == "frDate") {
					(new MsgBox("검색시작일자는 종료일자보다<br /><br/>클 수 없습니다.", "검색기간 확인")).alert();
					return;
				} else if (elementId == "toDate") {
					(new MsgBox("검색종료일자는 시작일자보다<br /><br/>작을 수 없습니다.", "검색기간 확인")).alert();
					return;
				}
			}
		}

		$("#"+elementId).val(val);
	},
	*/
	callbackSearchDate:function(elementId, val){
		$("#"+elementId).val(val);
	},
	
	/***
	getSelCustList: function(elementId) {
		var data = {
				serviceFlag : G_SF_UTIL_SELT_CUST,
				callback : 'abcd',
				searchTag : ''
		};

		$.ajax({
			type : "POST",
			url : G_JSONP_URL_UTIL,
			data : data,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			beforeSend : function() {
			},
			success : function(data){
				$.andrProgressHide();
				
				if(data.RETURN_CODE=="200"){
					arrCustList = data.RES_MDATA;
					
					$(elementId).empty();
					$(elementId).append('<option value="">-- ' + $(elementId).attr("tag") + ' (' + arrCustList.length + ') --</option>');
					if (arrCustList.length > 0) {
						$.each(arrCustList, function(key, val){
							$(elementId).append($("<option>").val(val.CODE).text(val.VALUE));
						});
					}
				} else {
					$(elementId).empty();
					$(elementId).append('<option value="">-- ' + elementId.attr("tag") + ' --</option>');
					
					(new MsgBox(data.RETURN_MSG, "확인")).alert();
					return;
				}
			},
			error: function(request,status,error) {
				$(elementId).empty();
				$(elementId).append('<option value="">-- ' + elementId.attr("tag") + ' --</option>');
				
				$.andrProgressHide();
				(new MsgBox("Parsing error:"+error, "확인")).alert();
				return;
			}
		});
	},
	
	selChangeCust: function(elementId) {
		
		var elementId1 = "#idSeltDiv";
		
		$(elementId1).empty();
		$(elementId1).append('<option value="">-- ' + $(elementId1).attr("tag") + ' --</option>');
		
		var custCode    = $(elementId).val();
		if(custCode == '' || custCode == '*') {
			return;
		}
		
		var data = {
				serviceFlag : G_SF_UTIL_SELT_CUST,
				callback : 'abcd',
				custCode : custCode,
				searchTag : ''
		};

		$.ajax({
			type : "POST",
			url : G_JSONP_URL_UTIL,
			data : data,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			beforeSend : function() {
			},
			success : function(data){
				$.andrProgressHide();
				
				if(data.RETURN_CODE=="200"){
					// 사업부, 영업소
					if(elementId1 != "") {
						var resultList1 = data.RES_MDATA;
						
						$(elementId1).empty();
						$(elementId1).append('<option value="">-- ' + $(elementId1).attr("tag") + ' (' + resultList1.length + ') --</option>');
						
						if (resultList1.length > 0) {
							$.each(resultList1, function(key, val){
								$(elementId1).append($("<option>").val(val.CODE).text(val.VALUE));
							});
						}
					}
				} else {
					(new MsgBox(data.RETURN_MSG, "확인")).alert();
					return;
				}
			},
			error: function(request,status,error) {
				$.andrProgressHide();
				(new MsgBox("Parsing error:"+error, "확인")).alert();
				return;
			}
		});
	},
	***/
	
	selChangeCust: function(elementId) {
		var custCode = $(elementId).val();
		
		// 사업부
		$("#idSeltDiv").empty();
		$("#idSeltDiv").append('<option value="">-- ' + $("#idSeltDiv").attr("tag") + '--</option>');
		$.fn.codeAddOtion($("#idSeltDiv"), $.fn.getSelCustDivList(custCode), "");
	},
	
	getDDList: function() {
		var seltCust = $("#idSeltCust").val();
		var seltDiv = $("#idSeltDiv").val();
		var searchValue = $("#searchValue").val();
		
		var ymd="", ym="";
		if($('#ymdTag').is(':checked')) {
			ym = $("#ymdYm").val();
		} else {
			ymd = $("#ymdDate").val();
		}
			
		var data = {
				serviceFlag : G_SF_RPT_DDLIST,
				callback : 'abcd',
				ymd: ymd,
				ym: ym,
				custCode: seltCust,
				custDiv: seltDiv,
				searchValue: searchValue
		};
		
		$.andrProgressShow();

		$.ajax({
			type : "POST",
			url : G_JSONP_URL_RPT,
			data : data,
			dataType : "jsonp",
			jsonp : "callback",
			crossDomain : true,
			beforeSend : function() {
				$("#dataList").find("tr").not("#emptyList").remove();
				$("#emptyList").find("td").html("<img src='../res/img/ajax-loader.gif' style='display: inline-block;vertical-align: middle;' />");
				$("#emptyList").show();
			},
			success : function(data){
				$.andrProgressHide();
				
				if(data.RETURN_CODE=="200"){
					resultList = data.RES_MDATA;
					""
					if (resultList.length > 0) {
						var sb = [];
						$.each(resultList, function(key, val){
							sb.push("<tr class='dataRow bold' style='color:black;'>");
							sb.push("  <td class='dotr dotb tac t_ell'>");
							sb.push("  		<input type='hidden' name='callSeq'   value='", val.CALL_SEQ, "'/>");
							sb.push("  		<input type='hidden' name='itemSeq'   value='", val.ITEM_SEQ, "'/>");
							sb.push("  		<input type='hidden' name='statusFlag'   value='", val.STATUS_FLAG, "'/>");
							sb.push(        val.CALL_SEQ, "-", val.ITEM_SEQ);
							sb.push("  </td>");
							sb.push("  <td class='dotr dotb tac t_ell' style='color:black;'>", val.IN_YMD, "~", val.EX_YMD, "</td>");
							sb.push("  <td class='dotr dotb tac t_ell'>", val.PROD_CODE, "</td>");
							sb.push("</tr>");
							sb.push("<tr>");
							sb.push("  <td class='     dotb tal t_ell' colspan='3'>");
							sb.push(		val.STATUS_FLAG == "50" ? "<span class='ui-icon ui-state-red ui-icon-bullet mr2'></span>" : "");
							sb.push(		val.STATUS_FLAG == "90" ? "<span class='ui-icon ui-state-blue ui-icon-close mr2'></span>" : "");
							sb.push(		val.MEDIA       != "P"  ? "<span class=\"icon_wrap mr2\"><span class=\"icon_smartphone\"></span></span>" : "");
							sb.push(        val.CUST_INFO, "  &nbsp;</td>");
							sb.push("</tr>");
							sb.push("<tr>");
							sb.push("  <td class='    lineb tal' colspan='3'>", val.TROUBLE_VALUE);
							sb.push(   val.REPAIR_VALUE != "" ? " <font color='blue'>(" + val.REPAIR_VALUE + ")</font>" : "");
							sb.push("  &nbsp;</td>");
							sb.push("</tr>");
						});
						
						sb = sb.join("");
						$("#dataList").append(sb).promise().done(function(){
							//터치시작 이벤트
							$("#dataList").find("tr.dataRow").find("td").bind("touchstart", function(e){
								$(this).parents("tr").find("td").css({"backgroundColor": "#CAE4FF" });
								$(this).parents("tr").next().find("td").css({"backgroundColor": "#CAE4FF" });
								$(this).parents("tr").next().next().find("td").css({"backgroundColor": "#CAE4FF" });
							});
							$("#dataList").find("tr.dataRow").next().find("td").bind("touchstart", function(e){
								$(this).parents("tr").find("td").css({"backgroundColor": "#CAE4FF" });
								$(this).parents("tr").prev().find("td").css({"backgroundColor": "#CAE4FF" });
								$(this).parents("tr").next().find("td").css({"backgroundColor": "#CAE4FF" });
							});
							$("#dataList").find("tr.dataRow").next().next().find("td").bind("touchstart", function(e){
								$(this).parents("tr").find("td").css({"backgroundColor": "#CAE4FF" });
								$(this).parents("tr").prev().find("td").css({"backgroundColor": "#CAE4FF" });
								$(this).parents("tr").prev().prev().find("td").css({"backgroundColor": "#CAE4FF" });
							});
							//터치종료 이벤트
							$("#dataList").find("tr.dataRow").find("td").bind("touchend", function(e){
								$(this).parents("tr").find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").next().find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").next().next().find("td").css({"backgroundColor": "#ffffff" });
							});
							$("#dataList").find("tr.dataRow").next().find("td").bind("touchend", function(e){
								$(this).parents("tr").find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").prev().find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").next().find("td").css({"backgroundColor": "#ffffff" });
							});
							$("#dataList").find("tr.dataRow").next().next().find("td").bind("touchend", function(e){
								$(this).parents("tr").find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").prev().find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").prev().prev().find("td").css({"backgroundColor": "#ffffff" });
							});
							//터치 후 화면을 움직일 경우 반전
							$("#dataList").find("tr.dataRow").find("td").bind("touchmove", function(e){
								$(this).parents("tr").find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").next().find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").next().next().find("td").css({"backgroundColor": "#ffffff" });
							});
							$("#dataList").find("tr.dataRow").next().find("td").bind("touchmove", function(e){
								$(this).parents("tr").find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").prev().find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").next().find("td").css({"backgroundColor": "#ffffff" });
							});
							$("#dataList").find("tr.dataRow").next().next().find("td").bind("touchmove", function(e){
								$(this).parents("tr").find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").prev().find("td").css({"backgroundColor": "#ffffff" });
								$(this).parents("tr").prev().prev().find("td").css({"backgroundColor": "#ffffff" });
							});
							
							$("#dataList").find("tr.dataRow").find("td").click(function(){
								var callSeq = $(this).parent().find("input[name='callSeq']").val();
								var itemSeq = $(this).parent().find("input[name='itemSeq']").val();
								var statusFlag = $(this).parent().find("input[name='statusFlag']").val();
								//상세조회화면으로 이동
								window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"callSeq", callSeq);
								window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"itemSeq", itemSeq);
								window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"statusFlag", statusFlag);

								//console.log("rpt110 click - A [callSeq : " + callSeq + ", itemSeq :" + itemSeq + ", statusFlag : " + statusFlag + "]");
								$.fn.showChildView("mnu_cal330", "");
							});
							$("#dataList").find("tr.dataRow").next().find("td").click(function(){
								var callSeq = $(this).parent().prev().find("input[name='callSeq']").val();
								var itemSeq = $(this).parent().prev().find("input[name='itemSeq']").val();
								var statusFlag = $(this).parent().prev().find("input[name='statusFlag']").val();
								//상세조회화면으로 이동
								window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"callSeq", callSeq);
								window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"itemSeq", itemSeq);
								window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"statusFlag", statusFlag);
								
								//console.log("rpt110 click - B [callSeq : " + callSeq + ", itemSeq :" + itemSeq + ", statusFlag : " + statusFlag + "]");
								$.fn.showChildView("mnu_cal330", "");
							});
							$("#dataList").find("tr.dataRow").next().next().find("td").click(function(){
								var callSeq = $(this).parent().prev().prev().find("input[name='callSeq']").val();
								var itemSeq = $(this).parent().prev().prev().find("input[name='itemSeq']").val();
								var statusFlag = $(this).parent().prev().prev().find("input[name='statusFlag']").val();
								//상세조회화면으로 이동
								window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"callSeq", callSeq);
								window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"itemSeq", itemSeq);
								window.sharedProxy.andrHashAddItem(H_LIST_TO_DETAIL,0,"statusFlag", statusFlag);
								
								//console.log("rpt110 click - C [callSeq : " + callSeq + ", itemSeq :" + itemSeq + ", statusFlag : " + statusFlag + "]");
								$.fn.showChildView("mnu_cal330", "");
							});
						});
						
						$("#emptyList").hide();
					} else {
						$("#emptyList").find("td").html("장애접수정보가 없습니다.");
						$("#emptyList").show();
					}
				} else {
					(new MsgBox(data.RETURN_MSG, "확인")).alert();
					return;
				}
			},
			error: function(request,status,error) {
				$.andrProgressHide();
				(new MsgBox("Parsing error:"+error, "확인")).alert();
				return;
			}
		});
	},
	
});