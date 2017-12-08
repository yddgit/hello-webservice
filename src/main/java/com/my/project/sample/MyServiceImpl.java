package com.my.project.sample;

import javax.jws.WebService;

import com.my.project.sample.wsimport.IMyService;

@WebService(
	endpointInterface = "com.my.project.sample.wsimport.IMyService",
	targetNamespace = "http://www.example.org/myService/",
	wsdlLocation = "META-INF/wsdl/myService.wsdl",
	serviceName = "MyServiceImpl")
public class MyServiceImpl implements IMyService {

	@Override
	public int times(String license, int num1, int num2) {
		System.out.println("[Server]Client license: " + license);
		return num1 * num2;
	}

	@Override
	public int divide(int num1, int num2) {
		return num1 / num2;
	}

}
