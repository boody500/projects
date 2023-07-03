/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toffee;

/**
 *
 * @author Seif
 */
import java.util.Scanner;
import java.util.regex.*;
import java.sql.*;
public class General_User
{
    // bool to check if user loggen in or not to display options in view catalog
    private boolean isLoggedIn = false;

    // take input from user and check all validations then call create account of account class to save all infos in database
    public boolean Register()
    {
        String name , email,password, phone_number, address;
        Account a = new Account();
        String pass_conf;
        Scanner data = new Scanner (System.in);
        //------------------------------------------------
        System.out.println("Name: "); 
        name = data.nextLine();

        // validations of name
        Pattern regexPattern = Pattern.compile("^[A-z]{3,15}[ ]{0,1}[A-z]{3,14}$");
        Matcher m = regexPattern.matcher(name);
        boolean b = m.matches();

        // if validations is  true
        while (!b)
        {
            System.out.println("Enter valid name: ");
            name = data.nextLine();
            m = regexPattern.matcher(name);
            b = m.matches();
        }
        //------------------------------------------------
        System.out.println("Email: ");
        email = data.next();
        // validations of email
        regexPattern = Pattern.compile("^\\w+([.-]?\\w+)@\\w+([.-]?\\w+)(.\\w{2,3})+$");
        m = regexPattern.matcher(email);
        b = m.matches();

        // if validations is  true
        while (!b)
        {
            System.out.println("Enter valid email: ");
            email = data.next();
            m = regexPattern.matcher(email);
            b = m.matches();
        }
        //-----------------------------------------------------
        System.out.println("Password");
        System.out.println("Minimum eight characters, at least one upper and lower letter, one number and one special character(#,@,&): ");
        password = data.next();

        // validations of Password
        regexPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,15}$");
        m = regexPattern.matcher(password);
        b = m.matches();

        // if validations is  true
        while (!b)
        {
            System.out.println("Enter valid Password: ");
            password = data.next();
            m = regexPattern.matcher(password);
            b = m.matches();
        }

        // take confirm password
        System.out.println("Enter the confirmation password: ");
        pass_conf = data.next();

        // check if both passwords are equal
        while (!pass_conf.equals(password))
        {
            System.out.println("Password doesn't match, please try again: ");            
            pass_conf = data.next();
        }
        //-----------------------------------------------------
        System.out.println("Phone Number: ");        
        phone_number = data.next();

        // validations of phone number
        regexPattern = Pattern.compile("^(01){1}[0-2 | (5)]{1}[0-9]{8}$");
        m = regexPattern.matcher(phone_number);
        b = m.matches();

        // if validations is  true
        while (!b)
        {
            System.out.println("Enter valid Phone Number: ");
            phone_number = data.next();
            m = regexPattern.matcher(phone_number);
            b = m.matches();
        }
        //-----------------------------------------------------
        System.out.println("Address");
        address = data.nextLine();

        // validations of address
        while (address.length() < 10 || address.length() > 30)
        {
            System.out.println("Enter valid address in this form([City], [Street]) ");
            address = data.nextLine();
        }
        //-----------------------------------------------------
        // verification of email by sending OTP to this email
        OTP o = new OTP();
        int code;
        boolean isVerified = false;

        // while email is not verified send a new OTP with different number
        while (!isVerified) {

            // generating an otp and store it in this var
            int otp = o.generateOTP();

            System.out.println("Check your email the verfication code....");

            // sending OTP to email
            o.sendOTP(email, otp);

            // taking  OTP from user
            System.out.print("Enter the verification code:\n> ");
            code = data.nextInt();

            // check if OTP equals code that we store generatingOTP function in it
            if (code == otp) {
                isVerified = true;

                // if code equals to otp and all email is not found before , create account
                if(a.create_account(name , email,password, phone_number, address))
                {
                    System.out.println("Account has been registered sucessfully!");                    
                    return true;
                }
                else
                {                    
                    return false;
                }
            } else {
                System.out.println("Invalid code. Sending another verification code.");
            }           
        }
        //-----------------------------------------------------        
        return true;
    }
