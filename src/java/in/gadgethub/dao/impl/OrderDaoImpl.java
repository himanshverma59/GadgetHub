
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.OrderDao;
import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.OrderDetailPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import in.gadgethub.utillity.DBUtil;
import in.gadgethub.utillity.IDUtil;
import in.gadgethub.utillity.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author himan
 */
public class OrderDaoImpl implements OrderDao{

    @Override
    public boolean addOrder(OrderPojo order) {
        boolean status=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("Insert into orders values(?,?,?,?,?)");
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getProductId());
            ps.setInt(3, order.getQuantity());
            ps.setDouble(4, order.getAmount());
            ps.setInt(5, 0);
            
            int count=ps.executeUpdate();
            status=count>0;
            
        }catch(SQLException ex){
            System.out.println("Error in AddOrder"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
         return status;
    }

    @Override
    public boolean addTransaction(TransactionPojo transaction) {
         boolean status=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("Insert into transactions values(?,?,?,?)");
            ps.setString(1, transaction.getTransactionId());
            ps.setString(2, transaction.getUserEmail());
            java.util.Date d1=transaction.getTransTime();
            java.sql.Date d2=new java.sql.Date(d1.getTime());
            ps.setDate(3,d2 );
            ps.setDouble(4, transaction.getAmount());
           
            
            int count=ps.executeUpdate();
            status=count>0;
            
        }catch(SQLException ex){
            System.out.println("Error in AddTransaction"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
         return status;
    }

    @Override
    public List<OrderPojo> getAllOrders() {
        List<OrderPojo> orderList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        Statement st=null;
        ResultSet rs=null;
        try{
            st=conn.createStatement();
            rs=st.executeQuery("Select * from orders");
            while(rs.next()){
                OrderPojo order=new OrderPojo();
                order.setOrderId(rs.getString("orderid"));
                order.setProductId(rs.getString("prodid"));
                order.setQuantity(rs.getInt("quantity"));
                order.setShipped(rs.getInt("shipped"));
                order.setAmount(rs.getDouble("amount"));
                orderList.add(order);
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllOrders"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
         return orderList;
    }

    @Override
    public List<OrderDetailPojo> getAllOrderDetails(String userEmailId) {
        List<OrderDetailPojo> orderList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("Select p.pid as prodid,o.orderid as orderid,o.shipped as shipped,p.image as image, p.pname as pname, o.quantity as qty,o.amount as amount,t.transtime as time from orders o,products p, transactions t where o.orderid=t.transid and o.prodid=p.pid and t.useremail=?");
            ps.setString(1, userEmailId);
            rs=ps.executeQuery();
            while(rs.next()){
                OrderDetailPojo orderDetail=new OrderDetailPojo();
                orderDetail.setOrderId(rs.getString("orderid"));
                orderDetail.setProdImage(rs.getAsciiStream("image"));
                orderDetail.setProdId(rs.getString("prodid"));
                orderDetail.setProdName(rs.getString("pname"));
                orderDetail.setQty(rs.getInt("qty"));
                orderDetail.setAmount(rs.getDouble("amount"));
                orderDetail.setTime(rs.getTimestamp("time"));
                orderDetail.setShipped(rs.getInt("shipped"));
                orderList.add(orderDetail);
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllOrderDetails"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
         return orderList;
        
    }

    @Override
    public String shipNow(String orderId, String prodId,String userId) {
        String status="Failure";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("update orders set shipped=1 where orderid=? and prodid=?");
            ps.setString(1, orderId);
            ps.setString(2, prodId);
            
            
            int count=ps.executeUpdate();
            if (count>0){
                status="order has been shipped successfully";
                
                try {
                     UserDao userDao=new UserDaoImpl();
                    String name=userDao.getUserFirstName(userId);
                    MailMessage.orderShipped(userId, name);
                } catch (Exception e) {
                    System.out.println("Error sending registration email: " + e.getMessage());
                }
            }
            
        }catch(SQLException ex){
            System.out.println("Error in shipNow"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
         return status;
    }
    

    @Override
    public String paymentSuccess(String username, double paidAmount) {
        String status="order placement failed";
        CartDaoImpl cartDao=new CartDaoImpl();
        List<CartPojo> cartList=cartDao.getAllCartItems(username);
        if(cartList.isEmpty()){
            return status;
        }
        
        String transactionId=IDUtil.generateTransId();
        TransactionPojo trPojo=new TransactionPojo();
        trPojo.setTransactionId(transactionId);
        trPojo.setUserEmail(username);
        trPojo.setAmount(paidAmount);
        trPojo.setTransTime(new java.util.Date());
        boolean result=addTransaction(trPojo);
        if(result==false){
            return status;
        }
        boolean ordered=true;
        ProductDaoImpl productDao=new ProductDaoImpl();
        for(CartPojo cartPojo:cartList){
            double amount=productDao.getProductPrice(cartPojo.getProdId())*cartPojo.getQuantity();
            OrderPojo order=new OrderPojo();
            order.setOrderId(transactionId);
            order.setProductId(cartPojo.getProdId());
            order.setQuantity(cartPojo.getQuantity());
            order.setAmount(amount);
            order.setShipped(0);
            ordered=addOrder(order);
            
            if(!ordered){
                break;
            }
            ordered=cartDao.removeAProduct(cartPojo.getUserEmail(), cartPojo.getProdId());
            
            if(!ordered){
                break;
            }
            
            ordered=productDao.sellNProduct(cartPojo.getProdId(), cartPojo.getQuantity());
            
            if(!ordered){
                break;
            }
            
        }
        if(ordered){
            status="order placed successfully";
            System.out.println("Transaction Successful:"+ transactionId);
            
            try {
                    UserDao userDao=new UserDaoImpl();
                    String name=userDao.getUserFirstName(username);
                    System.out.print(name+" "+username);
                    MailMessage.transactionSuccess(username, name,paidAmount);
                } catch (Exception e) {
                    System.out.println("Error sending registration email: " + e.getMessage());
                }
        }else{
            System.out.println("Transaction Failed"+ transactionId);
        }
        return status;
    }

    @Override
    public int getSoldQuantity(String prodId) {
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        int quantity=0;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select sum(quantity) as quant from orders where prodid=?");
            ps.setString(1, prodId);
            rs=ps.executeQuery();
            if(rs.next()){
                quantity=rs.getInt(1);
            }
        }catch(SQLException ex){
           System.out.println("Error in shipNow:"+ex);
           ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return quantity;
    }

    @Override
    public List<OrderPojo> getUnshippedOrders() {
        List<OrderPojo> orderList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        Statement st=null;
        ResultSet rs=null;
        try{
            st=conn.createStatement();
            rs=st.executeQuery("Select * from orders where shipped=0");
            while(rs.next()){
                OrderPojo order=new OrderPojo();
                order.setOrderId(rs.getString("orderid"));
                order.setProductId(rs.getString("prodid"));
                order.setQuantity(rs.getInt("quantity"));
                order.setShipped(rs.getInt("shipped"));
                order.setAmount(rs.getDouble("amount"));
                orderList.add(order);
            }
        }catch(SQLException ex){
            System.out.println("Error in getUnshippedOrders"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
         return orderList;
    }

    @Override
    public List<OrderPojo> getShippedOrders() {
        List<OrderPojo> orderList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        Statement st=null;
        ResultSet rs=null;
        try{
            st=conn.createStatement();
            rs=st.executeQuery("Select * from orders where shipped=1");
            while(rs.next()){
                OrderPojo order=new OrderPojo();
                order.setOrderId(rs.getString("orderid"));
                order.setProductId(rs.getString("prodid"));
                order.setQuantity(rs.getInt("quantity"));
                order.setShipped(rs.getInt("shipped"));
                order.setAmount(rs.getDouble("amount"));
                orderList.add(order);
            }
        }catch(SQLException ex){
            System.out.println("Error in getUnshippedOrders"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
         return orderList;
    }
    
}
