package com.nwtns.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.nwtns.framework.ParentConf;

public class ParseJSON {

//	protected JSONObject jsonRoot 		= null;
//	protected JSONArray jsonArr 		= null;
//	protected JSONObject jsonObj 		= null;
	
	public ParseJSON(){
//		jsonRoot 	= new JSONObject();
//		jsonArr 	= new JSONArray();
//		jsonObj 	= new JSONObject();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject error(String serviceFlag, String msg) {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("RETURN_CODE", ParentConf.RETURN_ERROR);
		jsonObj.put("RETURN_MSG", msg);
		jsonObj.put("NAME", serviceFlag);

		return jsonObj;
		
//		jsonObj.put("RETURN_CODE", ParentConf.RETURN_ERROR);
//		jsonObj.put("RETURN_MSG", msg);
//		jsonRoot.put(serviceFlag, jsonObj);
//
//		return jsonRoot;
	}
	
//	public JSONObject success(Class cls, String msg) {
//
//		NWLog.d(cls, "[return]" + msg);
//		jsonObj.put("RETURN_CODE", ParentConf.RETURN_SUCCESS);
//		jsonObj.put("RETURN_MSG", msg);
//		jsonRoot.put("JSON_RESULT", jsonObj);
//
//		return jsonRoot;
//	}
	
	@SuppressWarnings("unchecked")
	public JSONObject success(String serviceFlag, String msg) {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("RETURN_CODE", ParentConf.RETURN_SUCCESS);
		jsonObj.put("RETURN_MSG", msg);
		jsonObj.put("NAME", serviceFlag);
//		jsonObj.put("LIST", jsonObj);

		return jsonObj;
	}
	
	public JSONObject success(String serviceFlag, HashMap<String,Object> map) throws Exception{
		//NWLog.d(this.getClass(), "LHL_success(String serviceFlag, HashMap<String,Object> map): ["+serviceFlag+"] map.size():" + map.size());
		return success(serviceFlag, map, "MEMBER");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject success(String serviceFlag, HashMap<String,Object> map, String rootKey) throws Exception{
		JSONObject jsonObj 		= null;
		
		JSONObject jo1 = new JSONObject();
		
		if(map != null && map.size() > 0){
			//NWLog.d(this.getClass(), "ParseJSON success ["+serviceFlag+"] map.size():"+map.size());
			
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				//jo1.put(entry.getKey(), entry.getValue());
				jo1.put(entry.getKey(), NWUtil.print(entry.getValue(), ""));
			}
			
			jsonObj = new JSONObject();
			jsonObj.put("RETURN_CODE", ParentConf.RETURN_SUCCESS);
			jsonObj.put("RETURN_MSG", "");
			jsonObj.put(rootKey, jo1);
			jsonObj.put("NAME", serviceFlag);
		} else 
		{
			jsonObj = new JSONObject();
			jsonObj.put("RETURN_CODE", ParentConf.RETURN_ERROR);
			jsonObj.put("RETURN_MSG", "[" + serviceFlag + "] 요청결과가 없습니다.");
			jsonObj.put(rootKey, "");
			jsonObj.put("NAME", serviceFlag);
		}
		
		NWLog.d(this.getClass(), "ParseJSON success Result ["+serviceFlag+"]");
		NWLog.d(this.getClass(), jsonObj);
		
		return jsonObj;
	}
	
