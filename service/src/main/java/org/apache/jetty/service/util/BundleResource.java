package org.apache.jetty.service.util;

import java.net.URL;

import org.mortbay.resource.URLResource;

public class BundleResource extends URLResource {

	public BundleResource(URL url) {
		super(url, null);
	}

	private static final long serialVersionUID = 1L;
}
