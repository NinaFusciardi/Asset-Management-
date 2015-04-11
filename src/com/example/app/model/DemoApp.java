package com.example.app.model;

import java.util.Scanner;
import java.util.List;

public class DemoApp {
    
    public static void main(String[] args){
        
        Scanner keyboard = new Scanner(System.in);
        
        Model model;
        
        //Declaring the variables
        int opt = 11; 
      
        do {
            try{
            model = Model.getInstance();
            
            //Creating the Menu
            System.out.println("");
            System.out.println("1. Create new Customer");
            System.out.println("2. Delete existing Customer");
            System.out.println("3. Edit a Customer");
            System.out.println("4. View all Customers");
            System.out.println("5. View single Customer");
            System.out.println();
            System.out.println("6. Create new Branch");
            System.out.println("7. Delete existing Branch");
            System.out.println("8. Edit a Branch");
            System.out.println("9. View all Branches");
            System.out.println("10. View single Branch");
            System.out.println("11. Exit");
            System.out.println();
        
            //Asking user for input
            opt = getInt(keyboard, "Enter option: " , 11);
                    
            System.out.println("You chose option " + opt);
            switch (opt) {
                case 1: {
                    System.out.println("");
                    System.out.println("Creating customer");
                    createCustomer(keyboard, model);
                    break;
                }
                case 2: {
                    System.out.println("");
                    System.out.println("Deleting customer");
                    deleteCustomer(keyboard, model);
                    break;
                }
                case 3: {
                    System.out.println("");
                    System.out.println("Editing customer");
                    editCustomer(keyboard, model);
                    break;
                }
                case 4: {
                    System.out.println("");
                    System.out.println("Viewing customer");
                    viewCustomers(model);
                    break;
                }   
                case 5: {
                    System.out.println("");
                    System.out.println("View single customer");
                    viewCustomer(keyboard,model);
                    break;
                }
                case 6: {
                    System.out.println("");
                    System.out.println("Creating branch");
                    createBranch(keyboard, model);
                    break;
                }
                case 7: {
                    System.out.println("");
                    System.out.println("Deleting branch");
                    deleteBranch(keyboard, model);
                    break;
                }
                case 8: {
                    System.out.println("");
                    System.out.println("Editing branch");
                    editBranch(keyboard, model);
                    break;
                }
                case 9: {
                    System.out.println("");
                    System.out.println("Viewing branch");
                    viewBranches(model);
                    break;
                }
                case 10: {
                    System.out.println("");
                    System.out.println("View single branch");
                    viewBranch(keyboard, model);
                    break;
                }
            }
        }
        catch (DataAccessException e){
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println(); 
        }
    }
        while (opt != 11); //The do loop runs until the user input 9
        System.out.println("Goodbye");
    }
       
    //Methods for creating a customer
    private static void createCustomer(Scanner keyboard, Model mdl) throws DataAccessException {
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
        mobile = getInt(keyb, "Enter mobile: ",0);
        branchID = getInt(keyb, "Enter branchID: ",-1);
        
        Customer c = 
                new Customer(
                        fName, lName, address, mobile, email, branchID);
          
        return c;
    }
    
