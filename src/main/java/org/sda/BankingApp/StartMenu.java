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
        Scanner input1 = new Scanner(System.in);
        String username;
        while (true) {
            System.out.print("Username: ");
            username = input1.nextLine();
            break;
        }
        String password;
        Scanner input2 = new Scanner(System.in);
        while (true) {
            System.out.print("Password: ");
            password = input2.nextLine();
            break;
        }
        ClientData user = ClientDataDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Username or password incorrect.");
        }

    }

    public static void register() {
        ClientData clientData = new ClientData();
        Scanner input1 = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter a new Username: ");
            String username = input1.nextLine();
            if (ClientDataDao.findByUsername(username) == null) {
                clientData.setUsername(username);
                break;
            } else {
                System.out.println("Username is already taken.");
            }
        }
        Scanner input2 = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter a password: ");
            String password = input2.nextLine();
            if (password.length() >= 5) {
                clientData.setPassword(password);
                break;
            } else {
                System.out.println("Password has to be at least 5 characters long.");
            }
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
        Long cnp;
        while (true) {
            System.out.print("Please enter your CNP: ");
            try {
                cnp = input5.nextLong();
                int cnpLength = String.valueOf(cnp).length();
                if (cnpLength == 13 && (String.valueOf(cnp).startsWith("1") || String.valueOf(cnp).startsWith("2"))) {
                    clientData.setCnp(cnp);
                    break;
                } else {
                    System.out.println("Please enter a valid CNP.");
                }
            } catch (java.util.InputMismatchException e) {
                input5.nextLine();
                System.out.println("Please enter a valid CNP.");
            }
        }
        Scanner input6 = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter your email address: ");
            String email = input6.nextLine();
            clientData.setEmail(email);
            break;
        }
        ClientDataDao.createNewClient(clientData);
        System.out.println("\nNew user account created successfully! Please login with your new credentials.\n");
        login();
    }
}
