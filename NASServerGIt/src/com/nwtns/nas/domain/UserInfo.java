package com.nwtns.nas.domain;

/** 
 * @date : 
 * @author : mark
 * @purpose :
 */
public class UserInfo {
	
	private String LOGINID;
    private String PASSWORD;
    private String PDANUM;
    private String EMPNO;
    private String HNAME;
    private String JOBCOD;
    private String DEPCOD;
    private String PRSNUM;
    private String DIV;
    private String DELLOC;
    private String SALARA;
    private String SALRTE;
    private String EMAIL;
    private String STATUS;
    private String ROLE;
    private String TEAM;
    
	public String getROLE() {
		return ROLE;
	}
	public void setROLE(String role) {
		ROLE = role;
	}
	public String getTEAM() {
		return TEAM;
	}
	public void setTEAM(String team) {
		TEAM = team;
	}
	public String getLOGINID() {
		return LOGINID;
	}
	public void setLOGINID(String loginid) {
		LOGINID = loginid;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String password) {
		PASSWORD = password;
	}
	public String getPDANUM() {
		return PDANUM;
	}
	public void setPDANUM(String pdanum) {
		PDANUM = pdanum;
	}
	public String getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(String empno) {
		EMPNO = empno;
	}
	public String getHNAME() {
		return HNAME;
	}
	public void setHNAME(String hname) {
		HNAME = hname;
	}
	public String getJOBCOD() {
		return JOBCOD;
	}
	public void setJOBCOD(String jobcod) {
		JOBCOD = jobcod;
	}
	public String getDEPCOD() {
		return DEPCOD;
	}
	public void setDEPCOD(String depcod) {
		DEPCOD = depcod;
	}
	public String getPRSNUM() {
		return PRSNUM;
	}
	public void setPRSNUM(String prsnum) {
		PRSNUM = prsnum;
	}
	public String getDIV() {
		return DIV;
	}
	public void setDIV(String div) {
		DIV = div;
	}
	public String getDELLOC() {
		return DELLOC;
	}
	public void setDELLOC(String delloc) {
		DELLOC = delloc;
	}
	public String getSALARA() {
		return SALARA;
	}
	public void setSALARA(String salara) {
		SALARA = salara;
	}
	public String getSALRTE() {
		return SALRTE;
	}
	public void setSALRTE(String salrte) {
		SALRTE = salrte;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String email) {
		EMAIL = email;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String status) {
		STATUS = status;
	}
	/**
		 * 
		 * @return 
		 * @author 
		 */
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("UserInfo[");
			buffer.append("DELLOC = ").append(DELLOC);
			buffer.append(" DEPCOD = ").append(DEPCOD);
			buffer.append(" DIV = ").append(DIV);
			buffer.append(" EMAIL = ").append(EMAIL);
			buffer.append(" EMPNO = ").append(EMPNO);
			buffer.append(" HNAME = ").append(HNAME);
			buffer.append(" JOBCOD = ").append(JOBCOD);
			buffer.append(" LOGINID = ").append(LOGINID);
			buffer.append(" PASSWORD = ").append(PASSWORD);
			buffer.append(" PDANUM = ").append(PDANUM);
			buffer.append(" PRSNUM = ").append(PRSNUM);
			buffer.append(" SALARA = ").append(SALARA);
			buffer.append(" SALRTE = ").append(SALRTE);
			buffer.append(" STATUS = ").append(STATUS);
			buffer.append("]");
			return buffer.toString();
		}
	
	
}
