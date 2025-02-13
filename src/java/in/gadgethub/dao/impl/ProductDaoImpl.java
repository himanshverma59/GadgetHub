/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.ProductDao;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.utillity.DBUtil;
import in.gadgethub.utillity.IDUtil;
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
public class ProductDaoImpl implements ProductDao {
    public String addProduct(ProductPojo product){
        String status="Product Registration Failed";
        if(product.getProdId()==null){
            product.setProdId(IDUtil.generateProdId());
        }
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("Insert into products values(?,?,?,?,?,?,?,?)");
            ps.setString(1, product.getProdId());
            ps.setString(2, product.getProdName());
            ps.setString(3, product.getProdType());
            String pInfo=product.getProdInfo();
            if (pInfo.length() > 250) {
               pInfo = pInfo.substring(0, 250);
                 }

            ps.setString(4, pInfo);
            ps.setDouble(5, product.getProdPrice());
            ps.setInt(6, product.getQuantity());
            ps.setBlob(7, product.getProdImage());
            ps.setString(8, "Y");
            int count=ps.executeUpdate();
            if(count==1){
                status="Product Added successful with Id"+product.getProdId();
                
            }
            
        }catch(SQLException ex){
            System.out.println("Error in AddProduct"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
         return status;
    }
    public String updateProduct(ProductPojo prevProduct,ProductPojo updatedProduct){
        String status="Updation Failed!";
        if(!prevProduct.getProdId().equals(updatedProduct.getProdId())){
            status="product id mismatched!";
            return status;
        }
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("update products set pname=?,ptype=?,pprice=?,pquantity=?,image=? where pid=?");
            ps.setString(1, updatedProduct.getProdName());
            ps.setString(2, updatedProduct.getProdType());
            ps.setString(3, updatedProduct.getProdInfo());
            ps.setDouble(4, updatedProduct.getProdPrice());
            ps.setInt(5, updatedProduct.getQuantity());
            ps.setBlob(6, updatedProduct.getProdImage());
            ps.setString(7,updatedProduct.getProdId());
            int count=ps.executeUpdate();
            if(count==1){
                status="Product updated successfully";
                
            }
            
        }catch(SQLException ex){
            System.out.println("Error in updateProduct"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
         return status;
        
    }
    public String updateProductPrice(String prodId,double updatedPrice){
        String status="Updation Failed!";
       
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("update products set pprice=? where pid=?");
            ps.setDouble(1, updatedPrice);
            ps.setString(2,prodId);
            int count=ps.executeUpdate();
            if(count==1){
                status="price updated Successfully";
                
            }
            
        }catch(SQLException ex){
            System.out.println("Error in updatePrice"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
         return status;
        
    }
    public List<ProductPojo>getAllProducts(){
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        Statement st=null;
        ResultSet rs=null;
        try{
            st=conn.createStatement();
            rs=st.executeQuery("select * from products where available='Y'");
            
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in getAllProducts"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
        return productList;
    }
    public List<ProductPojo>getAllProductsByType(String ptype){
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        ptype=ptype.toLowerCase();
        try{
           ps=conn.prepareStatement("select * from products where lower(ptype) like ? and available='Y'");
           ps.setString(1,"%"+ptype+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in GetAllProductByType"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return productList;
    }
    public List<ProductPojo>searchAllProducts(String searchTerm){
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        searchTerm=searchTerm.toLowerCase();
        try{
           ps=conn.prepareStatement("select * from products where lower(ptype) like ? or lower(pname) like? or lower(pinfo) like ? and available='y'");
           ps.setString(1,"%"+searchTerm+"%");
            ps.setString(2,"%"+searchTerm+"%");
             ps.setString(3,"%"+searchTerm+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in GetAllProductByType"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return productList;
    }
    public ProductPojo getProductDetails(String prodId){
         ProductPojo product=null;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
           ps=conn.prepareStatement("select * from products where pid=? ");
           ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
                product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                
            }
            
        }catch(SQLException ex){
            System.out.println("Error in Geting product details "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return product;
    }
   public int getProductQuantity(String prodId){
       int quantity=0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try{
           ps=conn.prepareStatement("select pquantity from products where pid=?");
           ps.setString(1,prodId);
            
            rs=ps.executeQuery();
            if(rs.next()){
                quantity=rs.getInt(1);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in get product quantity"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return quantity;
   }
   
     public String updateProductWithoutImage(String prevProductId,ProductPojo updatedProduct){
        String status="Updation Failed!";
        int prevQuantity=0;
        if(!prevProductId.equals(updatedProduct.getProdId())){
            status="product id mismatched!";
            return status;
        }
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps2=null;
       
        try{
           
                prevQuantity=getProductQuantity(prevProductId);
            
            ps2=conn.prepareStatement("update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=? where pid=?");
            ps2.setString(1, updatedProduct.getProdName());
            ps2.setString(2, updatedProduct.getProdType());
            ps2.setString(3, updatedProduct.getProdInfo());
            ps2.setDouble(4, updatedProduct.getProdPrice());
            ps2.setInt(5, updatedProduct.getQuantity());
            
            ps2.setString(6,updatedProduct.getProdId());
            int count=ps2.executeUpdate();
            if(count==1&& prevQuantity<updatedProduct.getQuantity()){
                status="Product updated successfully and Mail sent";
                //mail code
                
            }else if(count==1){
                status="Product updated successfully and Mail sent";
            }
        }catch(SQLException ex){
            System.out.println("Error in updateProduct"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps2);
        
         return status;
        
    }

    

    @Override
    public double getProductPrice(String prodId) {
        double price=0.0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try{
           ps=conn.prepareStatement("select pprice from products where pid=?");
           ps.setString(1,prodId);
            
            rs=ps.executeQuery();
            if(rs.next()){
                price=rs.getInt(1);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in getProductprice"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return price;
    }

    @Override
    public boolean sellNProduct(String prodId, int n) {
        boolean status=false;
       
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("update products set pquantity=(pquantity-?) where pid=? and available='Y'");
            ps.setInt(1, n);
            ps.setString(2,prodId);
            int count=ps.executeUpdate();
            if(count==1){
                status=true;
                
            }
            
        }catch(SQLException ex){
            System.out.println("Error in sellNProduct"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
         return status;
    }

    @Override
    public List<String> getAllProductsType() {
         List<String> productTypeList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        Statement st=null;
        ResultSet rs=null;
       
        try{
           st=conn.createStatement();
          
            rs=st.executeQuery("select distinct ptype from products where available='Y'");
            while(rs.next()){
             productTypeList.add(rs.getString("ptype"));
            }
            
        }catch(SQLException ex){
            System.out.println("Error in GetAllProductType"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
        
        return productTypeList;
    }

    @Override
    public byte[] getImage(String prodId) {
        byte[]arr=null;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try{
           ps=conn.prepareStatement("select image from products where pid=?");
           ps.setString(1,prodId);
            
            rs=ps.executeQuery();
            if(rs.next()){
                arr=rs.getBytes(1);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in getImage"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return arr;
    }

    @Override
    public String removeProduct(String prodId) {
          String status="Product Not Found";
       
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps2=null;
        PreparedStatement ps1=null;
       
        try{
            ps2=conn.prepareStatement("update products set available='N' where pid=? and available='Y'");
            ps2.setString(1,prodId);
            int count=ps2.executeUpdate();
            if(count==1){
                ps1=conn.prepareStatement("delete from usercart where prodid=?");
                ps1.setString(1,prodId);
                count=ps1.executeUpdate();
                status="Product deletion successfully";
                
                
            }
        }catch(SQLException ex){
            status="Error:"+ex.getMessage();
            System.out.println("Error in removeproduct"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps2);
        DBUtil.closeStatement(ps1);
        
         return status;
    }
}
