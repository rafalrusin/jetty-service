package org.apache.jetty.service.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jetty.service.api.JettyService;
import org.apache.jetty.service.util.BundleResource;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.ResourceHandler;
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
		        response.getWriter().println("<h1>Hello World from Java</h1>" + request.getParameterMap());
		        
		        Request base_request = (request instanceof Request) ? (Request)request:HttpConnection.getCurrentConnection().getRequest();
		        base_request.setHandled(true); 
			}
		});
		ResourceHandler r = new ResourceHandler();
		
		System.out.println(getClass().getResource("/static"));
		
//		Resource r2 = new URLResource(getClass().getResource("/static"), new URLConnection(getClass().getResource("/static")));
		Resource r2 = new BundleResource(getClass().getResource("/static"));
//		Resource r2 = new FileResource(getClass().getResource("/static"));
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
