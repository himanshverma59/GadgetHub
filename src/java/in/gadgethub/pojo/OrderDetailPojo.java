/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

import java.io.InputStream;
import java.util.Date;

/**
 *
 * @author himan
 */
public class OrderDetailPojo {
    private String orderId;
    private String prodId;
    private String prodName;
    private int qty;
    private double amount;
    private int shipped;
    private Date time;
    private InputStream prodImage;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getShipped() {
        return shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public InputStream getProdImage() {
        return prodImage;
    }

    public void setProdImage(InputStream prodImage) {
        this.prodImage = prodImage;
    }

    public OrderDetailPojo(String orderId, String prodId, String prodName, int qty, double amount, int shipped, Date time, InputStream prodImage) {
        this.orderId = orderId;
        this.prodId = prodId;
        this.prodName = prodName;
        this.qty = qty;
        this.amount = amount;
        this.shipped = shipped;
        this.time = time;
        this.prodImage = prodImage;
    }

    public OrderDetailPojo() {
    }

    
    
}
