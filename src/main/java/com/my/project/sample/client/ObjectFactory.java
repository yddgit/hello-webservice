
package com.my.project.sample.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.my.project.sample.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TimesResponse_QNAME = new QName("http://www.example.org/myService/", "timesResponse");
    private final static QName _DivideResponse_QNAME = new QName("http://www.example.org/myService/", "divideResponse");
    private final static QName _License_QNAME = new QName("http://www.example.org/myService/", "license");
    private final static QName _Divide_QNAME = new QName("http://www.example.org/myService/", "divide");
    private final static QName _Times_QNAME = new QName("http://www.example.org/myService/", "times");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.my.project.sample.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Times }
     * 
     */
    public Times createTimes() {
        return new Times();
    }

    /**
     * Create an instance of {@link Divide }
     * 
     */
    public Divide createDivide() {
        return new Divide();
    }

    /**
     * Create an instance of {@link DivideResponse }
     * 
     */
    public DivideResponse createDivideResponse() {
        return new DivideResponse();
    }

    /**
     * Create an instance of {@link TimesResponse }
     * 
     */
    public TimesResponse createTimesResponse() {
        return new TimesResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/myService/", name = "timesResponse")
    public JAXBElement<TimesResponse> createTimesResponse(TimesResponse value) {
        return new JAXBElement<TimesResponse>(_TimesResponse_QNAME, TimesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DivideResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/myService/", name = "divideResponse")
    public JAXBElement<DivideResponse> createDivideResponse(DivideResponse value) {
        return new JAXBElement<DivideResponse>(_DivideResponse_QNAME, DivideResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/myService/", name = "license")
    public JAXBElement<String> createLicense(String value) {
        return new JAXBElement<String>(_License_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Divide }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/myService/", name = "divide")
    public JAXBElement<Divide> createDivide(Divide value) {
        return new JAXBElement<Divide>(_Divide_QNAME, Divide.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Times }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/myService/", name = "times")
    public JAXBElement<Times> createTimes(Times value) {
        return new JAXBElement<Times>(_Times_QNAME, Times.class, null, value);
    }

}
