//Class is the template for creating customer objects.
package com.example.app.model;

public class Customer {
    
    private int customerID;
    private String fName;
    private String lName;
    private String address;
    private int mobile;
    private String email;
    private int branchID;
    
    //This a new comment
    //New comment 2
    
    public Customer(int id, String fn, String ln, String a, int m, String e, int b) {
        
        this.customerID = id;
        this.fName = fn;
        this.lName = ln;
        this.address = a;
        this.mobile = m;
        this.email = e;
        this.branchID = b;
    }
    
    public Customer(String fn, String ln, String a, int m, String e, int b){
        this(-1, fn, ln, a, m, e, b);
    }
    
    public int getCustomerID() {
        return customerID;
    }
    
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    public String getFName() {
        return fName;
    }
    
    public void setFName(String fName) {
        this.fName = fName;
    }
    
    public String getLName() {
        return lName;
    }
    
    public void setLName(String lName) {
        this.lName = lName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getMobile() {
        return mobile;
    }
    
    public void setMobile(int mobile) {
        this.mobile = mobile;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getBranchID() {
        return branchID;
    }
    
    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }
}