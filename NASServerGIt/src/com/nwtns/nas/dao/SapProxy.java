package com.nwtns.nas.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import com.nwtns.framework.util.NWUtil;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.rt.TableParameter;

public class SapProxy {

	static SapDao dao;
	static boolean isOpened;

	public SapProxy(){
		dao = new SapDao("sap.properties");
		isOpened = dao.open();
	}
	
	public String getCustCredit(String agencyCode){
		JCoFunction function = dao.getFunction("ZSD_CUST_CREDIT");
		JCoParameterList param = function.getImportParameterList();
		param.setValue("IKUNNR", agencyCode);
		dao.excute(function);
		JCoParameterList output = function.getTableParameterList();
		
		JCoTable table = output.getTable("TCUST");
		
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		if(table.getNumRows() > 0){
			table.firstRow();
			do{
				HashMap<String, String> item = new HashMap<String,String>();
				item.put("ORDER_AMT", table.getString("ORDER_AMT"));
				list.add(item);
			} while(table.nextRow());
			
		}
		
		if(list.size()>0){
			return list.get(0).get("ORDER_AMT");
		}else{
			return "";
		}
	}
	
	
	public ArrayList<HashMap<String, Object>> getStcokList(HashMap<String, Object> req){
		
		JCoFunction function = dao.getFunction("ZSD_ORDER_TRACKING_QUERY");
		
		JCoParameterList param = function.getImportParameterList();
		JCoParameterList output = function.getTableParameterList();
		param.setValue("IERDAT_FR", req.get("IERDAT_FR"));
		param.setValue("IERDAT_TO", req.get("IERDAT_TO"));
		param.setValue("ILUSTK"   , req.get("ILUSTK"));
	    param.setValue("IVKBUR"   , req.get("IVKBUR")); // 영업팀
        param.setValue("IKUNNR"   , req.get("IKUNNR")); // 판매처
        param.setValue("IMATNR"   , req.get("IMATNR")); // 자재번호
        param.setValue("IPRODH1"  , req.get("IPRODH1")); // 브랜드
        param.setValue("IPRODH2"  , req.get("IPRODH2")); // 브랜드라인
        String erpOrderNo = (String)req.get("erpOrderNo");
		dao.excute(function);
//		in_parameter
//		IBESTA                          ,BESTA                           ,C,1,0,1,2,0, ,문서품목 확정상태,IMPORT,OPTIONAL,BESTA
//		IERDAT_FR                       ,VBAK                            ,D,8,1,8,16,0, ,레코드생성일,IMPORT,OPTIONAL,VBAK,ERDAT
//		IERDAT_TO                       ,VBAK                            ,D,8,9,8,16,0, ,레코드생성일,IMPORT,OPTIONAL,VBAK,ERDAT
//		IKUNNR                          ,VBAK                            ,C,10,17,10,20,0, ,판매처,IMPORT,OPTIONAL,VBAK,KUNNR
//		ILFSTK                          ,ZLFSTK                          ,C,1,27,1,2,0, ,출하구분(Blank OR 'Y'),IMPORT,OPTIONAL,ZLFSTK
//		ILUSTK                          ,ZLUSTK                          ,C,1,28,1,2,0, ,미출구분(Blank OR 'Y'),IMPORT,OPTIONAL,ZLUSTK
//		IMATNR                          ,VBAP                            ,C,18,29,18,36,0, ,자재번호,IMPORT,OPTIONAL,VBAP,MATNR
//		IPRODH1                         ,ZPRODH1                         ,C,4,47,4,8,0, ,브랜드코드,IMPORT,OPTIONAL,ZPRODH1
//		IPRODH2                         ,ZPRODH2                         ,C,7,51,7,14,0, ,브랜드라인,IMPORT,OPTIONAL,ZPRODH2
//		IVDATU_FR                       ,VBAK                            ,D,8,58,8,16,0, ,납품요청일,IMPORT,OPTIONAL,VBAK,VDATU
//		IVDATU_TO                       ,VBAK                            ,D,8,66,8,16,0, ,납품요청일,IMPORT,OPTIONAL,VBAK,VDATU
//		IVKBUR                          ,VKBUR                           ,C,4,74,4,8,0, ,영업팀,IMPORT,OPTIONAL,VKBUR
//		IWADAT_FR                       ,LIKP                            ,D,8,78,8,16,0, ,출고전기일,IMPORT,OPTIONAL,LIKP,WADAT_IST
//		IWADAT_TO                       ,LIKP                            ,D,8,86,8,16,0, ,출고전기일,IMPORT,OPTIONAL,LIKP,WADAT_IST
		
//		out_parameter
//		VBELN                           ,VBELN_VA                        ,C,10,0,10,0,20,0,0,판매 문서
//		POSNR                           ,POSNR_VA                        ,N,6,10,6,10,12,20,0,판매 문서 품목
//		AUART                           ,AUART                           ,C,4,16,4,16,8,32,0,영업문서유형
//		ERDAT                           ,ERDAT                           ,D,8,20,8,20,16,40,0,레코드 생성일
//		KUNNR                           ,KUNAG                           ,C,10,28,10,28,20,56,0,판매처
//		NAME1                           ,NAME1_GP                        ,C,35,38,35,38,70,76,0,이름 1
//		KUNWE                           ,KUNWE                           ,C,10,73,10,73,20,146,0,납품처
//		NAME2                           ,NAME1_GP                        ,C,35,83,35,83,70,166,0,이름 1
//		MATNR                           ,MATNR                           ,C,18,118,18,118,36,236,0,자재 번호
//		MAKTX                           ,MAKTX                           ,C,40,136,40,136,80,272,0,자재내역
//		KWMENG                          ,ZKWMENG                         ,P,4,176,8,176,8,352,3,기본단위에서의 오더수량
//		VRKME                           ,VRKME                           ,C,3,180,3,184,6,360,0,판매 단위
//		NETWR                           ,NETWR                           ,P,4,183,8,187,8,366,2,전표 통화의 정가
//		LFIMG                           ,LFIMG                           ,P,4,187,7,195,7,374,3,실제수량납품 (판매단위)
//		NWERT                           ,ZNZN00                          ,P,3,191,6,202,6,381,0,출하금액
//		RFMNG                           ,RFMNG                           ,P,4,194,8,208,8,387,3,기본단위 참조수량
//		SALES_AMT                       ,ZNZN00                          ,P,3,198,6,216,6,395,0,출하금액
//		VDATU                           ,EDATU_VBAK                      ,D,8,201,8,222,16,402,0,납품요청일
//		WADAT_IST                       ,WADAT_IST                       ,D,8,209,8,230,16,418,0,실제 자재 이동일
//		ABGRU                           ,ABGRU                           ,C,2,217,2,238,4,434,0,견적 및 판매 오더의 거부 사유
//		BEZEI                           ,BEZEI20                         ,C,20,219,20,240,40,438,0,내역
//		SFSTK                           ,ZSFSTK                          ,C,10,239,10,260,20,478,0,주문상태
//		AUGRU                           ,AUGRU                           ,C,3,249,3,270,6,498,0,오더사유 (거래사유)
//		AUGRU_TXT                       ,BEZEI40                         ,C,40,252,40,273,80,504,0,내역
		
		
//        <option value="ZOR">일반주문입고</option>	
//        <option value="ZRES">활성반품</option>	
//        <option value="ZRED">비활성반품(단종/파손)</option>	
//        <option value="ZRER">반입</option>	
//        <option value="ZREX">재판매반품</option>	
//        <option value="ZORX">재판매주문</option></select>
		JCoTable table = output.getTable("TORDER");
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		System.out.println(table.toString());
		
		
		if(table.getNumRows()>0){
			for(int i = 0; i < table.getNumRows(); i++){
				HashMap<String, Object> hashMap = new HashMap<String,Object>();
				table.setRow(i);
				System.out.println(erpOrderNo+" "+table.getString("VBELN"));
				if(erpOrderNo.equals(table.getString("VBELN"))){//디비 erpOrderNo 번호와 sap VBELN 같은것만
					hashMap.put("VBELN",table.getString("VBELN"));
					hashMap.put("POSNR",table.getString("POSNR"));
					hashMap.put("AUART" ,table.getString("AUART"));
					hashMap.put("ERDAT",table.getString("ERDAT"));
					hashMap.put("KUNNR",table.getString("KUNNR"));
					hashMap.put("NAME1",table.getString("NAME1"));
					hashMap.put("KUNWE",table.getString("KUNWE"));
					hashMap.put("NAME2",table.getString("NAME2"));
					hashMap.put("MATNR",table.getString("MATNR"));
					hashMap.put("MAKTX",table.getString("MAKTX"));
					hashMap.put("VDATU",table.getString("VDATU"));
					hashMap.put("BEZEI",table.getString("BEZEI"));
					hashMap.put("SFSTK",table.getString("SFSTK"));
					hashMap.put("VRKME",table.getString("VRKME"));                  //출하금액                 출고금액
					hashMap.put("AUGRU_TXT",table.getString("AUGRU_TXT"));
					hashMap.put("KWMENG"   ,(int)table.getDouble("KWMENG"));        //오더수량                주문수량
					hashMap.put("NETWR"    ,(long)table.getDouble("NETWR"));        //전표 통화의 정가    주문금액
					hashMap.put("LFIMG"    ,(int)table.getDouble("LFIMG"));         //실제수량납품          출하수량
					hashMap.put("NWERT"    ,(long)table.getDouble("NWERT"));        //출하금액                 출하금액
					hashMap.put("RFMNG"    ,(int)table.getDouble("RFMNG"));         //기본단위 참조수량  출고수량
					hashMap.put("SALES_AMT",(long)table.getDouble("SALES_AMT"));    //출하금액                 출고금액
					System.out.println("-----------------------------------------------");
					System.out.println("주문수량 KWMENG:"+(int)table.getDouble("KWMENG"));
					System.out.println("주문금액 NETWR:"+(long)table.getDouble("NETWR"));
					System.out.println("출하수량 LFIMG:"+(int)table.getDouble("LFIMG"));
					System.out.println("출하금액 NWERT:"+(long)table.getDouble("NWERT"));
					System.out.println("출고수량 RFMNG:"+(int)table.getDouble("RFMNG"));
					System.out.println("출고금액 SALES_AMT:"+(long)table.getDouble("SALES_AMT"));
					System.out.println("-----------------------------------------------");				
				
					list.add(hashMap);
				}
			}
		}
		return list;
	}
	
}
