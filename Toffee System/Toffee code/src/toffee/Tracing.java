/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toffee;

/**
 *
 * @author Seif
 */
import java.util.Random;
import java.util.Scanner;
public class Tracing {
    private String d_name;
    private int d_id;
    private String d_phone_number;
    private boolean delivered = false;    

    public String getD_name() {
        setD_name();
        return d_name;
    }

    public void setD_name() {        
        this.d_name = "Seif";
    }

    public int getD_id() {
        setD_id();
        return d_id;
    }

    // Here we generate a random delivery id that contains only 4 digits
    public void setD_id() {
        Random rand = new Random();
        this.d_id = rand.nextInt(2000) + 100;        
    }

    public String getD_phone_number() {
        setD_phone_number();
        return d_phone_number;
    }

    public void setD_phone_number() {
        this.d_phone_number = "0111345678";
    }
    
    public boolean order_delivered()
    {        
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while(!delivered)
        {          
            System.out.println(" _______________________________________");
            System.out.println("|      *Delivery Screen*                |");
            System.out.println("|      -----------------                |");
            System.out.println(  "|  -Delivery name: " + getD_name() + "                 |");
            System.out.println(  "|  -Delivery ID: " + getD_id() + "                    |");
            System.out.println(  "|  -Delivery Phone number: " + getD_phone_number() + "   |");
            System.out.println("|                                       |");
            System.out.println("|  Order has been delivered?            |");
            System.out.println("|          1- Yes                       |");
            System.out.println("|          2- No                        |");
            System.out.println("|                                       |");
            System.out.println("|_______________________________________|");                                
            choice = input.nextInt();        
            switch(choice)
            {
                case 1:                    
                    delivered = true;
                    return delivered;
                case 2:
                    System.out.println("Delivering.......");
                    break;                
            }
        }
        return false;
    }                
}
