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

public class Payment {
    Scanner input = new Scanner(System.in);
    private int order_id;
    
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    
        
    public void Cash_On_Delivery()
    {
        // Here we ask the user to enter the email and address 
        // Then, we ask the user to verify his email by an OTP code that will be sent to his email
        // if the user entered a wrong OTP code, then a new one will be sent until the user enter a valid code
        Order o1 = new Order();        
        OTP o = new OTP();
        int code;
        String email,address;
        System.out.println("Enter your address");
        address = input.nextLine();
        System.out.print("Enter you email:\n> ");
        email = input.next();
        System.out.println("Check your email the verfication code....");                               
        
        boolean isVerified = false;    
        while (!isVerified) {
            int otp = o.generateOTP();
            
            o.sendOTP(email, otp);
            System.out.print("Enter the verification code:\n> ");
            code = input.nextInt();

            if (code == otp) {
                isVerified = true;
                System.out.println("Email has been verfied sucessfully!");
                System.out.println("Order ID: " + o1.getId());
            } else {
                System.out.println("Invalid code. Sending another verification code.");
            }
        }
    }   
    
    
}