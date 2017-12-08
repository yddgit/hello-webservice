package com.my.project.jaxws;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.my.project.jaxws.model.Classroom;
import com.my.project.jaxws.model.Student;

/**
 * 使用Jaxb进行XML和对象的互相转换
 * @author yang
 */
public class TestJaxb {

	/**
	 * 将Java对象转换为XML
	 */
	@Test
	public void marshaller() {
		try {
			JAXBContext ctx = JAXBContext.newInstance(Student.class);
			Marshaller marshaller = ctx.createMarshaller();
			// 格式化XML输出
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 定义要转换的对象
			Student student = new Student();
			student.setId(1);
			student.setName("张三");
			student.setAge(20);
			Classroom classroom = new Classroom();
			classroom.setId(1);
			classroom.setGrade(2018);
			classroom.setName("CS1");
			student.setClassroom(classroom);
			// 将对象转换为XML
			marshaller.marshal(student, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将XML转换为Java对象
	 */
	@Test
	public void unmarshaller() {
		try {
			JAXBContext ctx = JAXBContext.newInstance(Student.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			// XML
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<student>"
					+ "  <id>2</id>"
					+ "  <name>李四</name>"
					+ "  <age>21</age>"
					+ "  <classroom>"
					+ "    <id>2</id>"
					+ "    <name>CS2</name>"
					+ "    <grade>2018</grade>"
					+ "  </classroom>"
					+ "</student>";
			// 将XML转换为对象
			Student student = (Student)unmarshaller.unmarshal(new StringReader(xml));
			System.out.println(student);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
