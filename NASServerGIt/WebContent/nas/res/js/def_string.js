/* ********************************************************************************
 * BY 최정주
 * 1. 공통처리를 위한 스크립트 파일
 * 2. 특정 페이지만을 위한 스크립트는 해당 폴더내 js 파일을 생성해서 정의하세요.
 * 
 * [2013-11-26] 최초작성 - 최정주
 * ******************************************************************************** */


//===========================================================================================
/* 전역변수 선언 규칙
 * 1. 대문자로 선언
 * 2. G_변수명으로 선언
 * */
 

//===========================================================================================
/* 사용자 정의 함수 
 * 호출시 $.getList(); 로 호출 가능
 * */
$.fn.extend({	
	// 문자열 공백 제거
	NWTrim : function (s){
		s += ''; // 숫자라도 문자열로 변환
		return s.replace(/^\s*|\s*$/g, '');
	},	

	commaSeparateNumber : function (val){
		//2015.11.10[KDBAEK] null체크 추가
		if(val == null || val == '')
			return "0";
		
	    while (/(\d+)(\d{3})/.test(val.toString())){
	      val = val.toString().replace(/(\d+)(\d{3})/, '$1'+','+'$2');
	    }
	    return val;
	},
	
	commaNum : function (num) {
		var minus = false;
		
		if (num < 0) { 
			num *= -1; 
			minus = true;
		}
		     
		var dotPos = (num+"").split(".");
		var dotU = dotPos[0];
		var dotD = dotPos[1];
		var commaFlag = dotU.length%3;
		var out = "";
		if(commaFlag) 
		{
			out = dotU.substring(0, commaFlag); 
			if (dotU.length > 3) out += ",";
		}
		    
		for (var i=commaFlag; i < dotU.length; i+=3) 
		{
			out += dotU.substring(i, i+3); 
			if( i < dotU.length-3) out += ",";
		}
		    
		if(minus) out = "-" + out;
		if(dotD)
			return out + "." + dotD;
		
		return out ;
    },
    
	nulltoString : function(str){
		if(str==null || str=="null" || jQuery.type(str)=="undefined"){
			return "";
		}else{
			return str;
		}
	},
	
	nulltoStr : function(str, replace){
		if(str =="" || str==null || str=="null" || jQuery.type(str)=="undefined" || str=="Infinity" || str=="NaN"){
			return replace;
		}else{
			return str;
		}
	},
	
	/**
	 * 필드를 테스트 한다
	 *
	 * @param     str   값
	 * @param     type  체크할 타입
	 * @return    true, false
	 */
	checkField : function(str, type) {
	    
	    switch(type)
	    {
		    /** 메일주소 (aaa@bbb.com) */
		    case 1 :
		        if(!str.value.match(/^(\S+)@(\S+)\.(\S+)/g)) return false;
		        break;
		    /** 숫자만 허용*/
	        case 2 :
	        	if(!str.value.match(/^[0-9]{0,}$/g)) return false;
	            break;
	        /** 영문 또는 영문/숫자 조합 */
	        case 3 :
	        	if(str.value.match(/^[0-9]{1,}$/g))				return false;
				else if(!str.value.match(/^[a-zA-Z0-9]+$/g))	return false;
	            break;
	        /** 전화번호 (XXX-XXXX-XXXX) */
	        case 4 :
	            if(!str.value.match(/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}$/g)) return false;
	            break;
	        /** 휴대폰번호 (XXX-XXXX-XXXX) */
	        case 5 :
	            if(!str.value.match(/^(010|011|016|017|018|019)-[0-9]{3,4}-[0-9]{3,4}$/g)) return false;
	            break;
	        /** 6~12자리  */
	        case 6 :
				if(!str.value.match(/^.{6,12}$/g))		return false;
	            break;
	        /** 숫자 또는 .  */
	        case 7 :
	        	if(str.value.match(/^[0-9]{1,3}.[0-9]{1,1}$/g)) return true;
	        	if(str.value.match(/^[0-9]{1,3}$/g)) 		  	return true;
	        	else return false;
	            break;
	        /** 숫자 4자리 미만  */
	        case 8 :
	        	if(str.value.match(/^[0-9]{4,}$/g)) return false;
	        	else return true;
	            break;
	        /** 숫자 4자리 이내  */
	        case 9 :
	        	if(str.value.match(/^[0-9]{5,}$/g)) return false;
	        	else return true;
	            break;
	        /** 2~8자리  */
	        case 10 :
				if(!str.value.match(/^.{2,8}$/g))		return false;
	            break;
	        /** 한글,영문,숫자  */
	        case 11 :
	        	if(!str.value.match(/^[ㄱ-힣a-zA-Z0-9 ]+$/g))	return false;
	            break;
	        case 12 :
	        	if(str.value.match(/^[0-9]{1,5}$/g)) {
	        		if(eval(str.value) >= 10) {
	        			return false;
	        		} else {
	        			return true;
	        		}
	        	}
	        	if(str.value.match(/^[0-9]{1,1}.[0-9]{1,3}$/g)) return true;
	        	else return false;
	            break;
		    /** Default */
		    default :
		        return false;
		        break;
	    }
	    return true;
	},
	
	lpad : function(value, len, padStr) {
		var str = value + "";
		
		while(str.length < len) {
			str = padStr + str;
		}
		
		return str;
	},
	
	rpad : function(value, len, padStr) {
		var str = value + "";
		
		while(str.length < len) {
			str = str + padStr;
		}
		
		return str;
	},
	
	/**
	 * 지정글자 (한글은 2글자 그 외는 1글자) 수 많큼 문자열 + 공백 혹은 공백 + 문자열로 리턴 됩니다.
	 * 		- ex1) PadStr('가나a', 8) OR PadStr('가나a', 8, 'L') 
	 * 		- 결과 = '가나a   '
	 * 		- ex2) PadStr('가나a', 8, 'R')
	 * 		- 결과 = '   가나a'
	 * 지정글자 (한글은 2글자 그 외는 1글자) 수 많큼 문자열을 잘라 리턴 합니다.
	 * 		- ex1) PadStr('자를문자열입니다.', 10, 'R')
	 * 		- 결과 = '자를문자열'
	 */
	PadStr : function(strData, nLeng, strdirec) {
		//var strWord = "";	//리턴할 문자열
		var sbWord = [];	//리턴할 문자열
		var strTemp = "";	//임시 문자열
		var nstrLength = 0;	//strData의 아스키 글자 길이를 구합니다.
		var asc = 0;	//해당아스키(Unicode 기준)
		
		//opt, 정렬 방향
		if(strdirec == null) { 
			strdirec = "L";
		}
		
		strTemp = String(strData); //문자열을 스트링으로 임시문자열에 반환
		strData = strTemp;
		
		//문자열  길이 구하기
		for(var i = 0; i < strData.length; i++) {	
			asc = strData.charCodeAt(i); //해당 문자아스키
			
			if(asc > 127) {
				nstrLength = nstrLength + 2;
			} else {
				nstrLength = nstrLength + 1;
			}
		}
		
		//입력문자열이 리턴할 길이보다 길 경우 - 문자열 자르는 PROC
		if(nLeng < nstrLength) {
			var nTempLeng = 0;
			
			for(var i = 0; i < strData.length; i++) {
				//해당 문자아스키
				asc = strData.charCodeAt(i); 
	    		
	    		if(asc > 127) {
	    			nTempLeng = nTempLeng + 2;
				} else {
	    			nTempLeng = nTempLeng + 1;
				} 
	    		
	    		//strWord = strWord + strData.charAt(i);
	    		sbWord.push(strData.charAt(i));
	    		if(nTempLeng == nLeng) {
	    			break;
    			} else if(nTempLeng == nLeng - 1) {
	    			if(strData.charCodeAt(i+1) > 127) {
	    				//strWord = strWord + " ";
	    				sbWord.push(" ");
	    				break;
    				} else {
	    				//strWord = strWord + strData.charAt(i + 1);
    					sbWord.push(strData.charAt(i + 1));
	    				break;
	    			}
    			}
			}
			
			//return strWord;
			return sbWord.join("");
		}
		
		//strWord = strData;
		//공백으로 매꾸는 PROC
		//for(var i = nstrLength; i < nLeng; i++) {
		//	if(strdirec == 'L') {
		//		strWord = strWord + ' ';
		//	} else {
		//		strWord = ' ' + strWord;
		//	}
		//}
		if(strdirec == 'L') {
			sbWord.push(strData);
			for(var i = nstrLength; i < nLeng; i++) {
				sbWord.push(" ");
			}
		} else {
			for(var i = nstrLength; i < nLeng; i++) {
				sbWord.push(" ");
			}
			sbWord.push(strData);
		}
		
		//return strWord;
		return sbWord.join("");
	},

	replaceAllstr : function(str, searchStr, replaceStr) {
		while (str.indexOf(searchStr) != -1) {
			str = str.replace(searchStr, replaceStr);
		}
		return str;
	},
	
	/**
	 * [JSONObject/JSONArray => JSONArray형식의 String으로 변경]
	 * @param json  : JSON형식의 Object/Array 데이터
	 * @param depth : 0
	 * @returns     : JSON형식의 String 데이터.
	 *                리턴값이 ''이 아닌경우 변환 성공으로 판단한다.
	 */
	toJSONString : function(jsonData, depth) {
		if(jsonData == null)
			return '';
		
	    var ary = new Array();
	    
	    for(var key in jsonData) {
	        var val = jsonData[key];
	        if(typeof(val)=='object')
	        	val = $.fn.toJSONString(val, depth+1);
	        else if(typeof(val)=='string')
	        	val = '"'+val+'"';
	        ary.push(depth == 0 ? val : key+':'+val);
	    }
	    
	    return (depth == 0 ? '['+ary.join()+']' : '{'+ary.join()+'}');
	},
	
	JSONObjectFromJSONArrayString : function(jsonData, depth) {
		if(jsonData == null)
			return '';
		
	    var ary = new Array();
	    
	    for(var key in jsonData) {
	        var val = jsonData[key];
	        if(typeof(val)=='object')
	        	val = $.fn.toJSONString(val, depth+1);
	        else if(typeof(val)=='string')
	        	val = '"'+val+'"';
	        ary.push(key+':'+val);
	    }
	    
	    return (depth == 0 ? '[{'+ary.join()+'}]' : '{'+ary.join()+'}');
	},
	strSplit:function(obj,len,spaceLen){
		var sbResult = [];
		var size = 0;
		var chr  = "";
		var cLen = 0;
		
		var sbSpace = [];
		for(var i =0;i<spaceLen;i++){
			sbSpace.push(" ");
		}
		
		for(var i = 0 ; i < obj.length ; i++ ) {
			  asc = obj.charCodeAt(i);
			  chr = obj.charAt(i);
	          if (asc < 127) cLen = 1; 
	          else           cLen = 2; 
	          if((size+cLen)<=len) {
	        	  sbResult.push(chr);
	        	  size += cLen;
	          } else {
	        	  sbResult.push(sbSpace.join(""));
	        	  sbResult.push(chr);
	        	  size = cLen;
	          }
		}
		
		return sbResult.join("");
		 
		/*
		var first  = "";
		var second ="";
		var space  = "";
		var result = "";
		var size   =0;
		var chr ="";
		for(var i = 0 ; i < obj.length ; i++ ) {
			  asc = obj.charCodeAt(i);
			  chr = obj.charAt(i);
	          if (asc < 127) size += 1; 
	          else size += 2; 
	          if(size<=len)
	        	  first += chr;
	          else 
	        	  second+= chr;
		 }
		for(var i =0;i<spaceLen;i++){
			space += " ";
		}
		 result = first+space+second;
		 return result;
		 */
	},
	
	 /**
	* 1. 함수명 : nullToObject
	* 2. 주기능 : Object null 체크
	* 3. Input Parameters
	*	1) obj (대상 Object)
	*	2) objName (Object명)
	* 4. Output Parameters
	* 	1) true/false
	*/
    nullToObject : function(obj, objName){
    	if($.fn.NWTrim($(obj).val()) == ""){
    		alert("["+objName + "]이(가) 없습니다.");
    		$(obj).focus();
    		return false;
    	}
    	return true;
    },
	
	getNumber:function(_val){
		if(_val == undefined){
			return 0;
		}
		_val += '';
		var num = parseFloat(_val.replace(/,/g,""));
		return $.isNumeric(num) ? num : 0;
		
	}
	
	
}); //END extend



