/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

/**
 *
 * @author himan
 */
public class OrderPojo {
    private String orderId;
    private String productId;
    private int quantity;
    private double amount;
    private int shipped;

    public OrderPojo() {
    }

    public OrderPojo(String orderId, String productId, int quantity, double amount, int shipped) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.shipped = shipped;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "OrderPojo{" + "orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + ", amount=" + amount + ", shipped=" + shipped + '}';
    }
    
    
}