    //Methods for deleting a customer
    private static void deleteCustomer(Scanner keyboard, Model model)throws DataAccessException {
        int customerID = getInt(keyboard, "Enter the customer ID of the customer to delete:",-1);
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
    private static void editCustomer(Scanner kb, Model m)throws DataAccessException {
        int customerID = getInt(kb, "Enter the customer ID of the customer to delete:",-1);
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

    private static void editCustomerDetails(Scanner keyb, Customer c)throws DataAccessException {
        int customerID;
        String fName, lName, address, email;
        int mobile, branchID;
        String line1 , line2;
        
        fName = getString(keyb, "Enter name[" + c.getFName() + "]:");
        lName = getString(keyb, "Enter surname[" + c.getLName() + "]:");
        address = getString(keyb, "Enter address[" + c.getAddress() + "]:");
        email = getString(keyb, "Enter email[" + c.getEmail() + "]:");
        mobile = getInt(keyb, "Enter mobile[" + c.getMobile() + "]:" , 0);
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
        if (mobile != c.getMobile()){
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
            displayCustomers(customers, mdl);            
        }
        System.out.println();
    }
    
    private static void displayCustomers(List<Customer> customers, Model mdl){
        System.out.printf("%5s %20s %20s %20s %20s %30s %20s\n", "customerID", "fName", "lName", "address", "mobile", "email", "branchID");
            for (Customer c : customers){
                Branch b = mdl.findBranchById(c.getBranchID());
                System.out.printf("%10d %20s %20s %20s %20d %30s %20s\n",
                    c.getCustomerID(),
                    c.getFName(),
                    c.getLName(),
                    c.getAddress(),
                    c.getMobile(),
                    c.getEmail(),
                    (b != null) ? b.getAddress(): "");
            }        
    }
    
    //Method for viewing a single customer
    private static void viewCustomer(Scanner keyboard, Model model)throws DataAccessException {
        int customerID = getInt(keyboard, "Enter the customer ID of the customer to view:",-1);
        Customer c;

        c = model.findCustomerByCustomerID(customerID);
        if (c != null) {
            Branch b = model.findBranchById(c.getBranchID());
            System.out.println("Name        : " + c.getFName());
            System.out.println("Surname     : " + c.getLName());
            System.out.println("Address     : " + c.getAddress());
            System.out.println("Mobile      : " + c.getMobile());
            System.out.println("Email       : " + c.getEmail());
            System.out.println("BranchId    : " + ((b != null) ? b.getAddress(): ""));
        }
        else {
            System.out.println("Customer not found");
        }
    }  
       
    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }
    
    private static void createBranch(Scanner keyboard, Model mdl)throws DataAccessException {
        Branch b = readBranch(keyboard);
            if (mdl.addBranch(b)){
                System.out.println("Branch added to database. ");
                System.out.println("");
            }
            else {
                System.out.println("Branch not added to database");
                System.out.println("");
            }
    }
    
     private static Branch readBranch(Scanner keyb) {
        int branchID;
        String address, branchManager, openingHours;
        int mobile;
        String line;
        
        /*line = getString(keyb, "Enter branchID: ");
        branchID = Integer.parseInt(line);*/
        address = getString(keyb, "Enter address: ");
        mobile = getInt(keyb, "Enter mobile: ",0);
        branchManager = getString(keyb, "Enter name: ");
        openingHours = getString(keyb, "Enter hours: ");
        
        Branch b = new Branch(
                   address, mobile, branchManager, openingHours);
          
        return b;
    }
     
      //Methods for deleting a branch
    private static void deleteBranch(Scanner keyboard, Model model)throws DataAccessException {
        int branchID = getInt(keyboard, "Enter the branch ID of the branch to delete:",-1);
        Branch b;

        b = model.findBranchById(branchID);
        if (b != null) {
            if (model.removeBranch(b)) {
                System.out.println("Branch deleted");
            }
            else {
                System.out.println("Branch not deleted");
            }
        }
        else {
            System.out.println("Branch not found");
        }
    }  
    
    //Methods for editing a branch
    private static void editBranch(Scanner kb, Model m)throws DataAccessException {
        int branchID = getInt(kb, "Enter the branch ID of the branch to edit:",-1);
        Branch b;

        b = m.findBranchById(branchID);
        if (b != null) {
            editBranchDetails(kb, b);
            if (m.updateBranch(b)) {
                System.out.println("Branch updated");
            }
            else {
                System.out.println("Branch not updated");
            }
        }
        else {
            System.out.println("Branch not found");
        }
    }

    private static void editBranchDetails(Scanner keyb, Branch b)throws DataAccessException {
        int branchID;
        String address, branchManager, openingHours;
        int mobile;
        String line1 , line2;
        
       // line1 = getString(keyb, "Enter branchID[" + b.getBranchID() + "]:");
        address = getString(keyb, "Enter address[" + b.getAddress() + "]:");
        mobile = getInt(keyb, "Enter mobile[" + b.getMobile() + "]:",0);
        branchManager = getString(keyb, "Enter branch manager[" + b.getBranchManager() + "]:");
        openingHours = getString(keyb, "Enter opening hours[" + b.getOpeningHours() + "]:");
        
        /*if (line1.length() != 0){
            branchID = Integer.parseInt(line1);
            b.setBranchID(branchID);
        }*/
        if (address.length() != 0){
            b.setAddress(address);
        }
        if (mobile != b.getMobile()){
            b.setMobile(mobile);
        }
        if (branchManager.length() != 0){
            b.setBranchManager(branchManager);
        }
        if (openingHours.length() != 0){
            b.setOpeningHours(openingHours);
        }
    }
    
    private static void viewBranches(Model mdl){
        List<Branch> branches = mdl.getBranches();
        System.out.println();
        if (branches.isEmpty()){
            System.out.println("There are no branches in the database");
        }
        else {
            System.out.printf("%10s %20s %20s %20s %20s\n", "branchID", "address", "mobile", "branchManager", "openingHours");
            for (Branch b : branches){
                System.out.printf("%10d %20s %20s %20s %20s8\n",
                    b.getBranchID(),
                    b.getAddress(),
                    b.getMobile(),
                    b.getBranchManager(),
                    b.getOpeningHours());
            }
        }
        System.out.println();
    }
    
    //Method for viewing a single customer
    private static void viewBranch(Scanner keyboard, Model model)throws DataAccessException {
        int branchID = getInt(keyboard, "Enter the Branch ID of the branch to view:",-1);
        Branch b;

         b = model.findBranchById(branchID);
        if (b != null) {
            System.out.println("BranchID         : " + b.getBranchID());
            System.out.println("Address         : " + b.getAddress());
            System.out.println("Mobile          : " + b.getMobile());
            System.out.println("Branch Manager  : " + b.getBranchManager());
            System.out.println("Opening Hours   : " + b.getOpeningHours());
        
            
            List<Customer> customerList = model.getCustomersByBranchID(b.getBranchID());
            System.out.println();
            if(customerList.isEmpty()){
                System.out.println("This branch manages no customers");
            }
            else {
                System.out.println("This branch manages the following customers");
                System.out.println("");
                displayCustomers(customerList, model);
            }
            System.out.println();
        }
        else {
            System.out.println("Branch not found");
        }
    }  
    
    private static int getInt(Scanner keyb, String prompt, int defaultValue){
        int opt = defaultValue;
        boolean finished = false;
        
        do {
            try {
                System.out.print(prompt);
                String line = keyb.nextLine();
                if (line.length() > 0){
                    opt = Integer.parseInt(line);
                }
                finished = true;
            } 
            catch (NumberFormatException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        while (!finished);
        
        return opt;
    }
}