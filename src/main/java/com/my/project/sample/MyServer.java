package com.my.project.sample;

import javax.xml.ws.Endpoint;

public class MyServer {
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9000/myService", new MyServiceImpl());
	}
}
