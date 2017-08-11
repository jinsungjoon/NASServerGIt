package com.nwtns.nas.push;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**  * GCM UTIL  
* GCMProvider 가 하는 일은 실제 GCM PUSH 를 보내는 역할을 합니다.  
* gcm-server.jar, json-simple-1.1.1.jar 필요  
*   
* @author   
*  LHL
*/ 
public class GCMProvider {
	//AIzaSyD_L2tZkpBT1wAmRGEMGJ-W87bHBmMosUk
	//415159460883
	
	//static final String API_KEY = "AIzaSyD_L2tZkpBT1wAmRGEMGJ-W87bHBmMosUk"; //서버 API KEY"; // server api key     
	//static final String API_KEY = "AIzaSyAS1VKo7U18GvNw4dM_ol-eb9wE2Xr6L0c";
	static final String API_KEY = "AIzaSyDfb2SdzGwBUamMqK5g3tNApDwvYXLc5UI";
	private static final int MAX_SEND_CNT = 999; // 1회 최대 전송 가능 수          
	
	// android 에서 받을 extra key (android app 과 동일해야 함)     
	static final String TITLE_EXTRA_KEY = "TITLE";     
	static final String MSG_EXTRA_KEY = "MSG";     
	static final String MSGID_EXTRA_KEY = "MSGID";     
	
	static final String PUSH_TYPE = "PUSH_TYPE";
	static final String AGENCY_CODE = "AGENCY_CODE";
	static final String AGENCY_EMP_CODE = "AGENCY_EMP_CODE";
	static final String STORE_ID = "STORE_ID";
	static final String WORK_DATE = "WORK_DATE";
	static final String SEQ = "SEQ";
	static final String STORE_CODE= "STORE_CODE";
	static final String STORE_NAME = "STORE_NAME";
	static final String PUSH_NAME = "PUSH_NAME";
	
	static final String TYPE_EXTRA_CODE = "application/json"; //TYPE_CODE";     
	
	// android 에서 받을 extras key        
	List<String> resList = null;
	private Sender sender;
	private Message message;
	public ArrayList<GCMVo> rtnList;       
	/**      
	* GCM  생성자      
	* RegistrationId 셋팅, sender 셋팅, message 셋팅     
	*       
	* @param reslist : RegistrationId      
	* @param gcmVo : msg 정보      */     
	public GCMProvider(List<String> reslist, GCMVo gcmVo) {         
		sender = new Sender(API_KEY);         
		this.resList = reslist;         
		setMessage(gcmVo);         
		rtnList = new ArrayList<GCMVo>();         
		sendGCM();     
	}       
	
	/**      
	* 메시지 셋팅      
	* @param gcmVo      
	*/     
	private void setMessage(GCMVo gcmVo) {         
		Builder builder = new Message.Builder();
		try {
			builder.addData(TITLE_EXTRA_KEY, URLEncoder.encode(gcmVo.getTitle(),"utf-8"));
        
		builder.addData(MSG_EXTRA_KEY, URLEncoder.encode( gcmVo.getMsg(),"utf-8"));
		builder.addData(MSGID_EXTRA_KEY, URLEncoder.encode( gcmVo.getMsgId(),"utf-8"));
		builder.addData(PUSH_TYPE, URLEncoder.encode( gcmVo.getPushType(),"utf-8"));
		builder.addData(WORK_DATE, URLEncoder.encode( gcmVo.getWorkDate(),"utf-8"));
		builder.addData(AGENCY_CODE, URLEncoder.encode( gcmVo.getAgencyCode(),"utf-8"));
		builder.addData(AGENCY_EMP_CODE, URLEncoder.encode(gcmVo.getAgencyEmpCode(),"utf-8"));
		builder.addData(STORE_ID, URLEncoder.encode(gcmVo.getStoreId(),"utf-8"));
		builder.addData(SEQ, URLEncoder.encode( gcmVo.getSeq(),"utf-8"));
		builder.addData(TYPE_EXTRA_CODE, URLEncoder.encode( gcmVo.getTypeCode(),"utf-8"));   
		builder.addData(PUSH_NAME, URLEncoder.encode( gcmVo.getPushName(),"utf-8"));
		builder.addData(STORE_CODE, URLEncoder.encode( gcmVo.getStoreCode(),"utf-8"));
		builder.addData(STORE_NAME, URLEncoder.encode( gcmVo.getStoreName(),"utf-8"));
		message = builder.build();     
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}       
	
	/**      
	* 메시지 전송      
	*/     
	private void sendGCM() {         
		if (resList.size() > 0) {            
			if (resList.size() <= MAX_SEND_CNT) { // 한번에 1000건만 보낼 수 있음                 
				sendMultivastResult(resList);             
			} else {                 
				List<String> resListTemp = new ArrayList<String>();
				for (int i = 0; i < resList.size(); i++) {                     
					if ((i + 1) % MAX_SEND_CNT == 0) {                         
						sendMultivastResult(resListTemp);
						resListTemp.clear();
					}                     
					resListTemp.add(resList.get(i));
				}             
				
				// 1000건씩 보내고 남은 것 보내기                 
				if(resListTemp.size() != 0){
					sendMultivastResult(resListTemp);
				}             
			}         
		}       
	}       
	
	/**      
	* 실제 멀티 메시지 전송      
	*       
	* @param list      */     
	private void sendMultivastResult(List<String> list) {
		try {
			MulticastResult multiResult = sender.send(message, list, 5); // 발송할 메시지, 발송할 타깃(RegistrationId), Retry 횟수
			List<Result> resultList = multiResult.getResults(); 
			for (int i=0; i<resultList.size(); i++){
				Result result = resultList.get(i);
				// 결과 셋팅                 
				GCMVo rtnGcmVo = new GCMVo(); 
				rtnGcmVo.setRegId(list.get(i));
				rtnGcmVo.setMsgId(result.getMessageId());
				rtnGcmVo.setErrorMsg(result.getErrorCodeName());
				if (result.getMessageId() != null) { // 전송 성공
					rtnGcmVo.setPushSuccessOrFailure(true);
				} else { // 전송 실패
					rtnGcmVo.setPushSuccessOrFailure(false);
				}
				rtnList.add(rtnGcmVo);
			}           
		} catch (IOException e) {
			e.printStackTrace();         
		}     
	} 
}