	public JSONObject success(String serviceFlag, ArrayList<HashMap<String,Object>> list) throws Exception{
		return success(serviceFlag, list, "LIST");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject success(String serviceFlag, ArrayList<HashMap<String,Object>> list, String rootKey) throws Exception{
		HashMap<String,Object> bean 	= null;
		JSONObject jsonObj 		= null;
		JSONArray jsonArr 	= new JSONArray();
		
		if(list != null){
			for(int i=0; i < list.size(); i++){
				jsonObj = new JSONObject();
				//bean = new HashMap();
				bean = (HashMap) list.get(i);
				
				Iterator iterator = bean.entrySet().iterator();
	
				while (iterator.hasNext()) {
					Entry entry = (Entry) iterator.next();
					//jsonObj.put(entry.getKey(), entry.getValue());
					jsonObj.put(entry.getKey(), NWUtil.print(entry.getValue(), ""));
				}
	
				jsonArr.add(jsonObj);
			}
		}
		jsonObj = new JSONObject();
		jsonObj.put("RETURN_CODE", ParentConf.RETURN_SUCCESS);
		jsonObj.put("RETURN_MSG", "");
		jsonObj.put(rootKey, jsonArr);
		jsonObj.put("NAME", serviceFlag);
		
		return jsonObj;
		
//		jsonRoot.put("JSON_RESULT", jsonObj);	
//		NWLog.d(this.getClass(), "Make Json("+jsonArr.size()+"): " + jsonRoot.toString());
//		return jsonRoot;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject toHashmap(HashMap<String, Object> inMap) {
		JSONObject j1 = new JSONObject();
		
		Iterator iterator = inMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			//j1.put(entry.getKey(), entry.getValue());
			
			if(entry.getValue() != null && entry.getValue().getClass().toString().compareTo("class java.util.HashMap")==0) {
				j1.put(entry.getKey(), toHashmap((HashMap<String, Object>) entry.getValue()));
			} else {
				j1.put(entry.getKey(), NWUtil.print(entry.getValue(), ""));
			}
		}
		
		return j1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject toLinkedHashmap(LinkedHashMap<String, Object> inMap) {
		JSONObject j1 = new JSONObject();
		
		Iterator iterator = inMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			//j1.put(entry.getKey(), entry.getValue());
			
			if(entry.getValue() != null && entry.getValue().getClass().toString().compareTo("class java.util.LinkedHashMap")==0) {
				j1.put(entry.getKey(), toLinkedHashmap((LinkedHashMap<String, Object>) entry.getValue()));
			} else {
				j1.put(entry.getKey(), NWUtil.print(entry.getValue(), ""));
			}
		}
		
		return j1;
	}
	
