package com.nwtns.framework.enc;


public class Base64Utils {

	// base64 디코딩
	public String base64Decoding(String encodedString) {
		String retVal = "";

		try {
			byte[] plainText = null; // 해쉬 값

			base64 b64 = new base64();
			plainText = b64.decode(encodedString);

			retVal = new String(plainText);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return retVal;
	} 
	
	public String base64Decoding(String encodedString, String enc) {
		String retVal = "";

		try {
			byte[] plainText = null; // 해쉬 값

			base64 b64 = new base64();
			plainText = b64.decode(encodedString);

			retVal = new String(plainText, enc);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return retVal;
	} 
	
	public byte[] base64DecodingToByte(String encodedString) {
		try {
			base64 b64 = new base64();
			return b64.decode(encodedString);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	} 

	// base64 인코딩
	public String base64Encoding(String value) {
		String retVal = "";

		try {
			byte[] plainText = null; // 평문
			plainText = value.getBytes();

			base64 b64 = new base64();
			retVal = b64.encode(plainText);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return retVal;
	}
	
	public String base64Encoding(String value, String enc) {
		String retVal = "";

		try {
			byte[] plainText = null; // 평문
			plainText = value.getBytes(enc);

			base64 b64 = new base64();
			retVal = b64.encode(plainText);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return retVal;
	}

	// Base64 + Seed 복호화
	// 2014.02.13[KDBAEK] UTF-8 추가
	public String decrypt(String str, String key) {
		if (key.length() != 16) {
			return "";
		}

		try {
			String strResult;
			String strTemp = "";
			strResult = "";

			SeedAlg seedAlg = new SeedAlg(key.getBytes());
			base64 b64 = new base64();
			strTemp = new String(seedAlg.decrypt(b64.decode(str)), "UTF-8");
			for (int i = 0; i < strTemp.length() && strTemp.charAt(i) != 0;) {
				if (strTemp.charAt(i) != '\n' && strTemp.charAt(i) != '\r') {
					strResult = strResult + strTemp.charAt(i);
					i++;
				}
			}

			return strResult;
		} catch (Exception ex) {
			return null;
		}
	}

	// Base64 + Seed 암호화
	// 2014.02.13[KDBAEK] UTF-8 추가
	public String encrypt(String str, String key) {
		if (key.length() != 16) {
			return "";
		}
		try {
			String strResult;
			String strTemp = "";
			strResult = "";

			SeedAlg seedAlg = new SeedAlg(key.getBytes());
			base64 b64 = new base64();
			strTemp = new String(b64.encode(seedAlg.encrypt(str.getBytes("UTF-8"))));
			for (int i = 0; i < strTemp.length(); i++) {
				if (strTemp.charAt(i) != '\n' && strTemp.charAt(i) != '\r') {
					strResult = strResult + strTemp.charAt(i);
				}
			}
			return strResult;
		} catch (Exception ex) {
			return null;
		}

	}

	public static void main(String args[]) {
		Base64Utils base = new Base64Utils(); // 요놈선언
		String encryptKey = "abcdefghijktttld"; // key 선언

		String W_ORG_FG = "암호화해야 할 문장";

		// seed + base64 암호화, 복호화
		String EN_ORG_FG = base.encrypt(W_ORG_FG, encryptKey);
		String DE_ORG_FG = base.decrypt(EN_ORG_FG, encryptKey);

//		System.out.println("==========================================");
//		System.out.println("원문: " + W_ORG_FG);
//		System.out.println("==========================================\n");
//		
//		System.out.println("SEED+BASE64 암호화 결과: " + EN_ORG_FG);
//		System.out.println("SEED+BASE64 복호화 결과: " + DE_ORG_FG);
//		System.out.println("==========================================\n");
		
		// base64로만 암호화, 복호화
		EN_ORG_FG = base.base64Encoding(W_ORG_FG);
		DE_ORG_FG = base.base64Decoding(EN_ORG_FG);

//		System.out.println("BASE64  암호화 결과 : " + EN_ORG_FG);
//		System.out.println("BASE64 복호화 결과 : " + DE_ORG_FG);
//		System.out.println("==========================================");
	}

}