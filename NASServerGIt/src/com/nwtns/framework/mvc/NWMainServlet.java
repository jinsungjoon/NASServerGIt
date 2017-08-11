package com.nwtns.framework.mvc;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nwtns.framework.exception.ActionKeyException;
import com.nwtns.framework.util.NWLog;
/**
 * 
 * @author 최정주
 *
 */
abstract public class NWMainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ServletContext context;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
//			Configuration.getInstance();
		} catch (Exception e) {
		//	NWLog.d(this.getClass(), e.toString());
		}
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
		NWLog.d(this.getClass(), request.getParameter("serviceFlag"));
		NWLog.d(this.getClass(), "doGet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
		NWLog.d(this.getClass(), "doPost");
	}

	protected void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String path = req.getRequestURI();
		//NWLog.d(this.getClass(), "path===============================================================>" + path);

		try {
			NWActionForward action = initAction(req, res, path);
			if (action != null) {
				if (action.isAjax()){
					res.setHeader("Content-Type","text/html;charset=UTF-8");
					PrintWriter out = new PrintWriter(new OutputStreamWriter(res.getOutputStream(), "UTF-8"), true);
					out.print(action.getResData());
					out.flush();
					out.close();
				} else {
					if (action.idRedirect()) {
						res.sendRedirect(action.getUrl());
					} else {
						RequestDispatcher rd = req.getRequestDispatcher(action.getUrl());

						rd.forward(req, res);
					}
				}
			}
		} catch (Exception e) {

			NWLog.e(this.getClass(),e);
		}
	}
	
	abstract public NWActionForward initAction(HttpServletRequest req,
			HttpServletResponse res, String path) throws ServletException,
			IOException, ActionKeyException;
}
