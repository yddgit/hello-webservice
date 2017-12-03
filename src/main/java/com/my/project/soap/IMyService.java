package com.my.project.soap;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * SEI(Service Endpoint Interface)
 * @author yang
 */
@WebService
public interface IMyService {
	/** 两数相加 */
	@WebResult(name = "addResult")
	public Integer add(@WebParam(name = "a") Integer a, @WebParam(name = "b") Integer b);
}
