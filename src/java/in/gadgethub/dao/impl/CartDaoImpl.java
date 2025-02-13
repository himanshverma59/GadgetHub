/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.CartDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.utillity.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author himan
 */
public class CartDaoImpl implements CartDao{

    @Override
    public String addProductToCart(CartPojo cart) {
         String status="Failed to Add into Cart";
         Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
         
        ResultSet rs=null;
        try{
            ps1=conn.prepareStatement("select * from usercart where prodid=? and useremail=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2,cart.getUserEmail());
            rs=ps1.executeQuery();
            if(rs.next()){
             ProductDaoImpl prodDao=new ProductDaoImpl();   
             int stockQty=prodDao.getProductQuantity(cart.getProdId());
             int newQty=cart.getQuantity()+rs.getInt("quantity");
             if(stockQty<newQty){
                 cart.setQuantity(stockQty);
                 updateProductInCart(cart);
                 status="Only +"+stockQty+" no of items are available in our stock so we are adding "+stockQty+" in your cart";
                 DemandPojo demandPojo=new DemandPojo();
                 demandPojo.setProdId(cart.getProdId());
                 demandPojo.setUserEmail(cart.getUserEmail());
                 demandPojo.setDemandQuantity(newQty-stockQty);
                 DemandDaoImpl demandDao = new DemandDaoImpl();
                 boolean result=demandDao.addProduct(demandPojo);
                 if(result){
                    status+="we will notify you when +"+(newQty-stockQty)+" no of items will available in our stock "; 
                 }
             }else{
                 cart.setQuantity(newQty);
                 status=updateProductInCart(cart);
             }
            }
            
        }catch(SQLException ex){
            status="Addition failed due to exception";
            System.out.println("Error in addProductTocart"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1);
       
        return status;
    }

    @Override
    public String updateProductInCart(CartPojo cart) {
        String status="Failed to Add into Cart";
         Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
         PreparedStatement ps2=null;
        ResultSet rs=null;
        int ans=0;
        try{
            ps1=conn.prepareStatement("select * from usercart where prodid=? and useremail=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2,cart.getUserEmail());
            rs=ps1.executeQuery();
            if(rs.next()){
                int qty=cart.getQuantity();
                if(qty>0){
                     ps2=conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                ps2.setString(2, cart.getUserEmail());
                ps2.setString(3,cart.getProdId());
                ps2.setInt(1, cart.getQuantity());
                ans=ps2.executeUpdate();
                if(ans>0){
                    status="product succcessfully updated";
                }else{
                    status="could not update the product";
                }
                }
                else if(qty==0){
                     ps2=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
                ps2.setString(1, cart.getUserEmail());
                ps2.setString(2,cart.getProdId());
               
                ans=ps2.executeUpdate();
                if(ans>0){
                    status="product succcessfully updated";
                }else{
                    status="could not update the product";
                }
                }
            }
            else{
                ps2=conn.prepareStatement("Insert into usercart values(?,?,?)");
                ps2.setString(1, cart.getUserEmail());
            ps2.setString(2,cart.getProdId());
            ps2.setInt(3, cart.getQuantity());
            ans=ps2.executeUpdate();
            if(ans>0){
                status="product successfully added to cart";
            }else{
                    status="could not insert the product";
                }
            
            }
        }catch(SQLException ex){
            status="updation failed due to exception";
            System.out.println("error in updateProductInCard "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }

    @Override
    public List<CartPojo> getAllCartItems(String userId) {
        List<CartPojo> items = new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select * from usercart where useremail=?");
            ps.setString(1, userId);
            rs=ps.executeQuery();
            while(rs.next()){
                CartPojo cart=new CartPojo();
                cart.setUserEmail(rs.getString("useremail"));
                cart.setProdId(rs.getString("prodid"));
                cart.setQuantity(rs.getInt("quantity"));
                items.add(cart);
                
            }
        }catch(SQLException ex){
            
            System.out.println("error in getAllCartItems "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        
        return items;
        
    }

    @Override
    public int getCartItemCount(String userId, String itemId) {
        if(userId==null||itemId==null)return 0;
        int count=0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select quantity from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2,itemId);
            rs=ps.executeQuery();
            if(rs.next()){
                count=rs.getInt(1);
            }
        }catch(SQLException ex){
            
            System.out.println("error in getCartItemCount "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        
        return count;
            
        
    }

    @Override
    public String removeProductFromCart(String userId, String prodId) {
        String status="Product Removal failed";
         Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
         PreparedStatement ps2=null;
         
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select * from usercart where prodid=? and useremail=?");
            ps.setString(1, prodId);
            ps.setString(2,userId);
            rs=ps.executeQuery();
            if(rs.next()){
                int prodQuantity=rs.getInt("quantity");
                prodQuantity--;
                if(prodQuantity>0){
                    ps2=conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                    ps2.setInt(1, prodQuantity);
                    ps2.setString(2,userId);
                    ps2.setString(3, prodId);
                    int k=ps2.executeUpdate();
                    if(k>0){
                        status="removal of product is successfull";
                    }
                    
                }else{
                    ps2=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
                    ps2.setString(1, userId);
                    ps2.setString(2,prodId);
                    int k=ps2.executeUpdate();
                    if(k>0){
                        status="removal of product is successfull";
                    }
            
                }
                
            }
            }catch(SQLException ex){
            status="Product Removal failed";
            System.out.println("error in removeProductFromCart "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        DBUtil.closeStatement(ps2);
       return status; 
    }

    @Override
    public Boolean removeAProduct(String userId, String prodId) {
        boolean status=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2, prodId);
            int k=ps.executeUpdate();
            //System.out.print(k);
            if(k>0){
                status=true;
            }
        }catch(SQLException ex){
            
            System.out.println("error in removeAproduct "+ex);
            ex.printStackTrace();
        }
        
        DBUtil.closeStatement(ps);
        
        return status;
    }
    
}
