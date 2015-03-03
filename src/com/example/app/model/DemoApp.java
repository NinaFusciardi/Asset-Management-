package com.example.app.model;

import java.util.Scanner;
import java.util.List;

public class DemoApp {
    
    public static void main(String[] args){
        
        Scanner keyboard = new Scanner(System.in);
        
        Model model = Model.getInstance();
        
        //Declaring the variables
        int opt; 
      
        do {
            //Creating the Menu
            System.out.println("1. Create new Customer");
            System.out.println("2. Delete existing Customer");
            System.out.println("3. Edit a Customer");
            System.out.println("4. View all Customers");
            System.out.println("5. Create new Branch");
            System.out.println("6. Delete existing Branch");
            System.out.println("7. Edit a Branch");
            System.out.println("8. View all Branches");
            System.out.println("9. Exit");
            System.out.println();
        
            //Asking user for input
            System.out.print("Enter option: ");
            String line = keyboard.nextLine();
            //Passes the users input into the varibale opt
            opt = Integer.parseInt(line);
            
            System.out.println("You chose option " + opt);
            switch (opt) {
                case 1: {
                    System.out.println("Creating customer");
                    createCustomer(keyboard, model);
                    break;
                }
                case 2: {
                    System.out.println("Deleting customer");
                    deleteCustomer(keyboard, model);
                    break;
                }
                case 3: {
                    System.out.println("Editing customer");
                    editCustomer(keyboard, model);
                    break;
                }
                case 4: {
                    System.out.println("Viewing customer");
                    viewCustomers(model);
                    break;
                }    
                case 5: {
                    System.out.println("Creating branch");
                    createBranch(keyboard, model);
                    break;
                }
                case 6: {
                    System.out.println("Deleting branch");
                    deleteBranch(keyboard, model);
                    break;
                }
                case 7: {
                    System.out.println("Editing branch");
                    editBranch(keyboard, model);
                    break;
                }
                case 8: {
                    System.out.println("Viewing branch");
                    viewBranches(model);
                    break;
                }
            }
        }
        while (opt != 9); //The do loop runs until the user input 9
        System.out.println("Goodbye");
    }
       
    //Methods for creating a customer
    private static void createCustomer(Scanner keyboard, Model mdl){
        Customer c = readCustomer(keyboard);
            if (mdl.addCustomer(c)){
                System.out.println("Customer added to database. ");
                System.out.println("");
            }
            else {
                System.out.println("Customer not added to database");
                System.out.println("");
            }
    }
    
    private static Customer readCustomer(Scanner keyb) {
        int customerID;
        String fName, lName, address, email;
        int mobile, branchID;
        String line;
        
        fName = getString(keyb, "Enter name: ");
        lName = getString(keyb, "Enter surname: ");
        address = getString(keyb, "Enter address: ");
        email = getString(keyb, "Enter email: ");
        line = getString(keyb, "Enter mobile: ");
        mobile = Integer.parseInt(line);
        line = getString(keyb, "Enter branchID: ");
        branchID = Integer.parseInt(line);
        
        Customer c = 
                new Customer(
                        fName, lName, address, mobile, email, branchID);
          
        return c;
    }
    
    //Methods for deleting a customer
    private static void deleteCustomer(Scanner keyboard, Model model) {
        System.out.print("Enter the customer ID of the customer to delete:");
        int customerID = Integer.parseInt(keyboard.nextLine());
        Customer c;

        c = model.findCustomerByCustomerID(customerID);
        if (c != null) {
            if (model.removeCustomer(c)) {
                System.out.println("Customer deleted");
            }
            else {
                System.out.println("Customer not deleted");
            }
        }
        else {
            System.out.println("Customer not found");
        }
    }  
    
    //Methods for editing a customer
    private static void editCustomer(Scanner kb, Model m) {
        System.out.print("Enter the customer ID of the customer to edit:");
        int customerID = Integer.parseInt(kb.nextLine());
        Customer c;

        c = m.findCustomerByCustomerID(customerID);
        if (c != null) {
            editCustomerDetails(kb, c);
            if (m.updateCustomer(c)) {
                System.out.println("Customer updated");
            }
            else {
                System.out.println("Customer not updated");
            }
        }
        else {
            System.out.println("Customer not found");
        }
    }

    private static void editCustomerDetails(Scanner keyb, Customer c) {
        int customerID;
        String fName, lName, address, email;
        int mobile, branchID;
        String line1 , line2;
        
        fName = getString(keyb, "Enter name[" + c.getFName() + "]:");
        lName = getString(keyb, "Enter surname[" + c.getLName() + "]:");
        address = getString(keyb, "Enter address[" + c.getAddress() + "]:");
        email = getString(keyb, "Enter email[" + c.getEmail() + "]:");
        line1 = getString(keyb, "Enter mobile[" + c.getMobile() + "]:");
        line2 = getString(keyb, "Enter branchID[" + c.getBranchID() + "]:");
        
        if (fName.length() != 0){
            c.setFName(fName);
        }
        if (lName.length() != 0){
            c.setLName(lName);
        }
        if (address.length() != 0){
            c.setAddress(address);
        }
        if (email.length() != 0){
            c.setEmail(email);
        }
        if (line1.length() != 0){
            mobile = Integer.parseInt(line1);
            c.setMobile(mobile);
        }
        if (line2.length() != 0){
            branchID = Integer.parseInt(line2);
            c.setBranchID(branchID);
        }
       
    }
    
    //Methods for viewing a customer
    private static void viewCustomers(Model mdl) {
        List<Customer> customers = mdl.getCustomers();
        System.out.println();
        if (customers.isEmpty()){
            System.out.println("There are no customers in the database");
        }
        else { 
            System.out.printf("%5s %20s %20s %20s %20s %30s %20s\n", "customerID", "fName", "lName", "address", "mobile", "email", "branchID");
            for (Customer c : customers){
                System.out.printf("%10d %20s %20s %20s %20d %30s %20s\n",
                    c.getCustomerID(),
                    c.getFName(),
                    c.getLName(),
                    c.getAddress(),
                    c.getMobile(),
                    c.getEmail(),
                    c.getBranchID());
            }                    
        }
        System.out.println();
    }
    
    private static void viewBranches(Model mdl){
        List<Branch> branches = mdl.getBranches();
        System.out.println();
        if (branches.isEmpty()){
            System.out.println("There are no branches in the database");
        }
        else {
            System.out.printf("%5s %20s %20s %20s %20s\n", "branchID", "address", "mobile", "branchManager", "openingHours");
        }
            for (Branch b : branches){
                System.out.printf("%10d %20s %20s %20s %20s8\n",
                    b.getBranchID(),
                    b.getAddress(),
                    b.getMobile(),
                    b.getBranchManager(),
                    b.getOpeningHours());
            }
            System.out.println();
        }
       
    
    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }
    
    
    }



