
var		m_nTotalCnt      = 0;		// 총 아이템 갯수
var		m_nOnePageCnt    = G_ROWCOUNT;		// 한페이지에 표시될 아이템 갯수
var		m_nCurrPage      = 1;		// 현재 페이지
var		m_nCurrPagsIndex = 1;		// 페이지단위 인덱스
var		m_nMaxPageCount  = 5;

/**
 * [조건]
 * 1) 부모페이지에 setTotalCount 구현
 * 2) 부모페이지에 searchList 구현
 * 3) 네비게이션 아이디 pagination
 */

$.fn.extend({	
	// navibar start **********************************************************************
	// FIRST, LEFT, RIGHT, LAST
	naviBar: function(state) {
		var tmp;
		//m_nTotalCnt = dataList[0].TOTCNT;
		
		console.log("naviBar-state : " + state)
		
		switch(state) {
		case "FIRST":
			if(1 <= (m_nCurrPagsIndex-1))
			{
				m_nCurrPagsIndex = 1;
				m_nCurrPage      = 1;
				$.fn.drawNavi();
				$.fn.searchList(m_nCurrPage);
				return true;
			}
			break;
		case "LEFT":
			if(1 <= (m_nCurrPagsIndex-1))
			{
				m_nCurrPagsIndex--;
				m_nCurrPage      = (m_nCurrPagsIndex-1) * m_nMaxPageCount + 1;
				$.fn.drawNavi(m_nCurrPage+m_nMaxPageCount-1);
				$.fn.searchList(m_nCurrPage+m_nMaxPageCount-1);
				return true;
			}
			break;
		case "RIGHT":
			nTmp = parseInt(m_nTotalCnt/(m_nOnePageCnt*m_nMaxPageCount)) + (m_nTotalCnt%(m_nOnePageCnt*m_nMaxPageCount)>0 ? 1 : 0 );
			if(nTmp >= (m_nCurrPagsIndex+1))
			{
				m_nCurrPagsIndex++;
				m_nCurrPage      = (m_nCurrPagsIndex-1) * m_nMaxPageCount + 1;
				$.fn.drawNavi();
				$.fn.searchList(m_nCurrPage);
				return true;
			}
			break;
		case "LAST":
			nTmp = parseInt(m_nTotalCnt/(m_nOnePageCnt*m_nMaxPageCount)) + (m_nTotalCnt%(m_nOnePageCnt*m_nMaxPageCount)>0 ? 1 : 0 );
			if(nTmp >= (m_nCurrPagsIndex+1))
			{
				m_nCurrPagsIndex = nTmp;
				m_nCurrPage      = (m_nCurrPagsIndex-1) * m_nMaxPageCount + 1;
				$.fn.drawNavi();
				$.fn.searchList(m_nCurrPage);
				return true;
			}
			break;
		}
		return false;
	},
	
	drawNavi: function(oldPage) {
		//console.log("drawNavi start")
		//console.log("oldPage:"+oldPage);
		if(oldPage == undefined || oldPage == "undefined") {
			oldPage = m_nCurrPage;
		}
		oldPage = parseInt(oldPage);
		
		var		nCalPage = 0;
		var		nTmp = parseInt(m_nTotalCnt/m_nOnePageCnt) + (m_nTotalCnt%m_nOnePageCnt>0 ? 1 : 0 );
		
		var pageNavi = "";
		pageNavi += '<li onclick="$.fn.naviBar(' + "'FIRST'" + ')"><a>&laquo;</a></li>';
		pageNavi += '<li onclick="$.fn.naviBar(' + "'LEFT'" + ')"><a>&lsaquo;</a></li>';
		
		//console.log("nTmp:"+nTmp);
		//console.log("m_nMaxPageCount:"+m_nMaxPageCount);
		//console.log("m_nCurrPage:"+m_nCurrPage);
		
		for(var i=0;i<m_nMaxPageCount;i++)
		{
			nCalPage = 1 + ((m_nCurrPage-1)/m_nMaxPageCount)*m_nMaxPageCount + i;
			console.log("nCalPage/m_nCurrPage:" + nCalPage + "/" + m_nCurrPage)
			
			//if((m_nCurrPage+i) > nTmp)		break;
			if(nCalPage > nTmp)		break;

			if(oldPage==nCalPage)	pageNavi += '<li onclick="$.fn.searchList('+Number(nCalPage)+');"><a style="color:#fff;background-color:#337ab7">'+(Number(nCalPage))+'</a></li>';
			else               		pageNavi += '<li onclick="$.fn.searchList('+Number(nCalPage)+');"><a>'+(Number(nCalPage))+'</a></li>';
		}
		
		pageNavi += '<li onclick="$.fn.naviBar(' + "'RIGHT'" + ')"><a>&rsaquo;</a></li>';
		pageNavi += '<li onclick="$.fn.naviBar(' + "'LAST'" + ')"><a>&raquo;</a></li>';
		
		$("#pagination").html(pageNavi);
		
		//console.log("drawNavi end")
	},
	
	setTotalCount: function(cnt) {
		m_nTotalCnt = cnt;
	}
	// navibar end **********************************************************************
	
});