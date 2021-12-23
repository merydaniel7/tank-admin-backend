package com.tank.tankadminbackend.models.unas.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    @JacksonXmlProperty(localName = "Key")
    private String orderKey;
    @JacksonXmlProperty(localName = "Customer")
    private Customer customer;
    @JacksonXmlProperty(localName = "Payment")
    private Payment payment;
    @JacksonXmlProperty(localName = "Shipping")
    private Shipping shipping;
    @JacksonXmlProperty(localName = "Weight")
    private float weight;
    @JacksonXmlProperty(localName = "Items")
    private Item[] items;

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}

