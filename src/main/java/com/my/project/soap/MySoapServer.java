package com.my.project.soap;

import javax.xml.ws.Endpoint;

/**
 * JAX-WS(Java API XML WebService)
 * @author yang
 */
public class MySoapServer {

	public static final String namespace = "http://project.my.com/webservice";

	public static void main(String[] args) {
		String address = "http://localhost:9000/myService";
		Endpoint.publish(address, new MyServiceImpl());
	}
}
