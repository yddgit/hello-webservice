
package com.my.project.sample.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "MyServiceImpl", targetNamespace = "http://www.example.org/myService/", wsdlLocation = "http://localhost:9000/myService?wsdl")
public class MyServiceImpl
    extends Service
{

    private final static URL MYSERVICEIMPL_WSDL_LOCATION;
    private final static WebServiceException MYSERVICEIMPL_EXCEPTION;
    private final static QName MYSERVICEIMPL_QNAME = new QName("http://www.example.org/myService/", "MyServiceImpl");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:9000/myService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MYSERVICEIMPL_WSDL_LOCATION = url;
        MYSERVICEIMPL_EXCEPTION = e;
    }

    public MyServiceImpl() {
        super(__getWsdlLocation(), MYSERVICEIMPL_QNAME);
    }

    public MyServiceImpl(WebServiceFeature... features) {
        super(__getWsdlLocation(), MYSERVICEIMPL_QNAME, features);
    }

    public MyServiceImpl(URL wsdlLocation) {
        super(wsdlLocation, MYSERVICEIMPL_QNAME);
    }

    public MyServiceImpl(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MYSERVICEIMPL_QNAME, features);
    }

    public MyServiceImpl(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MyServiceImpl(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IMyService
     */
    @WebEndpoint(name = "MyServiceImplPort")
    public IMyService getMyServiceImplPort() {
        return super.getPort(new QName("http://www.example.org/myService/", "MyServiceImplPort"), IMyService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IMyService
     */
    @WebEndpoint(name = "MyServiceImplPort")
    public IMyService getMyServiceImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.example.org/myService/", "MyServiceImplPort"), IMyService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MYSERVICEIMPL_EXCEPTION!= null) {
            throw MYSERVICEIMPL_EXCEPTION;
        }
        return MYSERVICEIMPL_WSDL_LOCATION;
    }

}
