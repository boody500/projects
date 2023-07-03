/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toffee;

/**
 *
 * @author Seif
*/

import java.sql.*;
import java.util.Scanner;
public class Account {
    private String Name;
    private String Email;
    private String Password;
    private String Phone_Number;
    private String Address;
    private int Loyalty_Points;
    private char Vouchers;
    private float Voucher_Value;
    //////////////////////////////

    // function that takes input from users  in regestration  and calling this function  to save all info in database
    public boolean create_account(String Name,String Email, String Password,String Phone_Number,String Address)
    {
        Scanner input = new Scanner (System.in);  
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        this.Phone_Number = Phone_Number;
        this.Address = Address;

        // url of database server
        String url = "jdbc:sqlserver://SEIFELDEEN:1433;databaseName=Toffee;user=sa;password=sa123456;encrypt=false;";

        try {
            // connecting database of the url to this program
            Connection con = DriverManager.getConnection(url);

            //a string of the query of adding info to database
            String sql = "INSERT INTO Data_Of_Registration (Name, email, password,phone_number, address) VALUES (?, ?, ?, ?, ?)";

            // convert sql string to a qury in database
            PreparedStatement statement = con.prepareStatement(sql);

            // setting inputs of user in query according to it's place (numbers)
            statement.setString(1, Name);
            statement.setString(2, Email);
            statement.setString(3, Password);
            statement.setString(4, Phone_Number);
            statement.setString(5, Address);

            // checking if query is excutable or not
            int rowsInserted = statement.executeUpdate();

            //if query executed succssesfully
            if (rowsInserted > 0)
            {
                System.out.println("Thank You For Your Registration In Our Toffee Application.You Are Now One Of Our Family Members."
                        + "We are So Glad You were Here!,We Will Do Our Best To Notify You About Our New Updates.");
                return true;
            } 
            else            // if not executed succssesfully
            {
                System.out.println("Registration failed.");
                return false;
            }
        }
        catch (SQLException e)                      // if any errors occures in connecting to database
        {
            //System.out.println("Error connecting to database: " + e.getMessage());
            return false;
        }        
    }
    
    
}
