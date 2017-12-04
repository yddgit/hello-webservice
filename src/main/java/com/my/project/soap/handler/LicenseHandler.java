package com.my.project.soap.handler;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.w3c.dom.Node;

public class LicenseHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		try {
			Boolean out = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			if(out) {
				System.out.println("[Server]LicenseHandler out message...");
			} else {
				System.out.println("[Server]LicenseHandler in message...");

				SOAPMessage message = context.getMessage();
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();

				SOAPBody body = envelope.getBody();
				Node node = body.getChildNodes().item(0);
				String portName = node.getLocalName();

				// 验证delete接口必须传输SOAPHeader/license, 否则抛出SOAPFaultException
				if("delete".equals(portName)) {
					SOAPHeader header = envelope.getHeader();
					if(header == null) {
						//如果header为空, 则添加SOAPFault信息
						SOAPFault fault = body.addFault();
						fault.setFaultString("[Server]Header can not be null.");
						throw new SOAPFaultException(fault);
					}
					@SuppressWarnings("unchecked")
					Iterator<SOAPHeaderElement> iterator = header.examineAllHeaderElements();
					if(!iterator.hasNext()) {
						//如果header中没有任何数据, 则添加SOAPFault信息
						SOAPFault fault = body.addFault();
						fault.setFaultString("[Server]Header data can not be null.");
						throw new SOAPFaultException(fault);
					}
					while(iterator.hasNext()) {
						SOAPHeaderElement h = iterator.next();
						System.out.println(String.format("[Server]Client %s: %s", h.getLocalName(), h.getValue()));
					}
				}
			}
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.err.println("[Server]LicenseHandler handle fault...");
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
