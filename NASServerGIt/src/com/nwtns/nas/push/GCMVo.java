package com.nwtns.nas.push;

/**
* GCMVo 는 PUSH를 보낼 때 정보를 담는 역할을 합니다.
*/

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
public class GCMVo {     
	// android 로 보낼 정보     
	
	private String title = "제목입니다.";
	private String msg = "내용입니다.";
	private String pushType = "";
	private String workDate = "";
	private String agencyCode = "";
	private String agencyEmpCode = "";
	private String storeId = "";
	private String seq = "";
	private String storeName= "";
	private String storeCode= "";
	private String pushName = "";
	
	private String typeCode = "application/json"; //"코드입니다.";
	
	// push 결과 정보     
	private String regId; // regId
	private boolean pushSuccessOrFailure; // 성공 여부
	private String msgId = ""; // 메시지 ID
	private String errorMsg = ""; // 에러메시지
	public String getTitle() {         
		return title;     
	}       
	
	public void setTitle(String title) throws UnsupportedEncodingException {
		this.title = title; //URLEncoder.encode(title, "UTF-8");
		System.out.println("title : " + this.title);
	}
	
	public String getMsg() {
		System.out.println("msg : " + this.msg);
		return msg;     
		
	}
	public void setWorkDate(String workDate){
		this.workDate = workDate;
	}
	public String getWorkDate(){
		return workDate;
	}
	public void setAgecyCode(String agencyCode){
		this.agencyCode = agencyCode;
	}
	public String getAgencyCode(){
		return agencyCode;
	}
	
	public void setAgencyEmpCode(String agencyEmpCode){
		this.agencyEmpCode = agencyEmpCode;
	}
	public String getAgencyEmpCode(){
		return agencyEmpCode;
	}
	public void setStoreId(String storeId){
		this.storeId = storeId;
	}
	public String getStoreId(){
		return storeId;
	}
	public void setSeq(String seq){
		this.seq = seq;
	}
	public String getSeq(){
		return seq;
	}
	public void setPushType(String pushType){
		this.pushType =pushType;
	}
	public String getPushType(){
		return pushType;
	}
	
	public void setPushName(String pushName){
		this.pushName = pushName;
		
	}
	public String getPushName(){
		return this.pushName;
	}
	public void setStoreCode(String storeCode){
		this.storeCode = storeCode;
	}
	public String getStoreCode(){
		return storeCode;
	}
	public void setStoreName(String storeName){
		this.storeName = storeName;
	}
	public String getStoreName(){
		return storeName;
	}
	public void setMsg(String msg) throws UnsupportedEncodingException {
		this.msg = msg; //URLEncoder.encode(msg, "UTF-8"); 
		System.out.println("msg : " + this.msg);
		
	}
	
	public String getTypeCode() {
		return typeCode;     
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;     
	}
	
	public boolean getPushSuccessOrFailure() {
		return pushSuccessOrFailure;     
	}
	
	public void setPushSuccessOrFailure(boolean pushSuccessOrFailure) {
		this.pushSuccessOrFailure = pushSuccessOrFailure;     
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;     
	}
	
	public void setRegId(String regId) {
		this.regId = regId;     
	}
	
	public String getMsgId() {
		return msgId;     
	}
	
	public void setMsgId(String msgId) {
		this.msgId = msgId;     
	}
	
	public String getRegId() {
		return regId;     
	}                
}