package com.joyme.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-1-23
 * Time: 上午10:08
 */
public class InitServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		MessageService service = new MessageService();
		try {
			service.loadMessagePropertis();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
