/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.utillity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author himan
 */
public class DBUtil {
    private static Connection conn;
    public static void openConnection(String dburl, String username,String password){
        if(conn==null){
        try{
            conn=DriverManager.getConnection(dburl,username,password);
            System.out.println(" gadgethub Connection opened!");
        }catch(SQLException ex){
            System.out.println("Error in opening connection");
            ex.printStackTrace();
        }
        }
    }
    public static void closeConnection(){
        if(conn!=null){
        try{
            conn.close();
        }catch(SQLException ex){
            System.out.println("Error in closing connection");
            ex.printStackTrace();
        }
       
    } 
    }
    public static Connection provideConnection(){
            return conn;
        }
    public static void closeResultSet(ResultSet rs){
        if(rs!=null){
        try{
            rs.close();
        }catch(SQLException ex){
            System.out.println("error in closing resultset");
            ex.printStackTrace();
            
        }
        }
    }
    public static void closeStatement(Statement st){
        if(st!=null){
        try{
            st.close();
            }
        catch(SQLException ex){
            System.out.println("error in closing Statement");
            ex.printStackTrace();
            
        }
    }
    }
}
