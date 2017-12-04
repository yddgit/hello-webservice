package com.my.project.jaxws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Test;

import com.my.project.jaxws.wsimport.MyServiceImplService;

public class TestClient {

	/**
	 * 直接使用本地的IMyService接口调用WebService接口
	 */
	@Test
	public void local() {
		try {
			URL wsdlLocation = new URL("http://localhost:9000/myService?wsdl");
			QName serviceName = new QName("http://jaxws.project.my.com/", "MyServiceImplService");
			Service service = Service.create(wsdlLocation, serviceName);
			IMyService myService = service.getPort(IMyService.class);
			System.out.println("local:" + myService.add(12, 33));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * 使用JDK的wsimport命令生成的源代码调用WebService接口
	 */
	@Test
	public void wsimport() {
		com.my.project.jaxws.wsimport.IMyService myService = new MyServiceImplService().getMyServiceImplPort();
		System.out.println("wsimport:" + myService.minus(29, 12));
	}

	/**
	 * 使用TCPMon截获WebService报文
	 */
	@Test
	public void tcpmon() {
		try {
			// wsdlLocation中的端口要修改为TCPMon的监听端口
			URL wsdlLocation = new URL("http://localhost:9001/myService?wsdl");
			QName serviceName = new QName("http://jaxws.project.my.com/", "MyServiceImplService");
			// 使用JDK的wsimport命令生成的源代码调用WebService接口
			com.my.project.jaxws.wsimport.IMyService myService = new MyServiceImplService(wsdlLocation, serviceName).getMyServiceImplPort();
			System.out.println("tcpmon:" + myService.minus(29, 12));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接口返回参数为复杂对象
	 */
	@Test
	public void returnComplexType() {
		com.my.project.jaxws.wsimport.IMyService myService = new MyServiceImplService().getMyServiceImplPort();
		com.my.project.jaxws.wsimport.User u = myService.login("admin", "123456");
		System.out.println("User:id=" + u.getId() + ",username=" + u.getUsername() + ",password=" + u.getPassword());
	}

}
