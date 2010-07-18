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

public class HelloWorld {
	
	private JettyService jettyService;
	private Handler registered;
	
	public void setJettyService(JettyService jettyService) {
		this.jettyService = jettyService;
	}

	public void init() throws Exception {
		ContextHandlerCollection handler = new ContextHandlerCollection();
		
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
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setBaseResource(new BundleResource(getClass().getResource("/static")));
		ContextHandler contextHandler = handler.addContext("","");
		contextHandler.setHandler(resourceHandler);
		
		registered = jettyService.registerApp("helloWorld", handler);
	}
	
	public void destroy() throws Exception {
		jettyService.unregisterApp(registered);
	}
}
