package com.nwtns.framework.log;

import java.io.File;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;


public class Log4jInit extends HttpServlet {
	private static final long serialVersionUID = -5104057715091654401L;

	public void init() {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j-init-file");
		
		if(file.startsWith("/")){
			file = file.substring(1, file.length());
		}
		
		if(prefix.endsWith(File.separator)){
			prefix = prefix.substring(0, prefix.length() - 1);
		}
		
		if (file != null) {
			PropertyConfigurator.configure(prefix +File.separator+ file);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		// Nothing happening here yet.
	}
}