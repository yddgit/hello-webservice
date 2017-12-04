package com.my.project.jaxws;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.my.project.jaxws.model.User;

/**
 * SEI(Service Endpoint Interface)
 * @author yang
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT)
//@SOAPBinding(style = Style.RPC)
public interface IMyService {
	/** 两数相加 */
	@WebResult(name = "addResult")
	public Integer add(@WebParam(name = "a") Integer a, @WebParam(name = "b") Integer b);
	/** 两数相减 */
	@WebResult(name = "minusResult")
	public Integer minus(@WebParam(name = "a") Integer a, @WebParam(name = "b") Integer b);
	/** 用户登录 */
	@WebResult(name = "loginUser")
	public User login(@WebParam(name = "username") String username, @WebParam(name = "password") String password);
}
