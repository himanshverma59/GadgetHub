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
public class UserPojo {
    
    private String userName;
    private String userEmail;
    private String mobile;
    private String address;
    private String password;
    private int pincode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public UserPojo(String userName, String userEmail, String mobile, String address, String password, int pincode) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.mobile = mobile;
        this.address = address;
        this.password = password;
        this.pincode = pincode;
    }

    public UserPojo() {
    }
   
    
}
