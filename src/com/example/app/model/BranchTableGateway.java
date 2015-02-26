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
