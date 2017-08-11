package com.nwtns.nas.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nwtns.framework.exception.ActionKeyException;
import com.nwtns.framework.mvc.NWActionForward;
import com.nwtns.framework.mvc.NWICommand;
import com.nwtns.framework.mvc.NWMainServlet;
import com.nwtns.framework.util.NWLog;

public class MainServlet extends NWMainServlet {

	@Override
	public NWActionForward initAction(HttpServletRequest req,
			HttpServletResponse res, String path) throws ServletException,
			IOException, ActionKeyException {
		CommandFactory factory = CommandFactory.getInstance();
		NWICommand command = factory.createCommand(path);
		
		NWLog.d(this.getClass(), "================== MainServlet START ==================" + path);
		
//		StringBuilder sb = new StringBuilder();
//		sb.append(String.format("▷ %s ", Conf.PRJ_NM));
//		if(req.getParameter("userId") != null)		sb.append(String.format("[%s]", "jsj"));
//		if(req.getParameter("serviceFlag") != null)	sb.append(String.format(" %s", req.getParameter("serviceFlag").toString()));
//		NWLog.i(this.getClass(), sb.toString());
		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");
		NWLog.i(this.getClass(),req.getParameter("userId"));
		
		NWActionForward action = command.execute(req);

		NWLog.d(this.getClass(), "================== MainServlet END ==================" + path);
		return action;
	}

}
