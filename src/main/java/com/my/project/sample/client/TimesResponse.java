
package com.my.project.sample.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for timesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="timesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="timesResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "timesResponse", propOrder = {
    "timesResult"
})
public class TimesResponse {

    protected int timesResult;

    /**
     * Gets the value of the timesResult property.
     * 
     */
    public int getTimesResult() {
        return timesResult;
    }

    /**
     * Sets the value of the timesResult property.
     * 
     */
    public void setTimesResult(int value) {
        this.timesResult = value;
    }

}
