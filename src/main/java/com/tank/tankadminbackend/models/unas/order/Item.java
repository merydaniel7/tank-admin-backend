package com.tank.tankadminbackend.models.unas.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    @JacksonXmlProperty(localName = "Sku")
    private String id;
    @JacksonXmlProperty(localName = "Unit")
    private String unit;
    @JacksonXmlProperty(localName = "Quantity")
    private float quantity;
    @JacksonXmlProperty(localName = "PriceNet")
    private float netPrice;
    @JacksonXmlProperty(localName = "Vat")
    private String vat;
    @JacksonXmlProperty(localName = "ProductParams")
    private ProductParam[] productParams;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(float netPrice) {
        this.netPrice = netPrice;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public ProductParam[] getProductParams() {
        return productParams;
    }

    public void setProductParams(ProductParam[] productParams) {
        this.productParams = productParams;
    }
}

