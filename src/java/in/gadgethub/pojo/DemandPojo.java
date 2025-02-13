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
public class DemandPojo {
    private String userEmail;
    private String prodId;
    private int demandQuantity;

    public DemandPojo() {
    }

    public DemandPojo(String userEmail, String prodId, int demandQuantity) {
        this.userEmail = userEmail;
        this.prodId = prodId;
        this.demandQuantity = demandQuantity;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getDemandQuantity() {
        return demandQuantity;
    }

    public void setDemandQuantity(int demandQuantity) {
        this.demandQuantity = demandQuantity;
    }

    @Override
    public String toString() {
        return "DemandPojo{" + "userEmail=" + userEmail + ", prodId=" + prodId + ", demandQuantity=" + demandQuantity + '}';
    }
    
    
}
