package org.apache.jetty.service;

import org.apache.jetty.service.api.JettyService;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.handler.ContextHandlerCollection;

public class JettyServiceImpl implements JettyService {
	private Server server;
	private ContextHandlerCollection rootContext;
	
	public void init() throws Exception {
		server = new Server(8080);
		rootContext = new ContextHandlerCollection();
		server.setHandler(rootContext);
		server.start();
	}
	
	public void destroy() throws Exception {
		server.stop();
	}
	
	public Handler registerApp(String name, Handler handler) throws Exception {
		server.stop();
		ContextHandler h = rootContext.addContext("/" + name, name);
		h.setHandler(handler);
		server.start();
		return h;
	}
	
	public void unregisterApp(Handler handler) throws Exception {
		server.stop();
		rootContext.removeHandler(handler);
		server.start();
	}
}
