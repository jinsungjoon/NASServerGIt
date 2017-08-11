package com.nwtns.nas.parser;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.jdom.xpath.*;

/**
 * 
 * <pre>
 * 1.기능 :  XML 파싱을 위한 util 클래스. <br>
 * 2.처리 개요
 *     - 
 *     - 
 * 3.주의사항 
 *     -
 * </pre>
 */
public class UtilJDom {

	/** 2005.12.27 min */
	public static Document defaultXmlDocument = null;
	
	/** 2006.8.20 leejh - element의 length를 구할때 사용되는 XMLOutputter 재사용 */
	private static XMLOutputter rawFormatOutputter = null;
	
	public static byte[] testXml = "<?xml version=\"1.0\" encoding=\"EUC-KR\"?><MciMessage><IntegrationHeaders type=\"recv\"><IntegrationHeader type=\"common\"><Fld id=\"Channelid\" value=\"01\" /><Fld id=\"Channelsubid\" value=\"01\" /><Fld id=\"Securesessionid\" value=\"6381709\" /><Fld id=\"MCIsessionId\" value=\"\" /><Fld id=\"IpAddress\" value=\"172.17.99.5\" /><Fld id=\"Servicemode\" value=\"T\" /><Fld id=\"MCItrancode\" value=\"2010401101\" /><Fld id=\"Servicehost\" value=\"CRM\" /><Fld id=\"MCIseqno\" value=\"900974000014\" /><Fld id=\"Bypass\" value=\"0\" /><Fld id=\"LangCd\" value=\"KOR\" /><Fld id=\"Cryptoflag\" value=\"2\" /><Fld id=\"MCIErrorCode\" value=\"\" /><Fld id=\"MCIErrorDesc\" value=\"\" /></IntegrationHeader><IntegrationHeader type=\"channel\"><Fld id=\"CardBrncd\" value=\"\" /><Fld id=\"CardTrmno\" value=\"\" /><Fld id=\"CardOprtrNo\" value=\"\" /><Fld id=\"CardEmpid\" value=\"\" /><Fld id=\"Termno\" value=\"\" /><Fld id=\"TmlName\" value=\"9009WS03\" /><Fld id=\"SignOnMode\" value=\"ee\" /><Fld id=\"OPMPath\" value=\"ff\" /><Fld id=\"BizAuth\" value=\"gg\" /><Fld id=\"BranchType\" value=\"hh\" /><Fld id=\"Scnno\" value=\"CRM_2010401101\" /><Fld id=\"Trancd\" value=\"A\" /><Fld id=\"Sbjcd\" value=\"080\" /><Fld id=\"Opid\" value=\"81\" /><Fld id=\"TmlMedia\" value=\"TD\" /><Fld id=\"TranSeqno\" value=\"5459\" /><Fld id=\"TranFlag\" value=\"\" /><Fld id=\"TranDesc\" value=\"\" /><Fld id=\"MassFlag\" value=\"\" /><Fld id=\"PfKey\" value=\"\" /><Fld id=\"AcnoBrno\" value=\"\" /><Fld id=\"PbMsSeqno\" value=\"\" /><Fld id=\"PbMsCustccd\" value=\"\" /><Fld id=\"Custgubun\" value=\"\" /><Fld id=\"IccardFlag\" value=\"\" /><Fld id=\"PinpadInput\" value=\"\" /><Fld id=\"SecurityCard\" value=\"\" /><Fld id=\"DualMonitor\" value=\"\" /><Fld id=\"IcType\" value=\"\" /><Fld id=\"IcChipno\" value=\"\" /><Fld id=\"IcAcnover\" value=\"\" /><Fld id=\"OvrFlag\" value=\"\" /><Fld id=\"OvrType\" value=\"\" /><Fld id=\"FirSupno\" value=\"\" /><Fld id=\"FirSupBrno\" value=\"\" /><Fld id=\"FirSupEmpno\" value=\"\" /><Fld id=\"FirSupName\" value=\"\" /><Fld id=\"SecSupno\" value=\"\" /><Fld id=\"SecSupBrno\" value=\"\" /><Fld id=\"SecSupEmpno\" value=\"\" /><Fld id=\"SecSupName\" value=\"\" /><Fld id=\"SupPwd\" value=\"\" /><Fld id=\"OvrStatus\" value=\"\" /><Fld id=\"fixManagerTerminalssWS\" value=\"\" /><Fld id=\"fixManagerIP\" value=\"\" /><Fld id=\"Tokenverifier\" value=\"bS84+N1Bzbz2amrWdGRxG0j/zuw=\" /><Fld id=\"Securetoken\" value=\"kdDPlHA2gaqJPhMwgcSJB6nDp2xmB+jvZ2tQks+FbBWQaZ3keJNwHnURGWvjEq+SkGmd5HiTcB51ERlr4xKvkpBpneR4k3AedREZa+MSr5KQaZ3keJNwHnURGWvjEq+SkGmd5HiTcB51ERlr4xKvkpBpneR4k3AedREZa+MSr5KQaZ3keJNwHnURGWvjEq+SkGmd5HiTcB51ERlr4xKvkpBpneR4k3AedREZa+MSr5JfIQHt9mjotVt+dNY/WqUt\" /></IntegrationHeader></IntegrationHeaders><MsgBody Taskcount=\"1\" wfrefID=\"\" wfrefCols=\"\"><Task number=\"1\" id=\"\" rtcode=\"\"><Device type=\"screen\" msg=\"\" clear=\"N\"><Fld id=\"hdfOID\" value=\"100023137342\"></Fld><Fld id=\"hdfSeqNo\" value=\"1\"></Fld><Fld id=\"CustClasCd\" value=\"0\"></Fld><Fld id=\"hdfCustClasCd\" value=\"0\"></Fld><Fld id=\"IdClascd\" value=\"01\"></Fld><Fld id=\"ID\" value=\"9999999999999\"></Fld><Fld id=\"RealBirthDate\" value=\"19990210\"></Fld><Fld id=\"RealBirthDateYear\" value=\"1999\"></Fld><Fld id=\"RealBirthDateMonth\" value=\"02\"></Fld><Fld id=\"SlrLncClasCd\" value=\"1\"></Fld><Fld id=\"SexClasCd\" value=\"2\"></Fld><Fld id=\"NationalISOCode\" value=\"KR\"></Fld><Fld id=\"NationalISOCodeDesc\" value=\"한국\"></Fld><Fld id=\"ResidenceNationalISOCode\" value=\"KR\"></Fld><Fld id=\"ResidenceNationalISOCodeDesc\" value=\"한국\"></Fld><Fld id=\"HouseZipCd\" value=\"121776\"></Fld><Fld id=\"HouseZipCd1\" value=\"121\"></Fld><Fld id=\"HouseZipCd2\" value=\"776\"></Fld><Fld id=\"HouseZipCdSeqNo\" value=\"01\"></Fld><Fld id=\"HouseAddress1\" value=\"서울 종로구 내수동\"></Fld><Fld id=\"TransactorClasCd\" value=\"015220100\"></Fld><Fld id=\"TransactorClasCdDesc\" value=\"일반개인\"></Fld><Fld id=\"HouseTelephoneNationalNumber\" value=\"082\"></Fld><Fld id=\"HouseTelephoneAreaNumber\" value=\"02\"></Fld><Fld id=\"HouseTelephoneNumber1\" value=\"703\"></Fld><Fld id=\"HouseTelephoneNumber2\" value=\"8986\"></Fld><Fld id=\"HouseTelephoneExtensionNumber\" value=\"\"></Fld><Fld id=\"HandPhoneNationalNumber\" value=\"082\"></Fld><Fld id=\"HandPhoneAreaNumber\" value=\"019\"></Fld><Fld id=\"HandPhoneNumber1\" value=\"9161\"></Fld><Fld id=\"HandPhoneNumber2\" value=\"8986\"></Fld><Fld id=\"HouseAllLifeAreaNumber\" value=\"\"></Fld><Fld id=\"HouseAllLifeNumber1\" value=\"\"></Fld><Fld id=\"HouseAllLifeNumber2\" value=\"\"></Fld><Fld id=\"CompanyZipCode\" value=\"136092\"></Fld><Fld id=\"CompanyZipCode1\" value=\"136\"></Fld><Fld id=\"CompanyZipCode2\" value=\"092\"></Fld><Fld id=\"CompanyZipCodeSeqNo\" value=\"01\"></Fld><Fld id=\"CompanyAddress1\" value=\"서울 성북구 종암2동\"></Fld><Fld id=\"CompanyAddress2\" value=\"8-2\"></Fld><Fld id=\"CompanyTelephoneNationalNumber\" value=\"082\"></Fld><Fld id=\"CompanyTelephoneAreaNumber\" value=\"02\"></Fld><Fld id=\"CompanyTelephoneNumber1\" value=\"2073\"></Fld><Fld id=\"CompanyTelephoneNumber2\" value=\"6021\"></Fld><Fld id=\"CompanyFaxNationalNumber\" value=\"\"></Fld><Fld id=\"CompanyFaxAreaNumber\" value=\"\"></Fld><Fld id=\"CompanyFaxNumber1\" value=\"\"></Fld><Fld id=\"CompanyFaxNumber2\" value=\"\"></Fld><Fld id=\"CompanyName\" value=\"\"></Fld><Fld id=\"DepartmentName\" value=\"\"></Fld><Fld id=\"JobTitle\" value=\"\"></Fld><Fld id=\"EmailAddress\" value=\"BLUESKY0@kbstar.com\"></Fld><Fld id=\"EmailReceiptFg\" value=\"0\"></Fld><Fld id=\"MprodRctPlcClasCd\" value=\"1\"></Fld><Fld id=\"SMSReceiptFg\" value=\"0\"></Fld><Fld id=\"TelCntcInhiClasCd\" value=\"\"></Fld><Fld id=\"TelCntcInhiClasDesc\" value=\"\"></Fld><Fld id=\"RegistrationBrCd\" value=\"\"></Fld><Fld id=\"RegistrationBrName\" value=\"\"></Fld><Fld id=\"RegisterEmpPsnlNo\" value=\"\"></Fld><Fld id=\"RegisterEmpName\" value=\"\"></Fld><Fld id=\"AdministrationBrcd\" value=\"0484\"></Fld><Fld id=\"AdministrationEmpPsnlNo\" value=\"\"></Fld><Fld id=\"AdministrationBrName\" value=\"종암동\"></Fld><Fld id=\"AdministrationEmpName\" value=\"\"></Fld><Fld id=\"ClrnObjFg\" value=\"\"></Fld><Fld id=\"NewSsn\" value=\"\"></Fld><Fld id=\"ClrnFg\" value=\"\"></Fld><Fld id=\"ClrnMdfDt\" value=\"\"></Fld><Fld id=\"ClrnBrcd\" value=\"\"></Fld><Fld id=\"ClrnOprrNo\" value=\"\"></Fld><Fld id=\"WeddingClas\" value=\"0\"></Fld><Fld id=\"WeddingDate\" value=\"\"></Fld><Fld id=\"WdMmdSlrLnrClasCd\" value=\"\"></Fld><Fld id=\"YearOfWeddingDate\" value=\"\"></Fld><Fld id=\"MonthOfWeddingDate\" value=\"\"></Fld><Fld id=\"DayOfWeddingDate\" value=\"\"></Fld><Fld id=\"PbAdminBrcd\" value=\"\"></Fld><Fld id=\"PbCustInfoProtFg\" value=\"0\"></Fld><Fld id=\"PbCustFg\" value=\"0\"></Fld><Fld id=\"WorktogetherYn\" value=\"\"></Fld><Fld id=\"JobClsCd\" value=\"\"></Fld><Fld id=\"JobClsDesc\" value=\"\"></Fld><Fld id=\"YearIncomeDistinctionCode\" value=\"\"></Fld><Fld id=\"YearIncomeDistinctionDesc\" value=\"\"></Fld><Fld id=\"HouseOwnDistinctionCode\" value=\"\"></Fld><Fld id=\"HouseOwnDistinctionDesc\" value=\"\"></Fld><Fld id=\"ResidenceFormDistinctionCode\" value=\"\"></Fld><Fld id=\"ResidenceFormDistinctionDesc\" value=\"null\"></Fld><Fld id=\"VehicleEngineDisplacementDistinctionCode\" value=\"\"></Fld><Fld id=\"VehicleEngineDisplacementDistinctionDesc\" value=\"\"></Fld><Fld id=\"HobbyDistinctionCode\" value=\"\"></Fld><Fld id=\"HobbyDistinctionDesc\" value=\"\"></Fld><Fld id=\"DepsPromsCustFg\" value=\"\"></Fld><Fld id=\"KookminCardOwnFg\" value=\"\"></Fld><Fld id=\"PlatinumOwnFg\" value=\"\"></Fld><Fld id=\"TopClassKindClasCd\" value=\"\"></Fld><Fld id=\"KookminCardCompanyCd\" value=\"\"></Fld><Fld id=\"hdfJobTitleTemp\" value=\"\"></Fld></Device></Task></MsgBody></MciMessage>".getBytes();
	