	/**
	 * 
	 * @param serviceFlag
	 * @param lists         지원타입 : class java.util.ArrayList, class java.util.HashMap, class org.json.simple.JSONObject
	 * @param rootKeys
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject success(String serviceFlag, Object[] lists, String[] rootKeys) throws Exception{
		JSONObject jsonObj = new JSONObject();
		
		if(lists!=null) {
			jsonObj = new JSONObject();
			jsonObj.put("RETURN_CODE", ParentConf.RETURN_SUCCESS);
			jsonObj.put("RETURN_MSG", "");
			jsonObj.put("NAME", serviceFlag);
			
			for(int n=0;n<lists.length;n++) {
				if(lists[n] == null) {
					jsonObj.put(rootKeys[n], null);
					continue;
				}
				
				if(lists[n].getClass().toString().compareTo("class java.util.ArrayList")==0) {
					ArrayList<Object> list = (ArrayList<Object>) lists[n];
					JSONArray ja = new JSONArray();
					if(list != null){
						for(int i=0; i < list.size(); i++){
							if(list.get(i).getClass().toString().compareTo("class java.util.HashMap")==0) {
								ja.add(toHashmap((HashMap) list.get(i)));
							}else if(list.get(i).getClass().toString().compareTo("class java.util.LinkedHashMap")==0) {
								ja.add(toLinkedHashmap((LinkedHashMap) list.get(i)));
							}
						}
					}
					jsonObj.put(rootKeys[n], ja);
					
					/***
					ArrayList<HashMap<String,Object>> list = (ArrayList<HashMap<String,Object>>) lists[n];
					JSONArray ja = new JSONArray();
					if(list != null){
						HashMap<String,Object> bean 	= null;
						for(int i=0; i < list.size(); i++){
							JSONObject j1 = new JSONObject();
							bean = (HashMap) list.get(i);
							Iterator iterator = bean.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry entry = (Entry) iterator.next();
								
								if(entry.getValue() == null) {
									j1.put(entry.getKey(), NWUtil.print(entry.getValue(), ""));
								} else {
									if(entry.getValue().getClass().toString().compareTo("class java.util.HashMap")==0)
										j1.put(entry.getKey(), toHashmap((HashMap<String, Object>) entry.getValue()));
									else if(entry.getValue().getClass().toString().compareTo("class java.util.LinkedHashMap")==0)
										 j1.put(entry.getKey(), toLinkedHashmap((LinkedHashMap<String, Object>) entry.getValue()));
									else
										j1.put(entry.getKey(), NWUtil.print(entry.getValue(), ""));
								}
							}
				
							ja.add(j1);
						}
					}
					jsonObj.put(rootKeys[n], ja);
					***/
				} else 
				if(lists[n].getClass().toString().compareTo("class java.util.HashMap")==0) {
					HashMap<String,Object> bean = (HashMap<String,Object>) lists[n];
					
					jsonObj.put(rootKeys[n], toHashmap(bean));
					
					/**
					JSONObject j1 = new JSONObject();
					
					Iterator iterator = bean.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry entry = (Entry) iterator.next();
						//j1.put(entry.getKey(), entry.getValue());
						j1.put(entry.getKey(), NWUtil.print(entry.getValue(), ""));
					}
					
					jsonObj.put(rootKeys[n], j1);
					**/
				} else
				if(lists[n].getClass().toString().compareTo("class java.util.LinkedHashMap")==0) {
					LinkedHashMap<String,Object> bean = (LinkedHashMap<String,Object>) lists[n];
					
					jsonObj.put(rootKeys[n], toLinkedHashmap(bean));
				} else
				if(lists[n].getClass().toString().compareTo("class org.json.simple.JSONObject")==0) {
					jsonObj.put(rootKeys[n], lists[n]);
				} else 
				if(lists[n].getClass().toString().compareTo("class org.json.simple.JSONArray")==0) {
					JSONArray jsonAry = (JSONArray) lists[n];
					
					jsonObj.put(rootKeys[n], jsonAry);
				} else {
					JSONObject j1 = new JSONObject();
					j1.put("ERR_MSG", "지원하지 않은 Pasing타입입니다. 관리자에게 문의하십시오.");
					jsonObj.put(rootKeys[n], j1);
				}
			}
		}
		
		
		return jsonObj;
	}
	
	// 2015.09.23[KDBAEK] JSONObject 데이터를 HashMap 데이터로 변경
	//  - 스마트폰에서 입력된 파라미터가 String에서 JSONObject로 변형된 이후 호출 된다.
	//  - 사용되는 타입은 String, JSONObject, JSONArray정도만 사용될 것이다.
	public HashMap<String, Object> toHashKeyValue(JSONObject jsonObject) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Iterator iterator = jsonObject.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			
			if(entry.getValue().getClass().toString().indexOf("String") > 0) {
				resultMap.put(entry.getKey().toString(), entry.getValue());
			} else
			if(entry.getValue().getClass().toString().indexOf("JSONObject") > 0) {
				resultMap.put(entry.getKey().toString(), toHashKeyValue((JSONObject)entry.getValue()));
			} else
			if(entry.getValue().getClass().toString().indexOf("JSONArray") > 0) {
				JSONArray list = (JSONArray) entry.getValue();
				if(list != null){
					List<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>(); 
					for(int i=0; i < list.size(); i++){
						if(list.get(i).getClass().toString().indexOf("JSONObject") > 0) {
							items.add(toHashKeyValue((JSONObject)list.get(i)));
						} else {
							throw new Exception(String.format("지원하지 않은 데이터 타입 입니다.", entry.getValue().getClass().toString()));
						}
					}
					resultMap.put(entry.getKey().toString(), items);
				}
			} else
