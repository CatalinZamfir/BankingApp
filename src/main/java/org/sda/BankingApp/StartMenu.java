package org.sda.BankingApp;

import org.sda.BankingApp.types.ClientData;
import org.sda.BankingApp.types.ClientDataDao;

import java.util.Scanner;

public class StartMenu {


    public static void loadStartMenu() {
        System.out.print("\nWelcome to\n");
        System.out.print("\033[1;93mC B H   B a n k\033[0m\n\n");
        System.out.println("[L] LOGIN");
        System.out.println("[R] REGISTER\n");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Choice: ");
            String choice = input.nextLine();
            choice = choice.toUpperCase();
            if (choice.equals("L")) {
                login();
                break;
            } else if (choice.equals("R")) {
                register();
                break;
            }
        }
    }

    public static void login() {
    }

    public static void register() {
        ClientData clientData = new ClientData();
        Scanner input1 = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter a new Username: ");
            String username = input1.nextLine();
            clientData.setUsername(username);
            break;
        }
        Scanner input2 = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter a password: ");
            String password = input2.nextLine();
            clientData.setPassword(password);
            break;
        }
        Scanner input3 = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter your First Name: ");
            String firstName = input3.nextLine();
            clientData.setFirstName(firstName);
            break;
        }
        Scanner input4 = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter your Last Name: ");
            String lastName = input4.nextLine();
            clientData.setLastName(lastName);
            break;
        }
        Scanner input5 = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter your CNP: ");
            long cnp = input5.nextLong();
            clientData.setCnp(cnp);
            break;
        }
        Scanner input6 = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter your email address: ");
            String email = input6.nextLine();
            clientData.setEmail(email);
            break;
        }
        System.out.println(clientData);
        ClientDataDao.createNewClient(clientData);
    }
}
