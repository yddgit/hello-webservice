package com.my.project.soap;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.my.project.soap.model.User;
import com.my.project.soap.wsimport.MyServiceImplService;
import com.my.project.soap.wsimport.UserException_Exception;

public class TestSoap {

	/** JAX-WS的接口和实现类的targetNamespace */
	private static final String wsdlLocation = "http://localhost:9000/myService?wsdl";
	private static final String prefix = "ns";

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
	@Test
	public void createMessage_add() {
		try {
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage();
			SOAPPart part = message.getSOAPPart();
			SOAPEnvelope envelope = part.getEnvelope();
			// 获取SOAPBody
			SOAPBody body = envelope.getBody();

			// 通过QName创建结点add, 并指定命名空间和前缀
			// XML: <ns:add xmlns:ns="http://project.my.com/webservice"/>
			QName qname = new QName(MySoapServer.namespace, "add", prefix);

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
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送SOAP消息(Message)
	 * @param message SOAP消息
	 */
	@Test
	public void sendMessage_add() {
		try {
			// 1.创建服务Service
			URL url = new URL(wsdlLocation);
			QName serviceName = new QName(MySoapServer.namespace, "MyServiceImplService");
			Service service = Service.create(url, serviceName);

			// 2.创建Dispatch(以SOAPMessage的方式发送)
			QName portName = new QName(MySoapServer.namespace, "MyServiceImplPort");
			Dispatch<SOAPMessage> dispatch = service
					.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

			// 3.发送消息并接收响应
			SOAPMessage message = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			//body
			SOAPBody body = envelope.getBody();
			SOAPElement add = body.addBodyElement(new QName(MySoapServer.namespace, "add", prefix));
			add.addChildElement("a").setValue("12");
			add.addChildElement("b").setValue("33");
			//response
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

	/**
	 * 发送SOAP消息(Payload)
	 */
	@Test
	public void sendPayload_addUser() {
		try {
			// 1.创建服务Service
			URL url = new URL(wsdlLocation);
			QName serviceName = new QName(MySoapServer.namespace, "MyServiceImplService");
			Service service = Service.create(url, serviceName);

			// 2.创建Dispatch(以Payload的方式发送)
			QName portName = new QName(MySoapServer.namespace, "MyServiceImplPort");
			Dispatch<Source> dispatch = service
					.createDispatch(portName, Source.class, Service.Mode.PAYLOAD);

			// 3.创建消息内容
			User u = new User();
			u.setId(2);
			u.setUsername("bob");
			u.setNickname("Bob");
			u.setPassword("111111");
			JAXBContext ctx = JAXBContext.newInstance(User.class);
			Marshaller marshaller = ctx.createMarshaller();
			// 设置Marshaller转换时不添加XML文档头<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			StringWriter writer = new StringWriter();
			marshaller.marshal(u, writer);

			// 4.封装SOAPPart
			String payload = String.format("<ns:addUser xmlns:ns=\"%s\">%s</ns:addUser>",
					MySoapServer.namespace, writer.toString());
			StreamSource message = new StreamSource(new StringReader(payload));

			// 5.发送消息，并将接收到的数据转换为DOM
			Source response = dispatch.invoke(message);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMResult result = new DOMResult();
			transformer.transform(response, result);

			// 6.从返回的DOM中解析返回值
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xpath.evaluate("//user", result.getNode(), XPathConstants.NODESET);
			Node node = nodeList.item(0);
			User user = (User) ctx.createUnmarshaller().unmarshal(node);
			System.out.println(user);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送SOAP消息(无请求参数)
	 */
	@Test
	public void sendNoRequest_list() {
		try {
			// 1.创建服务Service
			URL url = new URL(wsdlLocation);
			QName serviceName = new QName(MySoapServer.namespace, "MyServiceImplService");
			Service service = Service.create(url, serviceName);

			// 2.创建Dispatch(以SOAPMessage的方式发送)
			QName portName = new QName(MySoapServer.namespace, "MyServiceImplPort");
			Dispatch<SOAPMessage> dispatch = service
					.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

			// 3.发送消息并接收响应
			SOAPMessage message = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			//body
			SOAPBody body = envelope.getBody();
			body.addBodyElement(new QName(MySoapServer.namespace, "list", prefix));
			//print message
			message.writeTo(System.out);
			System.out.println();
			//send message
			SOAPMessage response = dispatch.invoke(message);
			response.writeTo(System.out);
			System.out.println();

			// 4.将响应消息Body中的内容转换为DOM结点，解析返回值
			Document doc = response.getSOAPBody().extractContentAsDocument();
			NodeList nodeList = doc.getElementsByTagName("user");
			JAXBContext ctx = JAXBContext.newInstance(User.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			for(int i=0; i<nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				User u = (User) unmarshaller.unmarshal(n);
				System.out.println(u);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送SOAP消息(带Header的请求)
	 */
	@Test
	public void sendHeader_authList() {
		try {
			// 1.创建服务Service
			URL url = new URL(wsdlLocation);
			QName serviceName = new QName(MySoapServer.namespace, "MyServiceImplService");
			Service service = Service.create(url, serviceName);

			// 2.创建Dispatch(以SOAPMessage的方式发送)
			QName portName = new QName(MySoapServer.namespace, "MyServiceImplPort");
			Dispatch<SOAPMessage> dispatch = service
					.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

			// 3.发送消息并接收响应
			SOAPMessage message = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			//header
			SOAPHeader header = envelope.getHeader();
			if(header == null) {
				header = envelope.addHeader();
			}
			QName authInfo = new QName(MySoapServer.namespace, "authInfo", prefix);
			header.addHeaderElement(authInfo).setValue("authorized");
			//body
			SOAPBody body = envelope.getBody();
			body.addBodyElement(new QName(MySoapServer.namespace, "authList", prefix));
			//print message
			message.writeTo(System.out);
			System.out.println();
			//send message
			SOAPMessage response = dispatch.invoke(message);
			response.writeTo(System.out);
			System.out.println();

			// 4.将响应消息Body中的内容转换为DOM结点，解析返回值
			Document doc = response.getSOAPBody().extractContentAsDocument();
			NodeList nodeList = doc.getElementsByTagName("user");
			JAXBContext ctx = JAXBContext.newInstance(User.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			for(int i=0; i<nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				User u = (User) unmarshaller.unmarshal(n);
				System.out.println(u);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用wsimport工具生成的客户端代码调用authList接口(说明显式声明Header的问题)
	 */
	@Test
	public void sendHeaderUseGeneratedCode_authList() {
		MyServiceImplService service = new MyServiceImplService();
		com.my.project.soap.wsimport.IMyService port = service.getMyServiceImplPort();
		List<com.my.project.soap.wsimport.User> users = port.authList(null, "authorized(use generated code)").getUser();
		for(com.my.project.soap.wsimport.User u : users) {
			System.out.println(u);
		}
	}

	/**
	 * 处理SOAP异常
	 */
	@Test
	public void handleFault_login() {
		try {
			// 1.创建服务Service
			URL url = new URL(wsdlLocation);
			QName serviceName = new QName(MySoapServer.namespace, "MyServiceImplService");
			Service service = Service.create(url, serviceName);

			// 2.创建Dispatch(以SOAPMessage的方式发送)
			QName portName = new QName(MySoapServer.namespace, "MyServiceImplPort");
			Dispatch<SOAPMessage> dispatch = service
					.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

			// 3.发送消息并接收响应
			SOAPMessage message = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			//body
			SOAPBody body = envelope.getBody();
			SOAPElement login = body.addBodyElement(new QName(MySoapServer.namespace, "login", prefix));
			login.addChildElement("username").setValue("xxx");
			login.addChildElement("password").setValue("yyy");
			//print message
			message.writeTo(System.out);
			System.out.println();
			//send message
			SOAPMessage response = dispatch.invoke(message); // throw exception
			response.writeTo(System.out);
			System.out.println();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SOAPFaultException e) {
			// 输出SOAPFault信息
			SOAPFault fault = e.getFault();
			System.err.println(fault.getFaultCode() + ": " + fault.getFaultString());
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用wsimport工具生成的客户端代码调用可能会抛出异常的login接口
	 */
	@Test
	public void handleFaultUseGeneratedCode_login() {
		MyServiceImplService service = new MyServiceImplService();
		com.my.project.soap.wsimport.IMyService port = service.getMyServiceImplPort();
		try {
			com.my.project.soap.wsimport.User user = port.login("xxx", "yyy");
			System.out.println(user);
		} catch (UserException_Exception e) {
			com.my.project.soap.wsimport.UserException ex = e.getFaultInfo();
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * 服务端Handler需要发送Header信息, 但客户端未发送时抛出SOAPFaultException
	 */
	@Test
	public void handleHeader_delete() {
		try {
			// 1.创建服务Service
			URL url = new URL(wsdlLocation);
			QName serviceName = new QName(MySoapServer.namespace, "MyServiceImplService");
			Service service = Service.create(url, serviceName);

			// 2.创建Dispatch(以SOAPMessage的方式发送)
			QName portName = new QName(MySoapServer.namespace, "MyServiceImplPort");
			Dispatch<SOAPMessage> dispatch = service
					.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

			// 3.发送消息并接收响应
			SOAPMessage message = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			//body
			SOAPBody body = envelope.getBody();
			SOAPElement login = body.addBodyElement(new QName(MySoapServer.namespace, "delete", prefix));
			SOAPElement user = login.addChildElement("user");
			user.addChildElement("id").setValue("2");
			user.addChildElement("username").setValue("bob");
			user.addChildElement("nickname").setValue("Bob");
			user.addChildElement("password").setValue("111111");
			//print message
			message.writeTo(System.out);
			System.out.println();
			//send message
			SOAPMessage response = dispatch.invoke(message); // throw exception
			response.writeTo(System.out);
			System.out.println();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SOAPFaultException e) {
			// 输出SOAPFault信息
			SOAPFault fault = e.getFault();
			System.err.println(fault.getFaultCode() + ": " + fault.getFaultString());
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 客户端通过Handler添加Header信息发送给服务端。
	 * 可以使用TCPMon截获SOAPMessage，观察由Handler添加的Header信息
	 */
	@Test
	public void handleHeaderUseGeneratedCode_delete() {
		try {
			MyServiceImplService service = new MyServiceImplService();
			com.my.project.soap.wsimport.IMyService port = service.getMyServiceImplPort();
			com.my.project.soap.wsimport.User u = new com.my.project.soap.wsimport.User();
			u.setId(2);
			u.setUsername("bob");
			u.setNickname("Bob");
			u.setPassword("111111");
			Holder<com.my.project.soap.wsimport.User> user = 
				new Holder<com.my.project.soap.wsimport.User>(u);
			port.addUser(user);
			port.delete(user);
			System.out.println(user.value);
		} catch (SOAPFaultException e) {
			// 输出SOAPFault信息
			SOAPFault fault = e.getFault();
			System.err.println(fault.getFaultCode() + ": " + fault.getFaultString());
		}
	}

}