	static {
		//System.out.println("jDomUtil.......");
		SAXBuilder builder = new SAXBuilder();
		//builder.setIgnoringElementContentWhitespace(true);
		String data = "<?xml version=\"1.0\" encoding=\"EUC-KR\"?><RootMessage/>";
		StringReader in = new StringReader(data);
		
		try {
			defaultXmlDocument = builder.build(in);
		} catch (Exception e1) {e1.printStackTrace();	}
		
		 
		Format format = Format.getRawFormat();
		format.setEncoding("EUC-KR");
		rawFormatOutputter = new XMLOutputter(format);
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : XML contents 를 통해 Root Element 를 반환한다. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param data
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Element getRootElement(byte[] data) throws JDOMException, IOException	{
		return getRootElement(new String(data).trim());
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : XML contents 를 통해 Root Element 를 반환한다. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param data
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Element getRootElement(String data) throws JDOMException, IOException	{
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		Element root = null;
		
		try	{
			doc = builder.build(new StringReader(data));
			root = doc.getRootElement();
		}
		catch (JDOMException e) { throw e; }
		catch (IOException e) { throw e; }
		finally {
			doc = null;
			builder = null;
		}
		
		return root;
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : XML contents 를 통해 Root Element 를 반환한다. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param is
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Element getRootElement(InputStream is) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		Element root = null;
		
		try	{
			doc = builder.build(is);
			root = doc.getRootElement();
		}
		catch (JDOMException e) { throw e; }
		catch (IOException e) { throw e; }
		finally {
			doc = null;
			builder = null;
		}
		
		return root;
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : XML 파일을 읽어 root Element를 반환한다. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param file
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Element getRootElement(File file)	throws JDOMException, IOException {
		FileInputStream fis = null;
		//
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		Element root = null;
		
		try	{
			fis = new FileInputStream(file);
			doc = builder.build(fis);
			root = doc.getRootElement();
		}
		catch (JDOMException e) { throw e; }
		catch (IOException e) { throw e; }
		finally {
			try {
				if ( fis != null ) 
					fis.close();
			} catch (IOException e) {}
			fis = null;
			doc = null;
			builder = null;
		}
		
		return root;
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : 특정 노드의 Child Node 에서 특정 Element 의 속성 값이 일치 하는 것을 카운트 한다. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param node
	 * @param compareAtt
	 * @param compareAttValue
	 * @return
	 */
	public static int countChilds(Element node, String compareAtt, String compareAttValue) {
		int total = 0;
		
		List children = node.getChildren();
		Iterator item = children.iterator();
		while(item.hasNext())
		{
			Element element = (Element)item.next();
			String isRight = element.getAttributeValue(compareAtt);
			if ( isRight.equals(compareAttValue) )
				total++;
		}
		
		return total;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : 현재 Element 의 자식 Element 에서 특정 속성값을 반환한다. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     - 만약, 찾지 못하면 null 을 반환한다
	 *</pre>
	 * @param 	node Element
	 * @param 	compareAtt 비교할 속성 필드명
	 * @param 	compareValue 비교할 속성 필드값
	 * @param 	findAtt 찾을 속성 필드명
	 * @return
	 */
	public static String findChildAttributeValue(Element node, String compareAtt, String compareValue, String findAtt)
	{
		String value = null;
		List children = node.getChildren();
		Iterator item = children.iterator();
		
		while(item.hasNext())
		{
			Element element = (Element)item.next();
			String isRight = element.getAttributeValue(compareAtt);
			
			if ( isRight.equals(compareValue) )
			{
				value = element.getAttributeValue(findAtt);
				break;
			}
		}
		
		return value;
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : 직접 해당 PATH  를 통해서 값을 검색하여 가져 올 수 있다. <br>
	 * 2.처리 개요
	 *     - path 사용법]
	 * 	     1) //XXXX : root node 부터 검색을 실시..
	 * 	     2) /XXXX 	: 에러 발생 가능성 있음
	 * 	     3) .//XXX	: 현재 노드를 root 로 하여 이하를 검색한다.
	 * 3.주의사항 
	 *     - 만약에 중간의 node 를 root 하여 검색하고자 한다면 .// 을 사용해야 한다
	 *     - 이 때 // path 를 준다면 최상위 root node 부터 검색을 실시 한다
	 *</pre>
	 * @param node
	 * @param path
	 * @param attributeName
	 * @return
	 * @throws JDOMException
	 */
	public static String findSinglenodeAttributeValue(Element node, String path, String attributeName) throws JDOMException	{
		return ((Element) XPath.newInstance(path).selectSingleNode(node)).getAttributeValue(attributeName);
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : 사용법: jDomUtil.findSinglenode(ActivityNode, "//Activity/","Type","START") . <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param node
	 * @param path
	 * @param name
	 * @param value
	 * @return
	 * @throws JDOMException
	 */
	public static Element findSinglenode(Element node, String path, String name, String value) throws JDOMException
	{
		// "//Activity/[@Type='START']" -> 
		
//		System.out.println("==>"+path+"[@"+name+"='"+value+"'] -> " + node);
		
		return (Element) XPath.newInstance(path+"[@"+name+"='"+value+"']").selectSingleNode(node);
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : 특정 하위 노드의 Children 을 반환한다. <br>
	 * 2.처리 개요
	 * 예제)
	 * <ExtendedAttribute Type="Default">
					<MessageFormatTransform Value="true">
						<MessageMappingRuleId Value="XXXXXXXXXX"/>
					</MessageFormatTransform>
					<CodeConversion Value="true">
						<CodeConversionPart Value="3"/>
						<CodeConversionType Value="1"/>
					</CodeConversion>
				</ExtendedAttribute>
	 * 검색하는 예제
	 * findSelecteNode( Element ,"//CodeConversionPart")
	 * findSelecteNode( Element ,"//ExtendedAttribute/CodeConversion/CodeConversionPart")
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param node
	 * @param path
	 * @return
	 * @throws JDOMException
	 */
	public static List findSelecteNode(Element node, String path) throws JDOMException
	{
		List nodeLists = XPath.newInstance(path).selectNodes(path);
		
		return nodeLists;
	}
	
	
	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : byte[]을 Document로 생성하는 메소드. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Document buildXml(byte[] xml) throws Exception
//	throws Exception
	{
		
		StringReader in = new StringReader(new String(xml));		
//		SAXBuilder builder = new SAXBuilder("weblogic.apache.xerces.parsers.SAXParser");
//		SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
		SAXBuilder builder = new SAXBuilder();
//		System.out.println("***************************driver=" + builder.getDriverClass());
		Document doc = null;
		try {
			doc = builder.build(in);
		} catch (Exception e1) {
			throw e1;
			//e1.printStackTrace();
		}
//		try{
//		}catch(Exception e){
//			System.out.println("jDomUtil.buildXml> XML 파싱 에러닷..");
//			System.out.println(new String(xml));
//			e.printStackTrace();
//			return null;//2005.11.29 min
//		}
		return doc;
	}
	
	public static Document buildXml2(byte[] xml) throws Exception {
		SAXParserFactory spf = SAXParserFactory.newInstance();
//		spf.setNamespaceAware(true);
//		spf.setXIncludeAware(xincludeProcessing);
//		spf.setSchema(schema);
//          spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, secureProcessing);
          
		// Create a SAXParser
		SAXParser sp = spf.newSAXParser();
          
		StringReader in = new StringReader(new String(xml));		
  		
		SAXBuilder builder = new SAXBuilder(sp.getClass().getName());
		Document doc = null;
  		try {
  			doc = builder.build(in);
  		} catch (Exception e1) {
  			throw e1;
  		}
  		return doc;
	}
	/**
	 * 
	 * <pre>
	 * 1.기능 : File을 Document로 생성하는 메소드. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param filename
	 * @return
	 */
	public static Document buildXml(File filename){
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try{
			doc = builder.build(filename);
		}catch(Exception e){
			e.printStackTrace();
		}
		return doc;
	}
	
	
	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Document 객제가 담고 있는 모든 데이타를 문자열로 반환한다. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @return
	 */
	public static String document2String(Document doc)
	{
//		Format format = Format.getPrettyFormat();
		Format format = Format.getRawFormat();
		format.setEncoding("EUC-KR");
		XMLOutputter outputter = new XMLOutputter(format);
		
		
		return outputter.outputString(doc);
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : . <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @return
	 */
	public static String document2PrettyString(Document doc)
	{
		Format format = Format.getPrettyFormat();
//		Format format = Format.getRawFormat();
		format.setEncoding("EUC-KR");
		XMLOutputter outputter = new XMLOutputter(format);
		
		
		return outputter.outputString(doc);
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Element ---> String 변환 . <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @return
	 */
	public static String element2String(Element doc)
	{
//		Format format = Format.getPrettyFormat();
		Format format = Format.getRawFormat();
		format.setEncoding("EUC-KR");
		XMLOutputter outputter = new XMLOutputter(format);
		
		return outputter.outputString(doc);
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : . <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @return
	 */
	public static String element2PrettyFormatString(Element doc) {
		Format format = Format.getPrettyFormat();
		format.setEncoding("EUC-KR");
		XMLOutputter outputter = new XMLOutputter(format);
		
		return outputter.outputString(doc);
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : . <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param element
	 * @return
	 */
	public static int elementLength(Element element) {
//		Format format = Format.getRawFormat();
//		format.setEncoding("EUC-KR");
//		XMLOutputter outputter = new XMLOutputter(format);
//		
		String str = rawFormatOutputter.outputString(element);
//		String str = outputter.outputString(element);
		return str.getBytes().length;
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Document를 printout하는 메소드. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 */
	public static void printXml(Document doc){
		Format format = Format.getPrettyFormat();
		format.setEncoding("EUC-KR");
		XMLOutputter outputter = new XMLOutputter(format);
	    try {   
	    	 outputter.output(doc, System.out); 
	    } catch (java.io.IOException e) {  
	    	//throw new MessageFormaterException ("XML 출력 중 에러가 발생했습니다.  Cause: " + e, e);
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Element를 printout하는 메소드. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 */
	public static void printXmlElement(Element doc){
		Format format = Format.getPrettyFormat();
		format.setEncoding("EUC-KR");
		XMLOutputter outputter = new XMLOutputter(format);
	    try {   
	    	 outputter.output(doc, System.out); 
	    } catch (java.io.IOException e) {  
	    	//throw new MessageFormaterException ("XML 출력 중 에러가 발생했습니다.  Cause: " + e, e);
	    	e.printStackTrace();
	    }
	}	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : attributeValue 검색 메소드. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @param path
	 * @param attributename
	 * @return
	 */
	public static String getSingleValue(Document doc, String path, String attributename){
		String rst = "";
		try{
			rst = ((Element)XPath.newInstance(path).selectSingleNode(doc)).getAttributeValue(attributename);
		}catch(JDOMException e){
			e.printStackTrace();
		}
		return rst;
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : attributeValue 검색 메소드. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param element
	 * @param path
	 * @param attributename
	 * @return
	 */
	public static String getSingleValue(Element element, String path, String attributename){
		String rst = "";
		try{
			rst = ((Element)XPath.newInstance(path).selectSingleNode(element)).getAttributeValue(attributename);
		}catch(JDOMException e){
			e.printStackTrace();
		}
		return rst;
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Element 검색 메소드. <br>
	 * 2.처리 개요
	 *     - XPath를 사용하해서 Element를 search한다.
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @param path
	 * @return
	 */
	public static Element getElement_XPath(Document doc, String path){
		Element element = null;
		try{
			Object obj = XPath.newInstance(path).selectSingleNode(doc);
			if (obj instanceof Element) {
				element = (Element)obj;
			}
		}catch(JDOMException e){
			e.printStackTrace();
		}
		return element;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Element 검색 메소드. <br>
	 * 2.처리 개요
	 *     - XPath를 사용하해서 Element를 search한다.
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param e
	 * @param path
	 * @return
	 */
	public static Element getElement_XPath(Element e, String path){
		Element element = null;
		try{
			element = (Element)XPath.newInstance(path).selectSingleNode(e);
		}catch(JDOMException e1){
			e1.printStackTrace();
		}
		return element;
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : 1단계의 element만 찾을때 사용 ex> //HugeGrid. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @param path
	 * @return
	 */
	private static Element getElementNonXPath1Depth(Document doc, String path) {
		return getElementNonXPath1Depth(doc.getRootElement() , path);
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : 1단계의 element만 찾을때 사용 ex> //HugeGrid. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param e
	 * @param path
	 * @return
	 */
	private static Element getElementNonXPath1Depth(Element e, String path) {
		
		try {
			String newPath = path.replaceAll("/","");
			
			if (newPath.equals(e.getName())) {
				return e;
			}
			
			List childList = e.getChildren();			
			Iterator iter = childList.iterator();
			Element childElement = null;
			Iterator backIter = null;
			
			while(iter.hasNext()) {
				childElement = (Element)iter.next();
				if(newPath.equals(childElement.getName())) {
					return childElement;
				}
				else {
					childList = childElement.getChildren();
					if ( childList != null && childList.size()>0) {
						backIter = iter;
						iter = childList.iterator();
					}
					else {
						if(backIter != null) {
							iter = backIter;	
						}
						
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Element 검색 메소드. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @param path
	 * @return
	 */
	//public static Element getElementNonXPath(Document doc, String path) {
	public static Element getElement2(Document doc, String path) {
		//return getElementNonXPath(doc.getRootElement() , path);
		return getElement2(doc.getRootElement() , path);
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Element 검색 메소드. <br>
	 * 2.처리 개요
	 *     - 속도를 위해 약간의 제약사항응 두었음.(&주의사항 참조)
	 *     - 
	 * 3.주의사항 
	 *     - 최상위 Element가 포함된 path 가 들어와야 한다. 
	 *     ex>표준전문에서 MciMessage/IntegrationHeaders/IntegrationHeader/Fld[@id='MCIseqno'] 는 되고
	 *        IntegrationHeader/Fld[@id='MCIseqno'] 는 찾지 못한다.
	 *     - 상대path 로 검색시에는 getElement2 를 사용할 것.   
	 *</pre>
	 * @see getElement2
	 * @param e
	 * @param path
	 * @return
	 */
	//public static Element getElementNonXPath(Element e, String path) {
	public static Element getElement2(Element e, String path) {
		
		try {
			
//			UtilJDom.printXmlElement(e);
//			String newPath = path.replaceAll("//","");
			String newPath = path;
			if(newPath.startsWith("//")) {			newPath = newPath.substring(2);			}
			else if (newPath.startsWith("/")) {		newPath = newPath.substring(1);			}
			
			String paths[] = newPath.split("/");
			int pathSize = paths.length -1;//마지막인지 비교하기위해 -1 한다.
			int pathIndex = 0;
			
			String condition;
			String []conditions = null;
			int idx = paths[pathSize].indexOf('[');
			if (idx > -1) {
				int end = paths[pathSize].indexOf(']');
				condition = paths[pathSize].substring(idx+1,end);
				conditions = condition.split("=");
				conditions[0] = conditions[0].replaceAll("@","");
				conditions[1] = conditions[1].replaceAll("'","");
				
				paths[pathSize] = paths[pathSize].substring(0,idx);
			}
			
			if (pathSize ==0 ) {
				return getElementNonXPath1Depth(e , path);
			}
			
			List childList = e.getChildren();			
			Iterator iter = childList.iterator();
			Element childElement = null;
			
			if (e.getName().equals(paths[0])) {
				pathIndex++;
			}
			
			while(iter.hasNext()) {
				childElement = (Element)iter.next();				
				if(paths[pathIndex].equals(childElement.getName())) {
					if (pathIndex == pathSize) {
						if(idx>-1) {
							if (conditions[1].equals(childElement.getAttributeValue(conditions[0])) ) {
								return childElement;
							}
						}
						else {
							return childElement;	
						}
					}
					else {
						pathIndex++;
						childList = childElement.getChildren();
						if ( childList != null) {
							iter = childList.iterator();
						}
						else {
//							return getElement2(e,path);
							return null;
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//return getElement2(e,path);
		return null;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Element 검색 메소드. <br>
	 * 2.처리 개요
	 *     - XPath search 에 가깝게 구현.
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @param path
	 * @return
	 */
	//public static Element getElementNonXPath(Document doc, String path) {
	public static Element getElement(Document doc, String path) {
		//return getElementNonXPath(doc.getRootElement() , path);
		return getElement(doc.getRootElement() , path);
	}
	
	/**
	 * 
	 * <pre>
	 * 1.기능 : Element 검색 메소드. <br>
	 * 2.처리 개요
	 *     - XPath search 에 가깝게 구현.
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param e
	 * @param path
	 * @return
	 */
	public static Element getElement(Element e, String path) {
		
		try {
			
			//TOMIN 임시.
//			System.out.println("*********** path:"+path);
//			System.out.println();
//			UtljDom.printXmlElement(e);
//			System.out.println();
//			System.out.println();
			
			
			String newPath = path;
			if(newPath.startsWith("//")) {			newPath = newPath.substring(2);			}
			else if (newPath.startsWith("/")) {		newPath = newPath.substring(1);			}
			
			String paths[] = newPath.split("/");
			int pathSize = paths.length -1;//마지막인지 비교하기위해 -1 한다.
			int pathIndex = 0;
			
			
			/***
			 * [ ] 내용 처리.
			 * 조건 -->[@id='MCIseqno']
			 * 위치 -->[position()=2]
			 */
			String condition;
			int position = 0;
			int currentPosition = 1;
			String []conditions = null;
			int idx = paths[pathSize].indexOf('[');
			if (idx > -1) {
				int end = paths[pathSize].indexOf(']');
				condition = paths[pathSize].substring(idx+1,end);
				
				if(condition.startsWith("position()")) {
					conditions = condition.split("=");
					position = Integer.parseInt(conditions[1]);
				}
				else {
					condition = paths[pathSize].substring(idx+1,end);
					conditions = condition.split("=");
					conditions[0] = conditions[0].replaceAll("@","");
					conditions[1] = conditions[1].replaceAll("'","");	
				}
				
				paths[pathSize] = paths[pathSize].substring(0,idx);
			}
			
//			if (pathSize ==0 ) {
//				return getElementNonXPath1Depth(e , path);
//			}
		
			List childList = e.getChildren();			
			Iterator iter = childList.iterator();
			Element childElement = null;
			
			boolean isRoot = false;
			if (e.getName().equals(paths[0])) {
				
				if(paths.length==1) {//Root Element일경우 바로 리턴한다.
					return e;
				}
				pathIndex++;
				isRoot = true;
			}
			
			Stack stack = new Stack();
			stack.push(iter);
			
			while(!stack.empty()) {
				iter = (Iterator)stack.pop();
				if(isRoot) {
					if(paths.length>1) {		pathIndex = 1;					}
					else {						pathIndex = 0;					}
				}
				else {
					pathIndex = 0;
				}
				
				while(iter.hasNext()) {
					childElement = (Element)iter.next();
					
					if(paths[pathIndex].equals(childElement.getName())) {
						if (pathIndex == pathSize) {
							if(idx>-1) {
								if(position>0) {
									if(position == currentPosition) {
										return childElement;
									}
									else {
										currentPosition++;
									}
								}
								else if (conditions[1].equals(childElement.getAttributeValue(conditions[0])) ) {
									return childElement;
								}
							}
							else {
								return childElement;	
							}
						}
						else {
							pathIndex++;
							
							childList = childElement.getChildren();
							if ( childList != null) {
								stack.push(iter);
								iter = childList.iterator();
							}
							else {
								return null;
							}
						}
					}
					else {
						childList = childElement.getChildren();
						if ( childList != null && childList.size() > 0) {
							stack.push(iter);
							iter = childList.iterator();
						}
					}
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	
	

	
	/**
	 * 
	 * <pre>
	 * 1.기능 : 특정 node의 List를 반환하는 메소드. <br>
	 * 2.처리 개요
	 *     - 
	 *     - 
	 * 3.주의사항 
	 *     -
	 *</pre>
	 * @param doc
	 * @param path
	 * @return
	 */
	public static List getNodeList(Document doc, String path){
		List lst = null;
		try{
			lst = XPath.newInstance(path).selectNodes(doc);
		}catch(JDOMException e){
			e.printStackTrace();
		}
		return lst;
	}
	
	/**
	* <pre>
	* 1. 기능 : 반복되는 노드 리스트를 반환한다.  
	* 2. 처리 개요 : 
	*     - 
	*     - 
	* 3. 주의사항 
	*     - path : ".//Node" 형식으로 넘긴다. 즉 Node Element 하위 노드에 대한 검색이 된다. 
	*</pre>
	* @param element
	* @param path
	* @return
	*/
	public static List getRepeatNodeList(Element element, String path){

		String nodeName = "";
		List list = new ArrayList();
		List lst;
	
		try {
			int total = XPath.newInstance("count("+path+"/*)").numberValueOf(element).intValue();
		
			for(int i=1 ; i <= total ; i++){
				nodeName = XPath.newInstance("name("+path+"/*[position()="+i+"])").valueOf(element);
				lst = XPath.newInstance(path+"/"+nodeName).selectNodes(element);
				if(lst.size() > 1){
					if(!list.contains(lst)){
						list.add(lst);
					}
				}
			}
		} catch (JDOMException e) {
	//	 TODO Auto-generated catch block
		e.printStackTrace();
		}
		return list;
	}	

	public static Document buildTestXml() {
		Document doc = null;
		
		try {
			doc = buildXml(testXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	
}

