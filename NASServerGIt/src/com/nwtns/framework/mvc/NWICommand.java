package com.nwtns.framework.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.nwtns.framework.exception.ActionKeyException;

/**
 * Command에서 사용되어지는 인터페이스 정의
 * @author 최정주
 *
 */
public interface NWICommand {
	
	/**
	 * MVC2 ActionForward 클래스 
	 * @param req
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws ActionKeyException
	 */
	public NWActionForward execute(HttpServletRequest req) throws ServletException, IOException, ActionKeyException;

	//public NWActionForward service(HttpServletRequest req) throws Exception;
}
