package com.my.project.soap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.w3c.dom.Document;

public class TestSoap {
	private static final String namespace = "http://soap.project.my.com/";
	private static final String wsdlLocation = "http://localhost:9000/myService?wsdl";
	private static final String prefix = "ns";

	public static void main(String[] args) {
		SOAPMessage message = soapMessage();
		sendMessage(message);
	}

	/**
	 * 构造SOAP消息，SOAP消息结构:
	 * <pre>
	 *   SOAPMessage
	 *   +--SOAPPart
	 *   |  +--SOAPEnvelope
	 *   |     +--SOAPHeader(optional): Headers(if any)
	 *   |     +--SOAPBody: XML Content or SOAPFault
	 *   +--AttachmentPart
	 *   |  +--MIME Headers
	 *   |  +--Content: XML or non-XML
	 *   +--AttachmentPart
	 *   |  +--MIME Headers
	 *   |  +--Content: XML or non-XML
	 *   \--More AttachmentPart ...
	 * </pre>
	 * @return 
	 */
	static SOAPMessage soapMessage() {
		try {
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage();
			SOAPPart part = message.getSOAPPart();
			SOAPEnvelope envelope = part.getEnvelope();
			// 获取SOAPBody
			SOAPBody body = envelope.getBody();

			// 通过QName创建结点add, 并指定命名空间和前缀
			// XML: <ns:add xmlns:ns="http://project.my.com/webservice"/>
			QName qname = new QName(namespace, "add", prefix);

			// 将add结点添加到SOAPBody中
			SOAPElement add = body.addBodyElement(qname);

			// 1.向add结点中添加文本内容
			// XML: <ns:add xmlns:ns="http://project.my.com/webservice">123</ns:add>
			//add.setValue("123");

			// 2.向add节点中添加XML结点
			// XML: <ns:add xmlns:ns="http://project.my.com/webservice"><a>12</a><b>33</b></ns:add>
			add.addChildElement("a").setValue("12");
			add.addChildElement("b").setValue("33");

			// 将SOAP消息输出到控制台
			message.writeTo(System.out);
			System.out.println();
			return message;
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送SOAP消息
	 * @param message SOAP消息
	 */
	static void sendMessage(SOAPMessage message) {
		try {
			// 1.创建服务Service
			URL url = new URL(wsdlLocation);
			QName serviceName = new QName(namespace, "MyServiceImplService");
			Service service = Service.create(url, serviceName);

			// 2.创建Dispatch
			QName portName = new QName(namespace, "MyServiceImplPort");
			Dispatch<SOAPMessage> dispatch = service
					.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

			// 3.发送消息并接收响应
			SOAPMessage response = dispatch.invoke(message);
			response.writeTo(System.out);
			System.out.println();

			// 4.将响应消息Body中的内容转换为DOM结点，解析返回值
			Document doc = response.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
			String addResult = doc.getElementsByTagName("addResult").item(0).getTextContent();
			System.out.println("addResult: " + addResult);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
