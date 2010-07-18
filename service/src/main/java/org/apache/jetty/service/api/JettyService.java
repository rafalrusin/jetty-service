package org.apache.jetty.service.api;

import org.mortbay.jetty.Handler;

public interface JettyService {
	public Handler registerApp(String name, Handler handler) throws Exception;
	public void unregisterApp(Handler handler) throws Exception;
}
