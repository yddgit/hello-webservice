package com.my.project.jaxws;

import javax.xml.ws.Endpoint;

/**
 * JAX-WS(Java API XML WebService)
 * @author yang
 */
public class MyServer {
	public static void main(String[] args) {
		String address = "http://localhost:9000/myService";
		Endpoint.publish(address, new MyServiceImpl());
	}
}
