package com.my.project.jaxws;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.EventFilter;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestStax {

	/** XMLStreamConstants中的常量值到常量名的映射 */
	private static Map<Integer, String> CONST_TO_NAME;

	/**
	 * 基于光标模型的操作
	 */
	@Test
	public void stream() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			while(reader.hasNext()) {
				int type = reader.next();
				if(type == XMLStreamConstants.START_ELEMENT) {
					if("title".equals(reader.getName().toString())) {
						String typeName = CONST_TO_NAME.get(type) + ": ";
						String lang = reader.getAttributeValue(0).toString();
						System.out.print(typeName + reader.getElementText() + "(" + lang + ") ");
					} else if("author".equals(reader.getName().toString())) {
						System.out.print(reader.getElementText() + " ");
					} else if("year".equals(reader.getName().toString())) {
						System.out.print(reader.getElementText() + " ");
					} else if("price".equals(reader.getName().toString())) {
						System.out.println(reader.getElementText());
					}
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 基于迭代模型的操作
	 */
	@Test
	public void event() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
			XMLEventReader reader = factory.createXMLEventReader(is);
			int eventNum = 0; // 迭代次数
			while(reader.hasNext()) {
				XMLEvent event = reader.nextEvent(); // 通过XMLEvent来获取节点类型
				String lang = null;
				if(event.isStartElement()) {
					// 通过event.asXXX()获取节点
					if("title".equals(event.asStartElement().getName().toString())) {
						lang = event.asStartElement().getAttributeByName(new QName("lang")).getValue().toString();
						System.out.print(reader.getElementText() + "(" + lang + ") ");
					} else if("author".equals(event.asStartElement().getName().toString())) {
						System.out.print(reader.getElementText() + " ");
					} else if("year".equals(event.asStartElement().getName().toString())) {
						System.out.print(reader.getElementText() + " ");
					} else if("price".equals(event.asStartElement().getName().toString())) {
						System.out.println(reader.getElementText());
					}
				}
				eventNum ++;
			}
			System.out.println("迭代次数: " + eventNum);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 基于过滤器的操作
	 */
	@Test
	public void filter() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
			XMLEventReader reader = factory.createXMLEventReader(is);
			// 使用过滤器排除不关心的节点，可以提高效率，减少迭代次数
			XMLEventReader filterReader = factory.createFilteredReader(reader, new EventFilter(){
				@Override
				public boolean accept(XMLEvent event) {
					if(event.isStartElement()) {
						String name = event.asStartElement().getName().toString();
						if("title".equals(name) || "author".equals(name) || "year".equals(name) || "price".equals(name)) {
							return true;
						} else {
							return false;
						}
					}
					return false;
				}
			});
			int eventNum = 0; // 迭代次数
			while(filterReader.hasNext()) {
				XMLEvent event = filterReader.nextEvent();
				String lang = null;
				if(event.isStartElement()) {
					if("title".equals(event.asStartElement().getName().toString())) {
						lang = event.asStartElement().getAttributeByName(new QName("lang")).getValue().toString();
						System.out.print(filterReader.getElementText() + "(" + lang + ") ");
					} else if("author".equals(event.asStartElement().getName().toString())) {
						System.out.print(filterReader.getElementText() + " ");
					} else if("year".equals(event.asStartElement().getName().toString())) {
						System.out.print(filterReader.getElementText() + " ");
					} else if("price".equals(event.asStartElement().getName().toString())) {
						System.out.println(filterReader.getElementText());
					}
				}
				eventNum ++;
			}
			System.out.println("迭代次数: " + eventNum);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 基于XPath的操作（查找category="web"的book节点，输出title和price信息）
	 */
	@Test
	public void xpath() {
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);

			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList list = (NodeList)xpath.evaluate("//book[@category='web']", doc, XPathConstants.NODESET);
			for(int i=0; i<list.getLength(); i++) {
				Element e = (Element)list.item(i);
				Node titleNode = e.getElementsByTagName("title").item(0);
				String lang = titleNode.getAttributes().getNamedItem("lang").getNodeValue();
				String title = titleNode.getTextContent();
				String price = e.getElementsByTagName("price").item(0).getTextContent();
				System.out.println(title + "(" + lang + ") " + price);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 使用XMLStreamWriter创建XML
	 */
	@Test
	public void writer() {
		try {
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			// 设置输出的XML内容中添加命名空间
			factory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
			XMLStreamWriter writer = factory.createXMLStreamWriter(System.out);
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeEndDocument();
			// 指定节点的命名空间
			String namespace = "http://www.example.org/facet";
			writer.writeStartElement("ns", "person", namespace);
			writer.writeAttribute("gender", "female");
			writer.writeStartElement(namespace, "name");
			writer.writeCharacters("Lily");
			writer.writeEndElement();
			writer.writeStartElement(namespace, "age");
			writer.writeCharacters("8");
			writer.writeEndElement();
			writer.writeStartElement(namespace, "email");
			writer.writeCharacters("lily@example.com");
			writer.writeEndElement();
			writer.writeEndElement();
			writer.writeCharacters("\n");
			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用Transformer更新节点信息
	 */
	@Test
	public void update() {
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);

			// 使用XPath查找并修改price节点的值
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList list = (NodeList)xpath.evaluate("//book[title='Harry Potter']", doc, XPathConstants.NODESET);
			Element book = (Element)list.item(0);
			Element price = (Element)(book.getElementsByTagName("price").item(0));
			price.setTextContent("39.99");

			// 将修改后的值输出到控制台
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			Result result = new StreamResult(System.out);
			transformer.transform(new DOMSource(doc), result);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将XMLStreamConstants中的常量添加到Map中，方便查看输出
	 */
	@Before
	public void before() {
		try {
			Class<?> clazz = Class.forName("javax.xml.stream.XMLStreamConstants");
			Field[] fields = clazz.getDeclaredFields();
			CONST_TO_NAME = new HashMap<Integer, String>();
			for(Field field : fields) {
				CONST_TO_NAME.put(field.getInt(clazz), field.getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
