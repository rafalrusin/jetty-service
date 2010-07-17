package org.apache.jetty.service.api;

import org.mortbay.jetty.Handler;

public interface JettyService {
	public void registerApp(String name, Handler handler);
	public void unregisterApp(Handler handler);
}
