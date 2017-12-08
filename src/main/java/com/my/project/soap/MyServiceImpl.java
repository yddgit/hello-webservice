package com.my.project.soap;

import java.util.ArrayList;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import com.my.project.soap.model.User;

/**
 * SIB(Service Implementation Bean)
 * @author yang
 */
// 实现类的targetNamespace需要与接口的targetNamespace相同
@WebService(
	endpointInterface = "com.my.project.soap.IMyService",
	targetNamespace = "http://project.my.com/webservice")
@HandlerChain(file = "handler-chain-server.xml")
public class MyServiceImpl implements IMyService {

	private static List<User> users = new ArrayList<User>();

	/**
	 * 在构造函数中初始化users
	 */
	public MyServiceImpl() {
		User u = new User();
		u.setId(1);
		u.setUsername("admin");
		u.setNickname("管理员");
		u.setPassword("111111");
		users.add(u);
	}

	@Override
	public Integer add(Integer a, Integer b) {
		System.out.println(a + "+" + b + "=" + (a+b));
		return a + b;
	}

	@Override
	public User addUser(User user) {
		if(!users.contains(user)) {
			user.setId(users.size() + 1);
			users.add(user);
		}
		return user;
	}

	@Override
	public List<User> list() {
		return users;
	}

	@Override
	public List<User> authList(String authInfo) {
		System.out.println("authInfo: " + authInfo);
		return users;
	}

	@Override
	public User login(String username, String password) throws UserException {
		for(User u : users) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
				return u;
			}
		}
		throw new UserException(String.format("User %s is not exists.", username));
	}

	@Override
	public User delete(User user) {
		if(users.contains(user)) {
			users.remove(user);
			return user;
		}
		return null;
	}

}
