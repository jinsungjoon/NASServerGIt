package com.nwtns.nas.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.nwtns.framework.util.NWLog;


public class JSonToClassParser {
	private Boolean isResult = true;
	private String rtnMsg = "";
	public Boolean IsResult() {
		return isResult;
	}
	public String  getReturnMsg(){
		return rtnMsg;
	}
	
	/**
	 * [클래스의 멤버변수 정보]
	 * @author Administrator
	 *
	 */
	private class TableFieldClass {
		public String tableName;
		public String setMethodName;
		public Object type;
		public String[] values = null;
		public void NewValues(int nSize) {
			values = new String[nSize];
		}
	}
	
	/**
	 * [클래스 멤버변수 변수타입 정의]
	 */
	//private static HashMap<String, HashMap<String, Object>> _dataClassMap = new HashMap<String, HashMap<String, Object>>();
	@SuppressWarnings("rawtypes")
	Class[] _paraTypeInteger = new Class[] {java.lang.Integer.class};
	@SuppressWarnings("rawtypes")
	Class[] _paraTypeLong = new Class[] {java.lang.Long.class};
	@SuppressWarnings("rawtypes")
	Class[] _paraTypeString = new Class[] {java.lang.String.class};
	@SuppressWarnings("rawtypes")
	Class[] _paraTypeStringArray = new Class[] {java.lang.String[].class};

