package com.my.project.soap;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.my.project.soap.model.User;

/**
 * SEI(Service Endpoint Interface)
 * @author yang
 */
// 接口的targetNamespace需要与实现类的targetNamespace相同
@WebService(targetNamespace = "http://project.my.com/webservice")
public interface IMyService {

	/** 两数相加 */
	@WebResult(name = "addResult")
	public Integer add(@WebParam(name = "a") Integer a, @WebParam(name = "b") Integer b);

	/**
	 * 请求参数是复杂实体
	 * @param user user
	 * @return user
	 */
	@WebResult(name = "user")
	public User addUser(@WebParam(name = "user") User user);

	/**
	 * 无请求参数
	 * @return user list
	 */
	@WebResult(name = "user")
	public List<User> list();

	/**
	 * 显式的声明Header信息(导致客户端调用非常不方便)
	 * @param authInfo 显式声明Header信息：@WebParam(name="xxx", header=true)
	 * @return user list
	 */
	@WebResult(name = "user")
	public List<User> authList(@WebParam(name = "authInfo", header = true) String authInfo);

	/**
	 * 此接口可能抛出异常
	 * @param username username
	 * @param password password
	 * @return user
	 * @throws UserException 声明的异常类型会被抛到客户端，如果有未声明的异常抛出则不会被抛到客户端
	 */
	@WebResult(name = "user")
	public User login(
		@WebParam(name = "username") String username,
		@WebParam(name = "password") String password) throws UserException;

	/**
	 * 通过Handler校验Header
	 * @param user user
	 * @return user
	 */
	@WebResult(name = "user")
	public User delete(@WebParam(name = "user") User user);

}
