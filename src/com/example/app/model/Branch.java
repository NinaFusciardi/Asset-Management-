package com.example.app.model;

public class Branch {
    
    private int branchID;
    private String address;
    private int mobile;
    private String branchManager;
    private String openingHours;
    
    public Branch(int id, String a, int m, String bm, String hrs ){
        this.branchID = id;
        this.address = a;
        this.mobile = m;
        this.branchManager = bm;
        this.openingHours = hrs;
    }
    
    public Branch(String a, int m, String bm, String hrs){
        this(-1, a, m, bm, hrs);
    }
    
    public int getBranchID() {
        return branchID;
    }
    
    public void setBranchID(int BranchID){
        this.branchID = BranchID;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
   
    public int getMobile() {
        return mobile;
    } 
    
    public void setMobile(int mobile){
        this.mobile = mobile;
    }
    
    public String getBranchManager() {
        return branchManager;
    }
    
    public void setBranchManager(String branchManager){
        this.branchManager = branchManager;
    }
    
    public String getOpeningHours() {
        return openingHours;
    }
    
    public void setOpeningHours(String openingHours){
        this.openingHours = openingHours;
    }
}

