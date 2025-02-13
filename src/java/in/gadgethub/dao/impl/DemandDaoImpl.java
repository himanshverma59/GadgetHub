/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.DemandDao;
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
public class DemandDaoImpl implements DemandDao{

    @Override
    public boolean addProduct(DemandPojo demandpojo) {
        boolean status=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        String updateSQL="update userdemand set quantity=quantity+? where useremail=? and prodid=?";
        String insertSQL="insert into userdemand values(?,?,?)";
        try{
            ps1=conn.prepareStatement(updateSQL);
            ps1.setInt(1, demandpojo.getDemandQuantity());
            ps1.setString(2, demandpojo.getUserEmail());
            ps1.setString(3, demandpojo.getProdId());
            int k=ps1.executeUpdate();
            if(k==0){
                ps2=conn.prepareStatement(insertSQL);
                ps2.setString(1, demandpojo.getUserEmail());
            ps2.setString(2, demandpojo.getProdId());
            ps2.setInt(3, demandpojo.getDemandQuantity());
            ps2.executeUpdate();
            }
            status=true;
        }
        catch(SQLException ex){
            System.out.println("Error in AddProduct"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        
         return status;
    }

    @Override
    public boolean removeProduct(String userId, String prodId) {
         boolean status=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        String updateSQL="delete from userdemand where useremail=? and prodid=?";
        try{
            ps=conn.prepareStatement(updateSQL);
            ps.setString(1,userId);
            ps.setString(2,prodId);
            status=ps.executeUpdate()>0;
        }catch(SQLException ex){
            System.out.println("error in remove product"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public List<DemandPojo> haveDemanded(String prodId) {
       List<DemandPojo> demandList=new ArrayList<>();
       Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select start from userdemand where prodid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            while(rs.next()){
                DemandPojo demandPojo=new DemandPojo();
                demandPojo.setUserEmail(rs.getString("useremail"));
                demandPojo.setProdId(rs.getString("prodid"));
                demandPojo.setDemandQuantity(rs.getInt("quantity"));
                demandList.add(demandPojo);
            }
        }catch(SQLException ex){
            System.out.println("error in have Demanded"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return demandList;
    }
    
}
