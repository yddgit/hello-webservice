package com.my.project.jaxws;

import javax.jws.WebService;

import com.my.project.jaxws.model.User;

/**
 * SIB(Service Implementation Bean)
 * @author yang
 */
@WebService(endpointInterface = "com.my.project.jaxws.IMyService")
public class MyServiceImpl implements IMyService {

	@Override
	public Integer add(Integer a, Integer b) {
		System.out.println(a + "+" + b + "=" + (a+b));
		return a + b;
	}

	@Override
	public Integer minus(Integer a, Integer b) {
		System.out.println(a + "-" + b + "=" + (a-b));
		return a - b;
	}

	@Override
	public User login(String username, String password) {
		System.out.println("User: " + username + " login...");
		User u = new User();
		u.setId(1);
		u.setUsername(username);
		u.setPassword(password);
		return u;
	}

}
