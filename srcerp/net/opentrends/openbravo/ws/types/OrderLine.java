/**
 * OrderLine.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package net.opentrends.openbravo.ws.types;

public class OrderLine  implements java.io.Serializable {
    private int orderLineId;

    private double price;

    private int productId;

    private int taxId;

    private double units;

    public OrderLine() {
    }

    public OrderLine(
           int orderLineId,
           double price,
           int productId,
           int taxId,
           double units) {
           this.orderLineId = orderLineId;
           this.price = price;
           this.productId = productId;
           this.taxId = taxId;
           this.units = units;
    }


    /**
     * Gets the orderLineId value for this OrderLine.
     * 
     * @return orderLineId
     */
    public int getOrderLineId() {
        return orderLineId;
    }


    /**
     * Sets the orderLineId value for this OrderLine.
     * 
     * @param orderLineId
     */
    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
    }


    /**
     * Gets the price value for this OrderLine.
     * 
     * @return price
     */
    public double getPrice() {
        return price;
    }


    /**
     * Sets the price value for this OrderLine.
     * 
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }


    /**
     * Gets the productId value for this OrderLine.
     * 
     * @return productId
     */
    public int getProductId() {
        return productId;
    }


    /**
     * Sets the productId value for this OrderLine.
     * 
     * @param productId
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }


    /**
     * Gets the taxId value for this OrderLine.
     * 
     * @return taxId
     */
    public int getTaxId() {
        return taxId;
    }


    /**
     * Sets the taxId value for this OrderLine.
     * 
     * @param taxId
     */
    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }


    /**
     * Gets the units value for this OrderLine.
     * 
     * @return units
     */
    public double getUnits() {
        return units;
    }


    /**
     * Sets the units value for this OrderLine.
     * 
     * @param units
     */
    public void setUnits(double units) {
        this.units = units;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderLine)) return false;
        OrderLine other = (OrderLine) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.orderLineId == other.getOrderLineId() &&
            this.price == other.getPrice() &&
            this.productId == other.getProductId() &&
            this.taxId == other.getTaxId() &&
            this.units == other.getUnits();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getOrderLineId();
        _hashCode += new Double(getPrice()).hashCode();
        _hashCode += getProductId();
        _hashCode += getTaxId();
        _hashCode += new Double(getUnits()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderLine.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://192.168.1.130:8880/openbravo/services/ExternalSales", "OrderLine"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderLineId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderLineId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("", "price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taxId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("units");
        elemField.setXmlName(new javax.xml.namespace.QName("", "units"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}