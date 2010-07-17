package org.apache.jetty.service.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jetty.service.api.JettyService;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.ResourceHandler;

public class HelloWorld {
	
	private JettyService jettyService;
	private ContextHandlerCollection handler;
	
	public void setJettyService(JettyService jettyService) {
		this.jettyService = jettyService;
	}

	public void init() {
		handler = new ContextHandlerCollection();
		handler.addContext("app", "app").setHandler(
			new AbstractHandler() {
			public void handle(String target, HttpServletRequest request,
					HttpServletResponse response, int arg3) throws IOException,
					ServletException {
		        response.setContentType("text/html");
		        response.setStatus(HttpServletResponse.SC_OK);
		        response.getWriter().println("Hello World Handler");
			}
		});
		handler.addContext("","static").setHandler(new ResourceHandler());
		
		jettyService.registerApp("helloWorld", handler);
	}
	
	public void destroy() {
		jettyService.unregisterApp(handler);
	}
}
