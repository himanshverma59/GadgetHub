/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.OrderDetailPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import java.util.List;

/**
 *
 * @author himan
 */
public interface OrderDao {
    public boolean addOrder(OrderPojo order);
    public boolean addTransaction(TransactionPojo transaction);
    public List<OrderPojo> getAllOrders();
    public List<OrderDetailPojo> getAllOrderDetails(String userEmailId);
    public String shipNow(String orderId, String prodId,String userId);
    public String paymentSuccess(String username,double paidAmount);
    public int getSoldQuantity(String prodId);
    public List<OrderPojo> getUnshippedOrders();
    public List<OrderPojo> getShippedOrders();
}