	/**
	 * [클래스의 <멤버변수 타입 / set 메소드> SELECT]
	 * @param strClsName
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unused")
	private HashMap<String, Object> getClassMember(String strClsName, Class cls) {
//			_dataClassMap = null;
//			_dataClassMap = new HashMap<String, HashMap<String, Object>>();
		HashMap<String, HashMap<String, Object>> _dataClassMap = new HashMap<String, HashMap<String, Object>>();
		if(_dataClassMap.get(strClsName) == null) {
			HashMap<String, Object> hMethod = new HashMap<String, Object>();
			
			Field[] field = cls.getDeclaredFields();
			
			for(int j=0;j<field.length;j++) {
				String strName = field[j].getName();
				String strMethodName = String.format("set%s%s", strName.substring(0, 1).toUpperCase(), strName.substring(1, strName.length()));
				
				TableFieldClass tableField = new TableFieldClass();
				tableField.tableName = strName;
				tableField.setMethodName = strMethodName;
				tableField.type = field[j].getType();
//					tableField.values = new String[nSize];
				hMethod.put(strName, tableField);
			}
			
			_dataClassMap.put(strClsName, hMethod);
		}
		
		return _dataClassMap.get(strClsName);
	}
	
	/**
	 * [JSON 형식의 데이터를 클래스 데이터로 변환]
	 * @param strClassName
	 * @param strDataRoot
	 * @param resJsonData
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object parserJsonArrayToClass(String strClassName, String strDataRoot, JSONObject resJsonData) {
		try {
//			JSONArray jResults = (JSONArray)resJsonData.get(strDataRoot);	// "ParamVO"
//			
//			NWLog.d(this.getClass(), "JsonArray To Class Data : 1");
//			NWLog.d(this.getClass(), jResults);
//			NWLog.d(this.getClass(), "JsonArray To Class Data : 2");

			Class cls = Class.forName(strClassName);
			HashMap<String, Object> clsMember = getClassMember(strClassName, cls);
			
			Object clsInstance = cls.newInstance();
			//for(int i=0;i<jResults.size();i++) {
				//JSONObject jObject = (JSONObject)jResults.get(i);
				JSONObject jObject = resJsonData;

				NWLog.d(this.getClass(), "JsonArray To Class Data : jObject 1");
				NWLog.d(this.getClass(), jObject);
				NWLog.d(this.getClass(), "JsonArray To Class Data : jObject 2");
				
				
				Iterator iterator = jObject.entrySet().iterator();
				while(iterator.hasNext()) {
					String strName = (String)((Entry) iterator.next()).getKey();
					
					// 1) JSONArray인지 체크 (체크할수 있는 방법을 이것밖에 모르겠다.)
					boolean isArrayCheck = true;
					try {
						JSONArray chkArray = (JSONArray)jObject.get(strName);
					} catch (Exception je) {
						isArrayCheck = false;
					}

					// 2) JSONArray일때 처리
					if(isArrayCheck) {
						JSONArray subArray = (JSONArray)jObject.get(strName);
						if(subArray.size() > 0) {
							for(int j=0;j<subArray.size();j++) {
								JSONObject subObject = (JSONObject)subArray.get(j);
								Iterator subItor = subObject.entrySet().iterator();
								while(subItor.hasNext()) {
									String subName = (String)((Entry) subItor.next()).getKey();
									if(subObject.containsKey(subName) == true) {
										TableFieldClass tableField = (TableFieldClass)clsMember.get(subName);
	
										if(tableField.type.equals(java.lang.String[].class)) {
											if(tableField.values == null) {
												tableField.NewValues(subArray.size());
											}
											tableField.values[j] = subObject.get(subName).toString();
										}
									}
								}
							}
						}
						JSONObject subObject = (JSONObject)subArray.get(0);
						Iterator subItor2 = subObject.entrySet().iterator();
						while(subItor2.hasNext()) {
							String subName = (String)((Entry) subItor2.next()).getKey();
							TableFieldClass tableField = (TableFieldClass)clsMember.get(subName);
							if(tableField.type.equals(java.lang.String[].class)) {
								String strMethodName = tableField.setMethodName;
								cls.getMethod(strMethodName, _paraTypeStringArray).invoke(clsInstance, new Object[]{tableField.values});
							}
						}
					} else
					// 3) ObjectValue인경우 처리
					if(jObject.containsKey(strName) == true) {
						TableFieldClass tableField = (TableFieldClass)clsMember.get(strName);
						String strMethodName = tableField.setMethodName;

						if(tableField.type.equals(java.lang.Long.class)) {
							cls.getMethod(strMethodName, _paraTypeLong).invoke(clsInstance, Long.parseLong(jObject.get(strName).toString()));
						} else
						if(tableField.type.equals(java.lang.Integer.class)) {
							cls.getMethod(strMethodName, _paraTypeInteger).invoke(clsInstance, Integer.parseInt(jObject.get(strName).toString()));
						} else
						if(tableField.type.equals(java.lang.String.class)) {
							cls.getMethod(strMethodName, _paraTypeString).invoke(clsInstance, jObject.get(strName).toString());
						}
					}
				}
			//}
			return clsInstance;
		} catch (Exception e) {
			isResult = false;
			rtnMsg = String.format("<%s> JSon To Class 파싱에러 : %s", strClassName, e.getMessage());
			NWLog.d(this.getClass(), rtnMsg);
		}
		
		return null;
	}
	
    /**
     * [JSON 형식의 데이터를 클래스 데이터로 변환]
     * @param strClassName
     * @param strDataRoot
     * @param strResponse
     * @return
     * @자료형식 : 아래 형식의 데이터 구조만 가능하다.
     * [변경전]	"{\"ClassSampleData\": [{"+
				"    \"cusCode\": \"1234567\","+
				"    \"cusName\": \"가남상회\","+
				"    \"longValue\": \"10\","+
				"    \"intValue\": \"20\","+
				"	 \"Members\":["+
				"		{\"artNum\":\"11111\",\"artNam\":\"제품1\",\"artIpsu\":\"5\"},"+
				"		{\"artNum\":\"22222\",\"artNam\":\"제품2\",\"artIpsu\":\"7\"},"+
				"		{\"artNum\":\"33333\",\"artNam\":\"제품3\",\"artIpsu\":\"8\"}"+
				"	],"+
				"	 \"Etc\":["+
				"		{\"etc1\":\"11111\",\"etc2\":\"22222\"},"+
				"		{\"etc1\":\"33333\",\"etc2\":\"44444\"}"+
				"	]"+
				"}]}";
     * [2013.01.23]
      			"{"+
				"    \"cusCode\": \"1234567\","+
				"    \"cusName\": \"가남상회\","+
				"    \"longValue\": \"10\","+
				"    \"intValue\": \"20\","+
				"	 \"Members\":["+
				"		{\"artNum\":\"11111\",\"artNam\":\"제품1\",\"artIpsu\":\"5\"},"+
				"		{\"artNum\":\"22222\",\"artNam\":\"제품2\",\"artIpsu\":\"7\"},"+
				"		{\"artNum\":\"33333\",\"artNam\":\"제품3\",\"artIpsu\":\"8\"}"+
				"	],"+
				"	 \"Etc\":["+
				"		{\"etc1\":\"11111\",\"etc2\":\"22222\"},"+
				"		{\"etc1\":\"33333\",\"etc2\":\"44444\"}"+
				"	]"+
				"}";
     */
	public Object parserJsonArrayToClass(String strClassName, String strDataRoot, String strResponse) {
		JSONObject resJsonData = null;
		try {
			NWLog.dc(this.getClass(), String.format("============ parserJsonArrayToClass Start ==========") );
			NWLog.dc(this.getClass(), String.format("parserJsonArrayToClass : <%s><%s>",  strClassName, strResponse));
			
			resJsonData = (JSONObject)new JSONParser().parse(strResponse);
			
			NWLog.dc(this.getClass(), "parserJsonArrayToClass resJsonData : 3");
			NWLog.dc(this.getClass(), resJsonData);
			NWLog.dc(this.getClass(), "parserJsonArrayToClass resJsonData : 4");
			
			Object objClass = parserJsonArrayToClass(strClassName, strDataRoot, resJsonData);
			
			NWLog.dc(this.getClass(), "parserJsonArrayToClass resJsonData : 5");
			NWLog.dc(this.getClass(), objClass);
			NWLog.dc(this.getClass(), "parserJsonArrayToClass resJsonData : 6");
			
			return objClass;
		} catch (ParseException e) {
			isResult = false;
			rtnMsg = String.format("<%s> JSon To Class 데이터 변환 에러 : %s", strClassName, e.getMessage());
			NWLog.d(this.getClass(), rtnMsg);
		}	
		
		return null;
		/*
		JSONArray jArray = null;
		JSONObject resJsonData = null;
		try {
			jArray = (JSONArray)new JSONParser().parse(strResponse);
			
//			NWLog.d(this.getClass(), "Class Name : " + strClassName);
//			NWLog.d(this.getClass(), "parserJsonArrayToClass jArray : 1");
//			NWLog.d(this.getClass(), jArray);
//			NWLog.d(this.getClass(), "parserJsonArrayToClass jArray : 2");
			
			resJsonData = (JSONObject)jArray.get(0);
			
//			NWLog.d(this.getClass(), "parserJsonArrayToClass resJsonData : 3");
//			NWLog.d(this.getClass(), resJsonData);
//			NWLog.d(this.getClass(), "parserJsonArrayToClass resJsonData : 4");
			
			Object objClass = parserJsonArrayToClass(strClassName, strDataRoot, resJsonData);
			return objClass;
		} catch (ParseException e) {
			isResult = false;
			rtnMsg = String.format("<%s> JSon To Class 데이터 변환 에러 : %s", strClassName, e.getMessage());
			NWLog.d(this.getClass(), rtnMsg);
		}	
		
		return null;
		*/
	}
}
