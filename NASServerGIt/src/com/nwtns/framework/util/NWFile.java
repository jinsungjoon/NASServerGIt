package com.nwtns.framework.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;

import com.nwtns.framework.exception.ActionKeyException;
import com.oreilly.servlet.MultipartRequest;

public class NWFile {

	public static String FILE_S = File.separator;

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param strFileName
	 * @throws Exception
	 *******************************************************************************/
	public static void checkNotFile(String strFileName) throws Exception {

		String notFileList[] = NWEncoding.str2strs(strFileName, "/");
		if (notFileList != null) {
			for (int i = 0; i < notFileList.length; i++) {
				if (getFileExt(strFileName).equalsIgnoreCase(notFileList[i].trim())) {
					throw new ActionKeyException("파일의 확장자가 " + notFileList[i] + "인 파일은 업로드가 금지됩니다.");
				}

			}
		}

	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param filPath
	 * @param fileName
	 * @param in
	 * @throws Exception
	 *******************************************************************************/
	public static void saveFile(String filPath, String fileName, InputStream in) throws Exception {

		File file = new File(filPath + fileName);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(file));

		int i;
		byte[] buffer = new byte[1024 * 4];

		while ((i = in.read(buffer)) > -1) {
			os.write(buffer, 0, i);
		}

		os.close();
		in.close();

	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param filPath
	 * @param fileNames
	 * @throws Exception
	 *******************************************************************************/
	public static void deleteFile(String filPath, String fileNames[]) throws Exception {

		// file delete
		if (fileNames != null) {
			File delFile = null;
			// 파일지우기
			for (int i = 0; i < fileNames.length; i++) {
				delFile = new File(filPath + fileNames[i]);
				delFile.delete();
			}
		}// end if(fileNames!= null)

	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param filPath
	 * @param fileName
	 * @throws Exception
	 *******************************************************************************/
	public static void deleteFile(String filPath, String fileName) throws Exception {

		// file delete
		if (!NWUtil.isEmpty(fileName)) {
			File delFile = null;
			// 파일지우기
			delFile = new File(filPath + fileName);
			delFile.delete();

		}// end if(fileNames!= null)

	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param fullPathWithFileName
	 * @throws Exception
	 *******************************************************************************/
	public static void deleteFile(String fullPathWithFileName) throws Exception {

		// file delete
		if (!NWUtil.isEmpty(fullPathWithFileName)) {
			File delFile = null;
			// 파일지우기
			delFile = new File(fullPathWithFileName);
			delFile.delete();

		}// end if(fileNames!= null)

	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param filesize
	 * @return
	 * @throws Exception
	 *******************************************************************************/
	public static String fileSize(long filesize) throws Exception {
		DecimalFormat df = new DecimalFormat(".##");
		String fSize = "";
		try {
			if ((filesize > 1024) && (filesize < 1024 * 1024)) {
				fSize = df.format((float) filesize / 1024).toString() + " KB";
			} else if (filesize >= 1024 * 1024) {
				fSize = df.format((float) filesize / (1024 * 1024)).toString() + " MB";
			} else {
				fSize = Long.toString(filesize) + " B";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return fSize;
	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param from
	 * @param to
	 * @throws Exception
	 *******************************************************************************/
	public static void moveFile(File from, File to) throws Exception {

		int fileSize = new Long(from.length()).intValue(); // bytes
		FileInputStream fis = new FileInputStream(from);
		FileOutputStream fos = new FileOutputStream(to);

		int a = 0, j = 0;
		byte[] b = new byte[1024];
		while (j < fileSize) {
			a = fis.read(b);
			fos.write(b, 0, a);
			j += a;
		}

		fos.close();
		fis.close();
		from.delete();

	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param from
	 * @param to
	 * @throws Exception
	 *******************************************************************************/
	public static void copyFile(File from, File to) throws Exception {

		int fileSize = new Long(from.length()).intValue(); // bytes
		FileInputStream fis = new FileInputStream(from);
		FileOutputStream fos = new FileOutputStream(to);

		int a = 0, j = 0;
		byte[] b = new byte[1024];
		while (j < fileSize) {
			a = fis.read(b);
			fos.write(b, 0, a);
			j += a;
		}

		fos.close();
		fis.close();

	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param strpath
	 * @throws Exception
	 *******************************************************************************/
	public static void dirMake(String strpath) throws Exception {
		File file = new File(strpath);
		if (!file.exists())
			file.mkdir();
	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param strpath
	 * @throws Exception
	 *******************************************************************************/
	public static void fullDirMake(String strpath) throws Exception {
		if (new File(strpath).exists())
			return;

		String dir[] = NWEncoding.str2strs(strpath, FILE_S);
		String ls_makeDir = "";
		if (dir != null && dir.length > 0) {
			for (int i = 0; i < dir.length; i++) {
				if (i == 0) {
					ls_makeDir = dir[i] + FILE_S;
					continue;
				}

				ls_makeDir += dir[i] + FILE_S;
				dirMake(ls_makeDir);
			}
		}
	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param strFileName
	 * @return
	 * @throws Exception
	 *******************************************************************************/
	public static String getFileExt(String strFileName) throws Exception {
		int index = strFileName.lastIndexOf(".");
		if (index == -1)
			return "";

		return strFileName.substring(index + 1);
	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param strFileName
	 * @return
	 * @throws Exception
	 *******************************************************************************/
	public static String getFileName(String filepath) {
		int file = filepath.lastIndexOf(File.separator);
		int ext = filepath.lastIndexOf(".");
		int length = filepath.length();
		String filename = filepath.substring(file + 1, ext);

		String extname = filepath.substring(ext + 1, length);

		String fullfilename = filename + "." + extname;

		return fullfilename;
	}
	public static String getFileNameRep(String strFileName) throws Exception {
		int index = strFileName.lastIndexOf(".");
		String tmpFileName = "";
		String tmpExtName = "";
		// System.out.println(strFileName);
		if (index == -1) {
			tmpFileName = strFileName;
			tmpExtName = "";

		} else {
			tmpFileName = strFileName.substring(0, index);
			tmpExtName = strFileName.substring(index);
		}

		String result = tmpFileName.replaceAll(":", "_");
		result = result.replaceAll(";", "_");
		result = result.replaceAll("/", "_");
		result = result.replaceAll("`", "_");
		result = result.replaceAll("\\?", "_");
		result = result.replaceAll("<", "_");
		result = result.replaceAll(">", "_");
		result = result.replaceAll("\\.", "_");

		result = result + tmpExtName;

		return result;
	}

/*	*//********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param request
	 * @param form
	 * @param fileSize
	 * @param path
	 * @return
	 * @throws Exception
	 *******************************************************************************//*
	public static Vector FileLoad(HttpServletRequest request, ActionForm form, int fileSize, String path, String returnURL) throws Exception {
		request.getSession().setAttribute("returnURL", returnURL);
		return FileLoad(request, form, fileSize, path);
	}

	public static Vector FileLoad(HttpServletRequest request, ActionForm form, int fileSize, String path) throws Exception {

		Log.log("============Starting File upload================");
		Log.log(path);

		Vector vfiles = new Vector();
		UploadFile FILES_VALUE = new UploadFile();

		CommonsMultipartRequestHandler parser = (CommonsMultipartRequestHandler) form.getMultipartRequestHandler();
		Hashtable file_elements = parser.getFileElements();
		Enumeration keys = file_elements.keys();

		int size_Of_Files = 0;

		while (keys.hasMoreElements()) {

			String key = (String) keys.nextElement();
			FormFile file = (FormFile) file_elements.get(key);

			System.out.println(key);

			if (!NWUtil.isEmpty(file.getFileName())) {

				String FILE_REAL_NAME = file.getFileName();
				String FILE_SYS_NAME = String.valueOf(getUniq()) + "." + getFileExt(FILE_REAL_NAME);

				if (file != null && !NWUtil.isEmpty(FILE_SYS_NAME)) {
					size_Of_Files += file.getFileSize();
					// 금지파일 검사
					NWFile.checkNotFile(FILE_SYS_NAME);
				}

				if (size_Of_Files > 1024 * 1024 * fileSize) {
					throw new AlertException("파일 용량이 " + fileSize + "M 를 초과 하였습니다.");
				} else {
					if (file != null && !NWUtil.isEmpty(FILE_SYS_NAME)) {

						// 디렉토리 생성
						NWFile.fullDirMake(path);

						FILES_VALUE = new UploadFile();

						// 실제 파일 저장
						NWFile.saveFile(path + NWFile.FILE_S, FILE_SYS_NAME, file.getInputStream());

						if (getFileExt(FILE_REAL_NAME).toLowerCase().equals("jpg") || getFileExt(FILE_REAL_NAME).toLowerCase().equals("gif") || getFileExt(FILE_REAL_NAME).toLowerCase().equals("bmp")|| getFileExt(FILE_REAL_NAME).toLowerCase().equals("png")) {
							NWImage.resize(new File(path + NWFile.FILE_S+ FILE_SYS_NAME), path + NWFile.FILE_S+"Thumbnail"+ NWFile.FILE_S, FILE_SYS_NAME, 150, 150);
						}

						// commonsUpload(request, res);

						FILES_VALUE.setSYS_FILE_NAME(FILE_SYS_NAME);
						FILES_VALUE.setFILE_REAL_NAME(FILE_REAL_NAME);
						FILES_VALUE.setFilesize(file.getFileSize());

						Log.log("FILE_SYS_NAME : " + FILE_SYS_NAME);
						Log.log("FILE_REAL_NAME : " + FILE_REAL_NAME);

						vfiles.add(FILES_VALUE);

					} else {
						throw new AlertException("업로드 중 문제 발생");
					}
				}
			} else {
				FILES_VALUE = new UploadFile();
				FILES_VALUE.setSYS_FILE_NAME("");
				FILES_VALUE.setFILE_REAL_NAME("");
				FILES_VALUE.setFilesize(0);
				vfiles.add(FILES_VALUE);
			}
		}

		Log.log("============End File upload ================");

		return vfiles;
	}*/

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @param path
	 * @param filename
	 * @return
	 *******************************************************************************/
	public static boolean existFile(String path, String filename) {
		boolean i = false;
		try {
			File f = new File(path, filename);
			i = f.exists();
		} catch (Exception e) {
			i = false;
		}
		return i;
	}

	/********************************************************************************
	 * 작성일 : 2009. 10. 29. 작성자 : 최정주
	 *
	 * @return
	 *******************************************************************************/
	public static long getUniq() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long l;
		synchronized (new NWFile()) {
			l = System.currentTimeMillis();
		}
		return l;
	}







	public static boolean deleteTempFile(Class cls, String path, String filename) {
		File file = new File(path, filename);
		if(file.exists())
			return file.delete();

		return false;
	}


	/**
	 * 서버에 파일을 업로드 한다.
	 * @param multi
	 * @param savePath
	 * @return
	 * @throws Exception
	 */
	public static String multiUpload(Class cls, MultipartRequest multi, String oriSavePath, String savePath, String fileName) throws Exception {
		Enumeration files = multi.getFileNames();

		while (files.hasMoreElements()) {

			String name = (String) files.nextElement();
			String original = multi.getOriginalFileName(name);

			File f = multi.getFile(name);
			int pos = original.lastIndexOf(".");
			String fileExt = original.substring(pos + 1);
			fileName = fileName + "." + fileExt;

			fileCopy(cls, oriSavePath + File.separator + original, savePath + File.separator + fileName);

			NWLog.i(cls, "[파일저장]"+savePath + File.separator + fileName);
			if (f.exists()) {
				f.delete();
			}
		}

		return fileName;
	}

	public static HashMap<String, String> multiUpload2(Class cls, MultipartRequest multi, String oriSavePath, String savePath, String fileName) throws Exception {
		HashMap<String, String> hm= new HashMap<String, String>();
		Enumeration files = multi.getFileNames();

		while (files.hasMoreElements()) {
			String name = (String) files.nextElement();
			String original = multi.getOriginalFileName(name);
			//NWEncoding.charSet(original);
			//original = new String((multi.getOriginalFileName(name)).getBytes("KSC5601"),"8859_1");
			hm.put("ORG_NM", original);

			File f = multi.getFile(name);
			int pos = original.lastIndexOf(".");
			String fileExt = original.substring(pos + 1);
			fileName = fileName + "." + fileExt;
			hm.put("FILE_NM", fileName);
			hm.put("FILE_EXT", fileExt);
			fileCopy(cls, oriSavePath + File.separator + original, savePath + File.separator + fileName);

			NWLog.i(cls, "[파일저장]"+savePath + File.separator + fileName);
			if (f.exists()) {
				f.delete();
			}
		}

		return hm;
	}


	/**
	 * 파일을 복사한다.
	 *
	 * @param inFileName
	 * @param outFileName
	 */
	public static void fileCopy(Class cls, String inFileName, String outFileName) throws Exception{
		NWLog.i(cls, "[COPY IN]"+inFileName);
		NWLog.i(cls, "[COPY OUT]"+outFileName);

		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(inFileName);
			fos = new FileOutputStream(outFileName);

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = fis.read(buffer, 0, 1024)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}

			fis.close();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fis != null) fis.close();
			if(fos != null) fos.close();
		}
	}

	/**
	 * 폴더 유무를 확인하여 없으면 폴더를 생성한다.
	 * @param path
	 * @return
	 */
	public static boolean makeDirectory(Class cls, String path) {
		boolean bool_dir = false;
		File Directory = new File(path); // 폴더명
		NWLog.i(cls, Directory.getPath()+"|"+Directory.getAbsolutePath()+"|"+Directory.exists());
		if (!Directory.exists()) {
			Directory.mkdirs();
			bool_dir = true;
			NWLog.i(cls, "[디렉토리생성]" + path);
		}
		return bool_dir;
	}
	public static boolean makeDirectory(String path) {
		boolean bool_dir = false;
		File Directory = new File(path); // 폴더명
		if (!Directory.exists()) {
			Directory.mkdirs();
			bool_dir = true;
		}
		return bool_dir;
	}

	public static boolean deleteDirectory(File path) {
		if (!path.exists()) {
			return false;
		}

		File[] files = path.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				deleteDirectory(file);
			} else {
				file.delete();
			}
		}

		return path.delete();
	}
}
