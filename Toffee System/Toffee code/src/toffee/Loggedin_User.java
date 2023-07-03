    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toffee;

/**
 *
 * @author Seif
 */
import static java.lang.System.exit;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class Loggedin_User extends General_User {
    float tp = 0;
    String url = "jdbc:sqlserver://SEIFELDEEN:1433;databaseName=Toffee;user=sa;password=sa123456;encrypt=false;";
    Scanner input = new Scanner (System.in);    
    Vector<Integer> v = new Vector<Integer>();  // storing items' ids 
    Vector<Integer> q = new Vector<Integer>();  // storing quantity of each id
    public void Select_Item(int id)                     
    {
        int quantity = 0;
        try {
            Connection con = DriverManager.getConnection(url);            
            String sql = "SELECT price FROM items WHERE id = ?";             
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery(); 
            if (result.next()) 
            {
                boolean found = false;
                
                // looping on items to check if the item found before or not
                // if the item found before , the new quantity will be added to the origin
                for(int i = 0; i < v.size();i++)
                {
                    if (id == v.get(i))
                    {                        
                        System.out.print("Item found before,Enter the quantity you need to add to it:\n> ");
                        quantity = input.nextInt();
                        int replacement = q.get(i) + quantity;
                        q.set(i, replacement);
                        found = true;
                    }
                }
                
                // if the id not found before in the cart , the id will be added to the vector 
                if(!found)
                {
                    v.add(id);
                    System.out.print("Enter the quantity you need:\n> ");
                    quantity = input.nextInt();
                    q.add(quantity);
                }                                    
                System.out.print("Price: " + result.getFloat("price") * quantity);
                System.out.println("\n");
            } 
            
            // checking if the id that the user selected is found or not in the catalog
            else 
            {
                System.out.println("Item not found, please enter valid ID\n");                        
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("Error connecting to database: " + e.getMessage());
        }                        
    }   
    //------------------------------------------------------------------------------------------
    public void Edit_quantity(int id) 
    {                             
        int new_quantity= 0;
        int choice = 0;        
        boolean action = true;
        boolean found = false;

        // checking if item found in cart before
        for (int i = 0; i < v.size(); i++)
            {
                if(v.get(i) == id)
                {                    
                    found = true;
                }                    
            }
        
        // if the item id found in the cart, display the 2 options (Remove the item from cart, Edit quantity)
        if (found)
        {
            System.out.print("1- Remove the item from cart\n2- Edit quantity\n");
            choice = input.nextInt();
            while(action)
            {
                switch(choice)
                {
                    case 1:
                        for (int i = 0; i < v.size(); i++)
                        {
                            if(v.get(i) == id)
                            {
                                v.remove(i);
                                q.remove(i);
                                action = false;
                                break;
                            }                    
                        }                        
                        break;
                        
                    case 2:
                        for (int i = 0; i < v.size(); i++)
                        {
                            if(v.get(i) == id)
                            {
                                try 
                                {
                                    // Here in this block we connect to database to get the price of the item id
                                    // Then, we multiplie the price from database by the new quantity that the user selected
                                    Connection con = DriverManager.getConnection(url);            
                                    String sql = "SELECT price FROM items WHERE id = ?";             
                                    PreparedStatement statement = con.prepareStatement(sql);
                                    statement.setInt(1, id);
                                    ResultSet result = statement.executeQuery(); 
                                    if (result.next()) 
                                    {                
                                        System.out.print("Enter the new quantity you want\n> ");
                                        new_quantity = input.nextInt();
                                        q.set(i, new_quantity);
                                        System.out.println("New price: " + new_quantity * result.getFloat("price"));
                                        break;
                                    } 
                                    else 
                                    {
                                        System.out.println("Item not found, please enter valid ID\n");                        
                                    }
                                } 
                                catch (SQLException e) 
                                {
                                    System.out.println("Error connecting to database: " + e.getMessage());
                                }                                
                                break;
                            }                    
                        }
                        action = false;
                        break;
                    default:
                        System.out.println("Wrong input, try again\n");

                }
            }
        }
        else
        {
            System.out.println("The item not found in you cart \n");
        }
    }
    //-------------------------------------------------------------------------------------------------------------    
    public void View_Cart() {
        Order o = new Order();
        System.out.println(" _________________________________________________________________________________________");
        System.out.println("|                                                                                         |");
        System.out.println("|                                        *Your Cart*                                      |");
        System.out.println("|                                                                                         |");                                        
    try (Connection con = DriverManager.getConnection(url)) {
        
        // checking if the cart is empty or not
        if(!v.isEmpty())
        {
            System.out.printf("%-10s %-20s %-10s %-10s\n", "|Item ID", "Name", "Price", "Quantity                                       |");
            System.out.printf("%-10s %-20s %-10s %-10s\n", "|=======", "====", "=====", "=========                                      |");
            
            // Looping on items selected before to print them with price multiplied with quantity
            // and print them in the cart
            for (int i = 0; i < v.size(); i++) {
                int id = v.get(i);
                String sql = "SELECT * FROM items WHERE id = ?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    String name = result.getString("name");
                    float price = result.getFloat("price");
                    System.out.print("|");
                    System.out.printf("%-10d %-20s %-10.2f %-10d %-20s\n", id, name, price * q.get(i), q.get(i), "                                   |");                    
                } else {
                    System.out.println("Item not found, please enter valid ID\n");
                }
            }
        }
        else
        {
            System.out.println("|                                    Your cart is empty                                   |");            
        }
        System.out.println("|_________________________________________________________________________________________|");
    } catch (SQLException e) {
        System.out.println("Error connecting to database: " + e.getMessage());
    }
    
        // After viewing cart and not empty , we display 2 options for the user
        // (Continue Shopping , Place Order)
        if(!v.isEmpty())
        {
            int choice = 0;         
            System.out.println("1- Continue Shopping\n2- Place Order\n");
            choice = input.nextInt();
            switch(choice)
            {            
                case 1:
                    View_Catalog();
                    break;
                case 2:                
                    tp = o.place_order(v, q);
                    System.out.println("1- Checkout\n2- View Catalog\n");                
                    int action = 0;
                    action = input.nextInt();
                    switch(action)
                    {
                        case 1:
                            Checkout();
                            break;
                        case 2:
                            View_Catalog();
                            break;
                        default:
                            System.out.println("Enter a valid choice");
                    }
                    break;

                default:
                    System.out.println("Enter a valid choice");            
            }
        }
    
    
}
    //-------------------------------------------------------------------------------------------------------------
    public boolean Checkout()
    {
        // Here we display the payment methods        
        Tracing t = new Tracing();
        Payment p = new Payment();                             
        Order o = new Order();        
        System.out.println("Choose Payment method:");
        System.out.println("1- Cash on delievery\n2- Gift Card\n3- eWallet\n4- Loyality Points");
        int choice = 0; 
        choice = input.nextInt();
        boolean flag = true;
        while(flag)
        {
            switch(choice)
            {
                case 1:                
                    p.setOrder_id(o.getId());
                    p.Cash_On_Delivery();
                    t.order_delivered();
                    System.out.println("TO OUR FAVOURITE CLIENT:)...\nThank you for ordering from our website. We hope to live up to your expectations.");
                    exit(0);                    
                case 2:
                    System.out.println("Gift Card Method");
                    flag = false;
                    break;
                case 3:
                    System.out.println("eWallet Method");
                    flag = false;
                    break;
                case 4:
                    System.out.println("Loyality Points Points");
                    flag = false;
                    break;
                default:
                    System.out.println("Enter a valid choice :(");                    
            }
        }
        return false;
    }

}