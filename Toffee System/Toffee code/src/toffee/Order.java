/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toffee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Random;
import java.time.LocalDate;
/**
 *
 * @author Seif
 */
public class Order {    
    private static int id;
    private float price;    
    private boolean delivered;
    private int quantity;
    
    // Here we generate a random order ID contains only 4 digits
    public void setId() {
        Random rand = new Random();
        this.id = rand.nextInt(4000) + 3000;
    }
    
    
    // Here we display a dynamic date
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public boolean getDelivered() {
        return delivered;
    }

    public void setDelivered() {
        Tracing t = new Tracing();        
        this.delivered = t.order_delivered();
    }
    
    public float getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }
    
    
                
    public float place_order(Vector<Integer>v,Vector<Integer>q)
    {        
        setId();
        String url = "jdbc:sqlserver://SEIFELDEEN:1433;databaseName=Toffee;user=sa;password=sa123456;encrypt=false;";
        float total_price = 0;
        quantity = v.size();
        LocalDate currentDate = getCurrentDate();
        System.out.println("Order Date: " + currentDate);
        System.out.println("Order ID: " + getId());        
        System.out.printf("%-10s %-20s %-10s %-10s\n", "Item ID", "Name", "Price", "Quantity");
        System.out.printf("%-10s %-20s %-10s %-10s\n", "=======", "====", "=====", "========");
                
        try (Connection con = DriverManager.getConnection(url)) {

        // Here we display the reset of the order with the total price
        for (int i = 0; i < v.size(); i++) {            
            int item_id = v.get(i);
            String sql = "SELECT * FROM items WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, item_id);
            ResultSet result = statement.executeQuery();            
            boolean row = result.next();
            
            if (row) {                
                String name = result.getString("name");
                float item_price = result.getFloat("price")* q.get(i);
                
                System.out.printf("%-10d %-20s %-10.2f %-10d\n", item_id, name, item_price , q.get(i));
                total_price += item_price;
            }
        }
            price = total_price;
            System.out.println("Total Price: " + price);
            System.out.println("===================================================");            
    } catch (SQLException e) {
        System.out.println("Error connecting to database: " + e.getMessage());
    }        
        return (price);
    }    
}