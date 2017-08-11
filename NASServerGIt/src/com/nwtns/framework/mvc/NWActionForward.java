package com.nwtns.framework.mvc;

public class NWActionForward {
	private String url;
	private boolean redirect;
	private boolean isAjax = false;
	private String resData;
	private String timeout = "";
	public void setUrl(String url) {
		this.url = url;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public String getUrl() {
		return url;
	}

	public boolean idRedirect() {
		return redirect;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public boolean isAjax() {
		return isAjax;
	}

	public void setAjax(boolean isAjax) {
		this.isAjax = isAjax;
	}

	public String getResData() {
		return resData;
	}

	public void setResData(String resData) {
		this.resData = resData;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

}
