package com.my.project.soap;

import javax.jws.WebService;

/**
 * SIB(Service Implementation Bean)
 * @author yang
 */
@WebService(endpointInterface = "com.my.project.soap.IMyService")
public class MyServiceImpl implements IMyService {

	@Override
	public Integer add(Integer a, Integer b) {
		System.out.println(a + "+" + b + "=" + (a+b));
		return a + b;
	}

}