//____________________________________________________________________________________________________________________________________________________    
    public boolean Login()
    {           
        Scanner data = new Scanner (System.in);
        System.out.println("Email: ");
        String email = data.next();        
        System.out.println("Password: ");
        String password = data.next();

        // url of database server
        String url = "jdbc:sqlserver://SEIFELDEEN:1433;databaseName=Toffee;user=sa;password=sa123456;encrypt=false;";
        boolean notfound = true;
    try {
            // connecting database of the url to this program
            Connection con = DriverManager.getConnection(url);

            //a string of the query of adding info to database
            String sql = "SELECT * FROM Data_Of_Registration WHERE email = ? AND password = ?";

            // convert sql string to a qury in database
            PreparedStatement statement = con.prepareStatement(sql);

            // setting inputs of user in query according to it's place (numbers)
            statement.setString(1, email);
            statement.setString(2, password);

            // checking if query is excutable or not
            ResultSet result = statement.executeQuery();

            // check if the accout is in database
            if(notfound)
            {                
                if (result.next()) {
                    System.out.println("Loggedin Successfully!");
                    isLoggedIn = true;
//                    notfound = false;
                    return true;
                } 
                else 
                {                    
                    System.out.println("Login failed, invalid email or password!");                                        
                    isLoggedIn = false;
//                    notfound = true;
                    return false;
                }                
            }
    }
    catch (SQLException e)                      // if any errors occures in connecting to database
    {
        System.out.println("Error connecting to database: " + e.getMessage());
        return false;
    }        
        return false;
}
//____________________________________________________________________________________________________________________________________________________
    public void View_Catalog()
    {        
        Scanner input = new Scanner(System.in);
        boolean action = true;
        int choice = 0;
        int id = 0;

        // url of database server
        String url = "jdbc:sqlserver://SEIFELDEEN:1433;databaseName=Toffee;user=sa;password=sa123456;encrypt=false;";

        try
        {
            // connecting database of the url to this program
            Connection con = DriverManager.getConnection(url);

            //a string of the query of adding info to database
            String sql = "SELECT * FROM items";

            // convert sql string to a query in database
            PreparedStatement statement = con.prepareStatement(sql);

            // checking if query is excutable or not
            ResultSet result = statement.executeQuery();

            System.out.printf("%-10s %-20s %-10s %-20s %-30s %-20s %-10s\n", "Item ID", "Name", "Price", "Category", "Description", "Brand", "Discount %");
            System.out.printf("%-10s %-20s %-10s %-20s %-30s %-20s %-10s\n", "=======", "====", "=====", "========", "===========", "=====", "==========");

            // while there are items in database display them
            while(result.next()){
            System.out.printf("%-10s %-20s %-10s %-20s %-30s %-20s %-10s\n", result.getInt("id"), result.getString("name"), result.getFloat("price"), result.getString("category"), result.getString("description"), result.getString("brand"), result.getInt("discount_percentage"));
            }
            System.out.printf("%-10s %-20s %-10s %-20s %-30s %-20s %-10s\n", "=======", "====", "=====", "========", "===========", "=====", "==========");

            //if user loggend in display catalog options
            if (isLoggedIn)
            {
                Loggedin_User l = new Loggedin_User();
                while(action)
                {
                    System.out.println("\n1- Select Item\n2- Edit quantity of a selected item\n3- View Cart\n4- Exit\n");
                    choice = input.nextInt();
                    switch(choice)
                    {
                        case 1:
                            System.out.print("Enter the id of the item you want to select\n> ");
                            id = input.nextInt();
                            l.Select_Item(id);
                            break;
                        case 2:
                            System.out.print("Enter the id of the item you want to edit\n> ");
                            id = input.nextInt();
                            l.Edit_quantity(id);
                            break;
                        case 3:
                            l.View_Cart();
                            break;
                        case 4:
                            System.out.println("Good Bye, please visit us again :)");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Wrong input, plz enter a valid choice\n");
                    }
                }

            }
        }       
        catch (SQLException e) 
        {
        System.out.println("Error connecting to database: " + e.getMessage());    
        }
                
    }
}
