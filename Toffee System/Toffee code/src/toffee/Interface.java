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
public class Interface 
{
    public void user_menu()
    {        
        General_User g = new General_User();               
        Scanner input = new Scanner (System.in);        
        int choice;
        boolean s = true;
        while(s)
        {
            System.out.println("*********************************************************************************************************************************");
            System.out.println("\t\t\t\t\t\t** Welcome to Toffee Website **");
            System.out.println("*********************************************************************************************************************************");
            System.out.println("Please select an option:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Catalog");
            System.out.println("4. Exit");
            System.out.print("> ");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    if(g.Register())
                    {
                        System.out.println("_______________");
                        System.out.println("|Try to log in|");
                        System.out.println("_______________");
                        s = false;
                    }
                    else
                    {
                        System.out.println("Account cannot be created as the email address found used before");
                        break;
                    }
                case 2:                    
                    while(!g.Login())
                    {                        
                        System.out.println("Try again");
                    }
                    g.View_Catalog();
                    s = false;
                    break;
                case 3:
                    g.View_Catalog();
                    s = false;
                    break;
                case 4:
                    System.out.println("Good Bye, please visit us again :)");
                    exit(0);
                default:
                    System.out.println("Wrong input, plz try again");                    
                    s = true;
                    break;
            }
        }
    }
}
