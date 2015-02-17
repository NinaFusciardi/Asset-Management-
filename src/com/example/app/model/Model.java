package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
   
    private static Model instance = null;

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    
    //Array list for customers
    private List<Customer> customers;
    CustomerTableGateway gateway;
    
    private Model() {

        try {
            Connection conn = DBConnection.getInstance();
            this.gateway = new CustomerTableGateway(conn);
            
            this.customers = this.gateway.getCustomers();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Adding a customer to the database
    public boolean addCustomer(Customer c) {
        boolean result = false;
        try {
            int id = this.gateway.insertCustomer(c.getFName(), c.getLName(), c.getAddress(), c.getMobile(), c.getEmail(), c.getBranchID());
            if (id != -1){
                c.setCustomerID(id);
                this.customers.add(c);
                result = true;
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    //Deleting a customer off the database
    public boolean removeCustomer(Customer c) {
        boolean removed = false;
        
        try {
            removed = this.gateway.deleteCustomer(c.getCustomerID());
            if (removed){
                removed = this.customers.remove(c);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
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
            updated = this.gateway.updateCustomer(c);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return updated;
    }
    
    //Viewing all customers in the database
    public List<Customer> getCustomers() {
        return this.customers;
    }
}     