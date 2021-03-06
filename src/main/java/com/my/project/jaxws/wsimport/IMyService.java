
package com.my.project.jaxws.wsimport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IMyService", targetNamespace = "http://jaxws.project.my.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IMyService {


    /**
     * 
     * @param b
     * @param a
     * @return
     *     returns java.lang.Integer
     */
    @WebMethod
    @WebResult(name = "addResult", targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://jaxws.project.my.com/", className = "com.my.project.jaxws.wsimport.Add")
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://jaxws.project.my.com/", className = "com.my.project.jaxws.wsimport.AddResponse")
    @Action(input = "http://jaxws.project.my.com/IMyService/addRequest", output = "http://jaxws.project.my.com/IMyService/addResponse")
    public Integer add(
        @WebParam(name = "a", targetNamespace = "")
        Integer a,
        @WebParam(name = "b", targetNamespace = "")
        Integer b);

    /**
     * 
     * @param b
     * @param a
     * @return
     *     returns java.lang.Integer
     */
    @WebMethod
    @WebResult(name = "minusResult", targetNamespace = "")
    @RequestWrapper(localName = "minus", targetNamespace = "http://jaxws.project.my.com/", className = "com.my.project.jaxws.wsimport.Minus")
    @ResponseWrapper(localName = "minusResponse", targetNamespace = "http://jaxws.project.my.com/", className = "com.my.project.jaxws.wsimport.MinusResponse")
    @Action(input = "http://jaxws.project.my.com/IMyService/minusRequest", output = "http://jaxws.project.my.com/IMyService/minusResponse")
    public Integer minus(
        @WebParam(name = "a", targetNamespace = "")
        Integer a,
        @WebParam(name = "b", targetNamespace = "")
        Integer b);

    /**
     * 
     * @param username
     * @param password
     * @return
     *     returns com.my.project.jaxws.wsimport.User
     */
    @WebMethod
    @WebResult(name = "loginUser", targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://jaxws.project.my.com/", className = "com.my.project.jaxws.wsimport.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://jaxws.project.my.com/", className = "com.my.project.jaxws.wsimport.LoginResponse")
    @Action(input = "http://jaxws.project.my.com/IMyService/loginRequest", output = "http://jaxws.project.my.com/IMyService/loginResponse")
    public User login(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password);

}
