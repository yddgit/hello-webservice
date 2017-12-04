package com.my.project.sample;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;

import org.junit.Test;

public class TestWSDL {

	/**
	 * 请求时发送服务端需要的头信息
	 */
	@Test
	public void testTimes() {
		try {
			String namespace = "http://www.example.org/myService/";
			QName serviceName = new QName(namespace, "MyServiceImpl");
			URL url = new URL("http://localhost:9000/myService?wsdl");
			Service service = Service.create(url, serviceName);

			QName portName = new QName(namespace, "MyServiceImplPort");
			Dispatch<SOAPMessage> dispatch =
					service.createDispatch(portName, SOAPMessage.class, Mode.MESSAGE);

			SOAPMessage message = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();

			SOAPBody body = envelope.getBody();
			QName times = new QName(namespace, "times", "ns");
			SOAPElement operation = body.addBodyElement(times);
			operation.addChildElement("num1").setValue("12");
			operation.addChildElement("num2").setValue("24");

			SOAPHeader header = envelope.getHeader();
			if(header == null) {
				header = envelope.addHeader();
			}
			QName license = new QName(namespace, "license");
			header.addHeaderElement(license).setValue("20201212110101");

			SOAPMessage response = dispatch.invoke(message);
			response.writeTo(System.out);
			System.out.println();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用wsimport生成的客户端代码调用divide接口
	 */
	@Test
	public void testDivide() {
		com.my.project.sample.client.MyServiceImpl service = new com.my.project.sample.client.MyServiceImpl();
		com.my.project.sample.client.IMyService port = service.getMyServiceImplPort();
		System.out.println(port.divide(34, 17));
	}
}
