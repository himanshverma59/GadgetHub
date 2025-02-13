/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.UserPojo;
import in.gadgethub.utillity.DBUtil;
import in.gadgethub.utillity.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author himan
 */
public class UserDaoImpl implements UserDao {
    
    public boolean isRegistered(String emailId){
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        boolean flag=false;
        try{
            ps=conn.prepareStatement("select * from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                flag=true;
            }
            
        }catch(SQLException ex){
            System.out.println("Error in isRegistered"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return flag;
    }
    public String registerUser(UserPojo user){
        String status="Registration Failed";
        if(isRegistered(user.getUserEmail())){
            status="Email already registered. Try again!!";
            return status;
        }
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("Insert into users values(?,?,?,?,?,?)");
            ps.setString(1, user.getUserEmail());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getMobile());
            ps.setString(4, user.getAddress());
            ps.setInt(5, user.getPincode());
            ps.setString(6, user.getPassword());
            int count=ps.executeUpdate();
            if(count==1){
                status="Registration successful";
                try {
                    MailMessage.registrationSuccess(user.getUserEmail(), user.getUserName());
                } catch (Exception e) {
                    System.out.println("Error sending registration email: " + e.getMessage());
                }
            }
            
        }catch(SQLException ex){
            System.out.println("Error in Register User"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
    public String isValidCredentials(String emailId,String password){
        String status="Login Denied! Invalid username or password";
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
            ps=conn.prepareStatement("select * from users where useremail=? AND password=?");
            ps.setString(1, emailId);
            ps.setString(2,password);
            rs=ps.executeQuery();
            if(rs.next()){
                status="Login Successful";
            }
            
        }catch(SQLException ex){
            status="Error:"+ex.getMessage();
            System.out.println("Error in isValidCredentials"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return status;
    }
    public UserPojo getUserDetails(String emailId){
        UserPojo user=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
             ps=conn.prepareStatement("select * from users where useremail=? ");
             ps.setString(1, emailId);
             rs=ps.executeQuery();
             if(rs.next()){
                 user=new UserPojo();
                 user.setUserEmail(rs.getString("useremail"));
                 user.setUserName(rs.getString("username"));
                 user.setMobile(rs.getString("mobile"));
                 user.setAddress(rs.getString("address"));
                 user.setPincode(rs.getInt("pincode"));
                 user.setPassword(rs.getString("password"));
                 
             }
        }
        catch(SQLException ex){
            
            System.out.println("Error in getUserDetail"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return user;
    }
    public String getUserFirstName(String emailId){
        String fName="";
        
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
             ps=conn.prepareStatement("select username from users where useremail=? ");
             ps.setString(1, emailId);
             rs=ps.executeQuery();
             if(rs.next()){
                String fullName=rs.getString(1);
                fName=fullName.split(" ")[0];
                 
             }
        }
        catch(SQLException ex){
            
            System.out.println("Error in getUserFirstName"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return fName;
    }
    public String getUserAddr(String emailId){
        String address="";
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
             ps=conn.prepareStatement("select address from users where useremail=? ");
             ps.setString(1, emailId);
             rs=ps.executeQuery();
             if(rs.next()){
                 
                 address=rs.getString(1);
             }
        }
        catch(SQLException ex){
            
            System.out.println("Error in getUserAddr"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return address;
    }
}
