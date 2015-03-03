package com.example.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerTableGateway {
    
    private static final String TABLE_NAME = "customer";
    private static final String COLUMN_CUSTOMERID = "customerID";
    private static final String COLUMN_FNAME = "fName";
    private static final String COLUMN_LNAME = "lName";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_MOBILE = "mobile";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_BRANCHID = "branchID";
    
    private Connection mConnection;
    
    public CustomerTableGateway(Connection connection) {
        mConnection = connection;
    }
    
    //Adding a customer
    public int insertCustomer(String fn, String ln, String a, int m, String e, int bid) throws SQLException {
        String query;       //the SQL query to execute
        PreparedStatement stmt;
        int numRowsAffected;
        int customerID = -1;
        
        //the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_FNAME + ", " +
                COLUMN_LNAME + ", " +
                COLUMN_ADDRESS + ", " +
                COLUMN_MOBILE + ", " +
                COLUMN_EMAIL + ", " +
                COLUMN_BRANCHID +
                ") VALUES (?, ?, ?, ?, ?, ?)";
        
        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, fn);
        stmt.setString(2, ln);
        stmt.setString(3, a);
        stmt.setInt(4, m);
        stmt.setString(5, e);
        stmt.setInt(6, bid);
        
        //execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if(numRowsAffected == 1) {
            //if one row was inserted, retrieve the id assigned to that row
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            
            customerID = keys.getInt(1);
        }
        
        //return the id assigned to the row int he database
        return customerID;
    }
    
    //Deleting a customer
    public boolean deleteCustomer(int id) throws SQLException{
        String query;       //the SQL query to execute
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_CUSTOMERID + " = ? " ;
        
        // create a PreparedStatement object to execute the query and insert the id into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        //execute the query
        numRowsAffected = stmt.executeUpdate();
        
        //return the true if one and only one row was deleted from the database
        return (numRowsAffected == 1);
        
    }
    
    //Updating a customer
    boolean updateCustomer(Customer c) throws SQLException {
        String query;       //the SQL query to execute
        PreparedStatement stmt;
        int numRowsAffected;
        
        //the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_FNAME + " = ?, " +
                COLUMN_LNAME + " = ?, " +
                COLUMN_ADDRESS + " = ?, " +
                COLUMN_MOBILE + " = ?, " +
                COLUMN_EMAIL + " = ?, " +
                COLUMN_BRANCHID + " = ? " +
                "WHERE " + COLUMN_CUSTOMERID + " = ?";
        
        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, c.getFName());
        stmt.setString(2, c.getLName());
        stmt.setString(3, c.getAddress());
        stmt.setInt(4, c.getMobile());
        stmt.setString(5, c.getEmail());
        stmt.setInt(6, c.getBranchID());
        stmt.setInt(7, c.getCustomerID());
        
        //execute the query
        numRowsAffected = stmt.executeUpdate();
        
        // return the true if one and only one row was updated in the database
        return numRowsAffected == 1;
           
    }
    
    //Viewing customers
    public List<Customer> getCustomers() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the
                            // SQL query
        ResultSet rs;       // the java.sql.ResultSet representing the result of
                            // SQL query 
        List<Customer> customers;   // the java.util.List containing the Programmer objects
                            // created for each row in the result of the query
        int customerID;             // the id of a programmer
        String fName, lName, email, address;
        int mobile, branchID;
        Customer c;       // a Programmer object created from a row in the result of the query
                             
        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
         // iterate through the result set, extracting the data from each row
        // and storing it in a Programmer object, which is inserted into an initially
        // empty ArrayList
        customers = new ArrayList<Customer>();
        while (rs.next()) {
            customerID = rs.getInt(COLUMN_CUSTOMERID);
            fName = rs.getString(COLUMN_FNAME);
            lName = rs.getString(COLUMN_LNAME);
            address = rs.getString(COLUMN_ADDRESS);
            mobile = rs.getInt(COLUMN_MOBILE);
            email = rs.getString(COLUMN_EMAIL);
            branchID = rs.getInt(COLUMN_BRANCHID);
            
            c = new Customer(customerID, fName, lName, address, mobile, email, branchID);
            customers.add(c);
        }

        return customers;
    }   
   
}   