//			if(entry.getValue().getClass().toString().indexOf("ArrayList") > 0) {
//				throw new Exception(String.format("지원하지 않은 데이터 타입 입니다.", entry.getValue().getClass().toString()));
//			} else
//			if(entry.getValue().getClass().toString().indexOf("HashMap") > 0) {
//				throw new Exception(String.format("지원하지 않은 데이터 타입 입니다.", entry.getValue().getClass().toString()));
//			} else 
			{
				resultMap.put(entry.getKey().toString(), "Default 지원하지 않은 데이터 타입 입니다. (" + entry.getValue().getClass().toString() + ")");
				throw new Exception(String.format("지원하지 않은 데이터 타입 입니다.", entry.getValue().getClass().toString()));
			}
		}
		
		return resultMap;
	}
	
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public JSONObject success(String serviceFlag, Object[] lists, String[] rootKeys) throws Exception{
//		JSONObject jsonObj = new JSONObject();
//		
//		if(lists!=null) {
//			jsonObj = new JSONObject();
//			jsonObj.put("RETURN_CODE", ParentConf.RETURN_SUCCESS);
//			jsonObj.put("RETURN_MSG", "");
//			jsonObj.put("NAME", serviceFlag);
//			
//			for(int n=0;n<lists.length;n++) {
//				ArrayList<HashMap<String,Object>> list = (ArrayList<HashMap<String,Object>>) lists[n];
//				JSONArray ja = new JSONArray();
//				if(list != null){
//					HashMap<String,Object> bean 	= null;
//					for(int i=0; i < list.size(); i++){
//						JSONObject j1 = new JSONObject();
//						bean = new HashMap();
//						bean = (HashMap) list.get(i);
//						
//						Iterator iterator = bean.entrySet().iterator();
//						while (iterator.hasNext()) {
//							Entry entry = (Entry) iterator.next();
//							//j1.put(entry.getKey(), entry.getValue());
//							j1.put(entry.getKey(), NWUtil.print(entry.getValue(), ""));
//						}
//			
//						ja.add(j1);
//					}
//				}
//				
//				jsonObj.put(rootKeys[n], ja);
//			}
//		}
//		
//		return jsonObj;
//	}
	
	
	
//	// Sample
//	public JSONObject success(Map<String,Object> map) throws Exception{
//		HashMap<String,Object> bean 	= null;
//
//		NWLog.d(this.getClass(), "map.size():"+map.size());
//		System.out.println("map.size():"+map.size());
//		
//		if(map != null){
//			for(int i=0; i < map.size(); i++){
//				jsonObj = new JSONObject();
//				bean = (HashMap<String,Object>) map.get(i);
//				Iterator iterator = bean.entrySet().iterator();
//				while (iterator.hasNext()) {
//					Entry entry = (Entry) iterator.next();
//					NWLog.d(this.getClass(), "entry.getKey():"+entry.getKey() + " | " + entry.getValue());
//					jsonObj.put(entry.getKey(), entry.getValue());
//				}
//				jsonArr.add(jsonObj);
//			}
//		}
//		jsonObj = new JSONObject();
//		jsonObj.put("RETURN_CODE", ParentConf.RETURN_SUCCESS);
//		jsonObj.put("LIST", jsonArr);
//		jsonRoot.put("JSON_RESULT", jsonObj);
//
//		return jsonRoot;
//	}
	
/*
	public JSONObject result(HashMap bean) throws Exception{
		
		
		Iterator iterator = bean.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			jsonObj.put(entry.getKey(), entry.getValue());
		}

		jsonObj.put("RETURN_CODE", ParentConf.RETURN_SUCCESS);
		jsonRoot.put("JSON_RESULT", jsonObj);	
		
		NWLog.d(this.getClass(), "Make Json Size: " + jsonObj.size());
		
		return jsonRoot;
	}

	public JSONObject result() throws Exception{
		
		jsonObj.put("RETURN_CODE", ParentConf.RETURN_SUCCESS);
		jsonRoot.put("JSON_RESULT", jsonObj);	
		
		NWLog.d(this.getClass(), "Make Json Size: " + jsonObj.size());
		
		return jsonRoot;
	}*/
}
