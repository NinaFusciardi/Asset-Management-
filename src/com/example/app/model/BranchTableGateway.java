package com.example.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BranchTableGateway {
    
    private Connection mConnection;
    
    private static final String TABLE_NAME = "branch";
    private static final String COLUMN_BRANCHID = "branchID";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_MOBILE = "mobile";
    private static final String COLUMN_BRANCHMANAGER = "branchManager";
    private static final String COLUMN_OPENINGHOURS = "openingHours";
    
    public BranchTableGateway(Connection connection) {
        mConnection = connection;
    }
    
    //Adding a branch
    public int insertBranch(int id, String a, int m, String bm, String hrs) throws SQLException {
        String query;       //the SQL query to execute
        PreparedStatement stmt;
        int numRowsAffected;
        int branchID = -1;
        
        //the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_BRANCHID + ", " +
                COLUMN_ADDRESS + ", " +
                COLUMN_MOBILE + ", " +
                COLUMN_BRANCHMANAGER + ", " +
                COLUMN_OPENINGHOURS +
                ") VALUES (?, ?, ?, ?, ?)";
        
        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, id);
        stmt.setString(2, a);
        stmt.setInt(3, m);
        stmt.setString(4, bm);
        stmt.setString(5, hrs);
        
        //execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if(numRowsAffected == 1) {
            //if one row was inserted, retrieve the id assigned to that row
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            
            branchID = keys.getInt(1);
        }
        
        //return the id assigned to the row int he database
        return branchID;
    }
    
    //Deleting a customer
    public boolean deleteBranch(int id) throws SQLException{
        String query;       //the SQL query to execute
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_BRANCHID + " = ? " ;
        
        // create a PreparedStatement object to execute the query and insert the id into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        //execute the query
        numRowsAffected = stmt.executeUpdate();
        
        //return the true if one and only one row was deleted from the database
        return (numRowsAffected == 1);
        
    }
    
    //Updating a customer
    boolean updateBranch(Branch b) throws SQLException {
        String query;       //the SQL query to execute
        PreparedStatement stmt;
        int numRowsAffected;
        
        //the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_BRANCHID + " = ?, " +
                COLUMN_ADDRESS + " = ?, " +
                COLUMN_MOBILE + " = ?, " +
                COLUMN_BRANCHMANAGER + " = ?, " +
                COLUMN_OPENINGHOURS + " = ? " +
                "WHERE " + COLUMN_BRANCHID + " = ?";
        
        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, b.getBranchID());
        stmt.setString(2, b.getAddress());
        stmt.setInt(3, b.getMobile());
        stmt.setString(4, b.getBranchManager());
        stmt.setString(5, b.getOpeningHours());
      
        
        //execute the query
        numRowsAffected = stmt.executeUpdate();
        
        // return the true if one and only one row was updated in the database
        return numRowsAffected == 1;
           
    }
    
    public List<Branch> getBranches() throws SQLException {
        String query;                     // the SQL query to execute
        Statement stmt;                  // the java.sql.Statement object used to execute the SQL query
        ResultSet rs;                   // the java.sql.ResultSet representing the result of SQL query
        List<Branch> branches;         // the java.util.List containing the Branch objects created for each row
                                      // in the result of the query the id of a branch
        
        String address, branchManager, openingHours;
        int branchID, mobile;
        Branch b;                       // a Branch object created from a row in the result of the query
        
        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
        // iterate through the result set, extracting the data from each row
        // and storing it in a Branch object, which is inserted into an initially
        // empty ArrayList
        
        branches = new ArrayList<Branch>();
        while (rs.next()) {
            branchID = rs.getInt(COLUMN_BRANCHID);
            address = rs.getString(COLUMN_ADDRESS);
            mobile = rs.getInt(COLUMN_MOBILE);
            branchManager = rs.getString(COLUMN_BRANCHMANAGER);
            openingHours = rs.getString(COLUMN_OPENINGHOURS);
            
            b = new Branch(branchID, address, mobile, branchManager, openingHours);
            branches.add(b);
        }
        
        // return the list of Branch objects retrieved
        return branches;
        
        
    }
}
