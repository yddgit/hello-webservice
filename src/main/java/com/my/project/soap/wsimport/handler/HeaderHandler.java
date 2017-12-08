package com.my.project.soap.wsimport.handler;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Node;

import com.my.project.soap.MySoapServer;

public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

	private static final String prefix = "ns";

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		try {
			Boolean out = (Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			if(out) {
				System.out.println("[Client]HeaderHandler out message...");

				SOAPMessage message = context.getMessage();
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();

				SOAPBody body = envelope.getBody();
				Node node = body.getChildNodes().item(0);
				String portName = node.getLocalName();

				// 调用delete接口时添加SOAPHeader/license
				if("delete".equals(portName)) {
					System.out.println("[Client]HeaderHandler add header...");
					SOAPHeader header = envelope.getHeader();
					if(header == null) {
						header = envelope.addHeader();
					}
					QName license = new QName(MySoapServer.namespace, "license", prefix);
					header.addHeaderElement(license).setValue("20201101010101");
				}
			} else {
				System.out.println("[Client]HeaderHandler in message...");
			}
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.err.println("[Client]HeaderHandler handle fault...");
		return false;
	}

	@Override
	public void close(MessageContext context) {
		
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

}
