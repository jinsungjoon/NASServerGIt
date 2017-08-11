package com.nwtns.framework.exception;

public final class AlertException extends Exception {

	public AlertException(String msg) {
		this.msg = msg;
	}

	public AlertException(String msg, String returnURL) {
		this.msg = msg;
		this.returnURL = returnURL;
	}

	public String getMessage() {
		return msg;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public String msg;
	public String returnURL;
}
