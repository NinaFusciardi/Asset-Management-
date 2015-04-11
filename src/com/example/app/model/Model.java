package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
   
    private static Model instance = null;

    public static Model getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    
    //Array list for customers
    List<Customer> customers;
    List<Branch> branches;
    CustomerTableGateway customerGateway;
    BranchTableGateway branchGateway;
    
   private Model() throws DataAccessException {

        try {
            Connection conn = DBConnection.getInstance();
            this.customerGateway = new CustomerTableGateway(conn);
            this.branchGateway = new BranchTableGateway(conn);
            
            this.customers = this.customerGateway.getCustomers();
            this.branches = this.branchGateway.getBranches();
        } 
        catch (ClassNotFoundException ex) {
            throw new DataAccessException("Exception initialising Model object: " + ex.getMessage());
        } 
        catch (SQLException ex) {
            throw new DataAccessException("Exception initialising Model object: " + ex.getMessage());
        }
    }
    
    //Viewing all customers in the database
    public List<Customer> getCustomers() {
        return this.customers;
    }
    
    //Adding a customer to the database
    public boolean addCustomer(Customer c) throws DataAccessException {
        boolean result = false;
        try {
            int id = this.customerGateway.insertCustomer(c.getFName(), c.getLName(), c.getAddress(), c.getMobile(), c.getEmail(), c.getBranchID());
            if (id != -1){
                c.setCustomerID(id);
                this.customers.add(c);
                result = true;
            }
        } 
        catch (SQLException ex) {
            throw new DataAccessException("Exception adding customer: " + ex.getMessage());
        }
        return result;
    }
    
    //Deleting a customer off the database
    public boolean removeCustomer(Customer c) throws DataAccessException {
        boolean removed = false;
        
        try {
            removed = this.customerGateway.deleteCustomer(c.getCustomerID());
            if (removed){
                removed = this.customers.remove(c);
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception removing a customer: " + ex.getMessage());
        }
        
        return removed;
    }

    Customer findCustomerByCustomerID(int customerID) {
        Customer c = null;
        int i = 0;
        boolean found = false;
        while (i < this.customers.size() && !found) {
            c = this.customers.get(i);
            if (c.getCustomerID() == customerID) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            c = null;
        }
        return c;
    }
    
    //Editing a customer on the database
    boolean updateCustomer(Customer c){
        boolean updated = false;
        
        try {
            updated = this.customerGateway.updateCustomer(c);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return updated;
    }
    
    public List<Customer> getCustomersByBranchID(int branchID) {
        List<Customer> list = new ArrayList<Customer>();
        for (Customer c : this.customers){
            if (c.getBranchID() == branchID){
                list.add(c);
            }
        }
        return list;
    }
    
    //Viewing all branches in the database
    public List<Branch> getBranches() {
        return this.branches;
    }
    
    //Adding a branch to the database
    public boolean addBranch(Branch b)throws DataAccessException {
        boolean result = false;
        try {
            int id = this.branchGateway.insertBranch(b.getAddress(), b.getMobile(), b.getBranchManager(), b.getOpeningHours());
            if (id != -1){
                b.setBranchID(id);
                this.branches.add(b);
                result = true;
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception adding branch: " + ex.getMessage());
        }
        return result;
        
    }
    
     //Deleting a branch off the database
    public boolean removeBranch(Branch b) throws DataAccessException {
        boolean removed = false;
        
        try {
            removed = this.branchGateway.deleteBranch(b.getBranchID());
            if (removed){
                removed = this.branches.remove(b);
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception removing branch: " + ex.getMessage());
        }
        
        return removed;
    }
    
    //Editing a branch on the database
    boolean updateBranch(Branch b) throws DataAccessException {
        boolean updated = false;
        
        try {
            updated = this.branchGateway.updateBranch(b);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception updating branch: " + ex.getMessage());
        }
        
        return updated;
    }
    
    Branch findBranchById(int id) {
        Branch b = null;
        int i = 0;
        boolean found = false;
        while (i < this.branches.size() && !found) {
            b = this.branches.get(i);
            if (b.getBranchID() == id) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            b = null;
        }
        return b;
    }
    
  
    
    
    

}     

