//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.01 at 11:43:42 PM IST 
//


package interstellar.za.co.discovery.routes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RouteDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RouteDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="planetOrigin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="planetDestination" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="distance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteDetails", propOrder = {
    "planetOrigin",
    "planetDestination",
    "distance",
    "path"
})
public class RouteDetails {

    @XmlElement(required = true)
    protected String planetOrigin;
    @XmlElement(required = true)
    protected String planetDestination;
    protected double distance;
    @XmlElement(required = true)
    protected String path;

    /**
     * Gets the value of the planetOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanetOrigin() {
        return planetOrigin;
    }

    /**
     * Sets the value of the planetOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanetOrigin(String value) {
        this.planetOrigin = value;
    }

    /**
     * Gets the value of the planetDestination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanetDestination() {
        return planetDestination;
    }

    /**
     * Sets the value of the planetDestination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanetDestination(String value) {
        this.planetDestination = value;
    }

    /**
     * Gets the value of the distance property.
     * 
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the value of the distance property.
     * 
     */
    public void setDistance(double value) {
        this.distance = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

}
