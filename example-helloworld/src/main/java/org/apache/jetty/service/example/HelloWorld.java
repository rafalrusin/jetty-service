package org.apache.jetty.service.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jetty.service.api.JettyService;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.resource.FileResource;
import org.mortbay.resource.Resource;

public class HelloWorld {
	
	private JettyService jettyService;
	private ContextHandlerCollection handler;
	private Handler reg;
	
	public void setJettyService(JettyService jettyService) {
		this.jettyService = jettyService;
	}

	public void init() throws Exception {
		handler = new ContextHandlerCollection();
		
		handler.addContext("/app", "app").setHandler(new AbstractHandler() {
			public void handle(String target, HttpServletRequest request,
					HttpServletResponse response, int arg3) throws IOException,
					ServletException {
		        response.setContentType("text/html");
		        response.setStatus(HttpServletResponse.SC_OK);
		        response.getWriter().println("<h1>Hello World Handler</h1>");
		        
		        Request base_request = (request instanceof Request) ? (Request)request:HttpConnection.getCurrentConnection().getRequest();
		        base_request.setHandled(true); 
			}
		});
		ResourceHandler r = new ResourceHandler();
		
		Resource r2 = new FileResource(getClass().getResource("/static"));
		r.setBaseResource(r2);
		ContextHandler ch = handler.addContext("","");
		ch.setHandler(r);
		ch.setClassLoader(getClass().getClassLoader());
		
		reg = jettyService.registerApp("helloWorld", handler);
	}
	
	public void destroy() throws Exception {
		jettyService.unregisterApp(reg);
	}
}
