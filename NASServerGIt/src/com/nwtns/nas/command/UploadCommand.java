package com.nwtns.nas.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.nwtns.framework.exception.ActionKeyException;
import com.nwtns.framework.mvc.NWActionForward;
import com.nwtns.framework.mvc.NWICommand;
import com.nwtns.framework.mvc.NWSuperCommand;
import com.nwtns.framework.util.NWExcel;
import com.nwtns.framework.util.NWFile;
import com.nwtns.framework.util.NWLog;
import com.nwtns.framework.util.NWUtil;
import com.nwtns.framework.util.ParseJSON;
import com.nwtns.nas.base.Conf.DirConfig;
import com.nwtns.nas.base.Conf.UploadConfig;
import com.nwtns.nas.dao.DbDao;
import com.oreilly.servlet.MultipartRequest;

/**
 * 멀티파트 파일 업로드 처리를 위한 클래스
 * @author cjj
 *
 */
public class UploadCommand extends NWSuperCommand implements NWICommand {


	@SuppressWarnings("unchecked")
	@Override
	public NWActionForward execute(HttpServletRequest request) throws ServletException, IOException, ActionKeyException {
		NWActionForward action = null;
		ParseJSON resParser = new ParseJSON();
		//HashMap<String,Object> param = new HashMap<String,Object>();
		JSONObject jsonRoot = resParser.success("", "");

		NWLog.i(this.getClass(), "=========>FILE_UPLOAD_START " + request.getRequestURI());
		String mUploadType = "", mUserId = "", mFileName = "";
		String mOrgFileNm = "", mNewFileNm="" ,uploadFileExt="" , uploadFilePath="";
		// 파일업데이트와 동시에 DB 처리할 내용이 있을경우 처리하기 위함.
		String mExtraData = "";

		try {
			// 1. 저장 디렉토리 생성
			//String mToday = NWUtil.getDateType("D");
			String temp_path = DirConfig.PATH_DIR_TEMP;
			NWLog.i(this.getClass(), "=========>temp_path: " + temp_path);

			NWFile.makeDirectory(this.getClass(), DirConfig.PATH_APP_FILE);	// 앱 파일 경로
			NWFile.makeDirectory(this.getClass(), DirConfig.PATH_APP_FILE + "old" + File.separator); // 앱 백업 파일 경로

			// 2. MultipartRequest
			//File file = new File(temp_path);
			MultipartRequest multi = new MultipartRequest(request, temp_path, 600*1024*1024, "utf-8");
			Enumeration files = multi.getParameterNames();

			// 3. 공통 파라미터 수신
			mUploadType = multi.getParameter("upload_type");
			mUserId     = multi.getParameter("user_id");
			mOrgFileNm  = multi.getParameter("file_name");
			mExtraData  = multi.getParameter("extraData");
			//while(files.hasMoreElements()){
			//	String name = (String) files.nextElement();
			//	String value = multi.getParameter(name);
			//	NWLog.i(this.getClass(), "Parameter Name - " + name + ", Value - " + value);
			//	if(name.equals("upload_type")) 	mUploadType = value;
			//	if(name.equals("user_id")) 		mUserId = value;
			//	if(name.equals("file_name")) 	mOrgFileNm = value;
			//	if(name.equals("extraData")) 	mExtraData = value;
			//}
			jsonRoot.put("NAME", mUploadType);

			mFileName = NWUtil.getDateType("B");
			mFileName = mUserId+"_"+mFileName;
			
			NWLog.i(this.getClass(), "upload_type:" + mUploadType + ", user_id:" + mUserId + ", tmp_file_name:" + mFileName);
			
			// 4.1 DB 업데이트 처리
			if(!mExtraData.equals("") && !"null".equals(mExtraData) && mExtraData != null) {
				ArrayList<HashMap<String, Object>> data = executeDatabase(mUploadType, mUserId, mExtraData);
				if(data == null) {
					return jsonForward(action, resParser.error(mUploadType, "업데이트 실패 (데이터 입력)"));
				}
				jsonRoot.put("DATA", data);
			}

			// 4.2  파일 업로드 처리
			HashMap<String, String> hm= NWFile.multiUpload2(this.getClass(), multi, temp_path, temp_path, mFileName);		// 기본 저장 폴더
			mOrgFileNm = hm.get("ORG_NM");		// 원본파일명
			mNewFileNm = hm.get("FILE_NM");		// 신규파일명
			uploadFileExt = hm.get("FILE_EXT");	// 확장자

			NWLog.i(this.getClass(), "[PARAM] mUserId:" + mUserId);
			NWLog.i(this.getClass(), "[PARAM] mFileType:" + mUploadType);
			NWLog.i(this.getClass(), "[PARAM] mFileName:" + mFileName);
			NWLog.i(this.getClass(), "[PARAM] mOrgFileNm:" + mOrgFileNm);
			NWLog.i(this.getClass(), "[PARAM] mNewFileNm:" + mNewFileNm);
			NWLog.i(this.getClass(), "[PARAM] uploadFileExt:" + uploadFileExt);

			//  APK 파일 업로드일 경우
			if(UploadConfig.FILE_TYPE_APK.equals(mUploadType)){
				String newAppVersion = "", oldAppVersion = "";
				
				newAppVersion = multi.getParameter("new_ver_info");
				oldAppVersion = multi.getParameter("old_ver_info");
				
				File f = new File(DirConfig.PATH_APP_FILE + mOrgFileNm);
				if (f != null && f.exists()) {
					NWFile.makeDirectory(this.getClass(), DirConfig.PATH_APP_FILE + "old" + File.separator + oldAppVersion + File.separator);
					File tempPath = new File(DirConfig.PATH_APP_FILE + "old" + File.separator + oldAppVersion + File.separator + mOrgFileNm);
					if (tempPath != null && tempPath.exists()) {
						tempPath.delete();
					}
					//f.renameTo(tempPath);
					NWFile.fileCopy(this.getClass(), DirConfig.PATH_APP_FILE + mOrgFileNm, DirConfig.PATH_APP_FILE + "old" + File.separator + oldAppVersion + File.separator + mOrgFileNm);
				}

				NWFile.fileCopy(this.getClass(), temp_path +File.separator + mNewFileNm, DirConfig.PATH_APP_FILE + mOrgFileNm);
				if(!"".equals(mNewFileNm)) NWFile.deleteTempFile(this.getClass(), temp_path , mNewFileNm);
				NWLog.i(this.getClass(), "[apk 저장] :" + DirConfig.PATH_APP_FILE + mOrgFileNm);
			}
			// EXCEL 파일 업로드일 경우
			else if (UploadConfig.FILE_TYPE_EXCEL.equals(mUploadType)) {
				String excel_path = temp_path + File.separator + mNewFileNm;
				List<List<List<String>>> sheetList = NWExcel.excel(excel_path, uploadFileExt);
				List<HashMap<String, Object>> info = new ArrayList<HashMap<String, Object>>();
				List<HashMap<String, Object>> prodCodeList = new ArrayList<HashMap<String, Object>>();
				List<List<String>> rowList = sheetList.get(0);
				for (int r = 0; r < rowList.size(); r++) {
					List<String> cellList = rowList.get(r);
					HashMap<String, Object> cell = new HashMap<String, Object>();
					cell.put("PROD_SEQ", cellList.get(0));
					cell.put("GROUP_NAME", cellList.get(1));
					cell.put("MODEL_NAME", cellList.get(2));
					cell.put("DELV_CNT", cellList.get(3));
					cell.put("BUY_PRICE", cellList.get(4));
					cell.put("APPLY_PRICE", cellList.get(5));
					cell.put("SPEC_TEXT", cellList.get(6));
					cell.put("SETT_TEXT", cellList.get(7));
					info.add(cell);
				}
				rowList = sheetList.get(1);
				for (int r = 0; r < rowList.size(); r++) {
					List<String> cellList = rowList.get(r);
					HashMap<String, Object> cell = new HashMap<String, Object>();
					cell.put("PROD_SEQ", cellList.get(0));
					cell.put("ITEM_NO", cellList.get(1));
					cell.put("PROD_CODE", cellList.get(2));
					prodCodeList.add(cell);
				}
				//param.put("INFO", info);
				//param.put("LIST", prodCodeList);
				jsonRoot.put("INFO", info);
				jsonRoot.put("LIST", prodCodeList);
			}
			else {
				uploadFilePath = UploadConfig.PATH_TEMP_FILE + "file"+ File.separator;

				NWFile.fileCopy(this.getClass(), temp_path +File.separator + mNewFileNm,  uploadFilePath + mOrgFileNm);
				if(!"".equals(mNewFileNm)) NWFile.deleteTempFile(this.getClass(), temp_path , mNewFileNm);
				NWLog.i(this.getClass(), "[파일 저장] :" + uploadFilePath + mOrgFileNm);
			}

			// 5. 리턴
			//if(!"".equals(mOrgFileNm)) {
			//	param.put("RETURN_CODE", "200");
			//	NWLog.i(this.getClass(), "[리턴] :" +"OK|"+param.toString());
			//	action = jsonForward(action, resParser.success(mUploadType, ""));
			//}
			action = jsonForward(action, jsonRoot);

		} catch (Exception e) {
			NWFile.deleteTempFile(this.getClass(), uploadFilePath , mNewFileNm);

			e.printStackTrace();
			
			action = jsonForward(action, resParser.error(mUploadType, "업데이트 실패"));
		}

		return action;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private ArrayList<HashMap<String, Object>> executeDatabase(String uploadType, String userId, String extraData) throws Exception {
		NWLog.i(this.getClass(), "extraData:" + extraData);
		
		LinkedHashMap<String, Object> paramMap1 = new LinkedHashMap<String, Object>();
		org.json.JSONObject jsonData = new org.json.JSONObject(extraData);
		Iterator<Object> iter = jsonData.keys();
		
		while (iter.hasNext()) {
			String k = (String) iter.next();
			String v = (String) jsonData.get(k);
			paramMap1.put(k, v);
		}
		NWLog.i(this.getClass(), "paramMap1:" + paramMap1);
		
		DbDao dao = new DbDao(userId);
		
		
		dao.beginTrans();
		
		if(UploadConfig.FILE_TYPE_APK.equals(uploadType)){
			dao.queryForUpdate("ASMaster.setVerUpdate", paramMap1);
			if(dao.IsResult()==false) {
				dao.rollbackTrans();
				dao.endTrans();
				return null;
			}
		}
		
		dao.commitTrans();
		dao.endTrans();
		
		ArrayList<HashMap<String, Object>> mapResult = new ArrayList<HashMap<String, Object>>();;
		
		if(UploadConfig.FILE_TYPE_APK.equals(uploadType)){
			mapResult = dao.queryForList("ASMaster.getVersionInfo", paramMap1);
		}
		
		return mapResult;
	}
}









