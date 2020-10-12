package org.sda.BankingApp;

import org.sda.BankingApp.types.ClientData;
import org.sda.BankingApp.types.ClientDataDao;

import java.util.Scanner;

public class StartMenu {


    public static void loadStartMenu() {
//        System.out.print("\nWelcome to\n");
        System.out.print("\n\u001B[7m\033[1;33mWELCOME TO CBH Bank      \u001B[0m\n");
        System.out.print("\u001B[7m\033[1;36mThe bank you can trust   \u001B[0m\n\n");
        System.out.println("[L] Login");
        System.out.println("[R] Register\n");
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
        Scanner input2 = new Scanner(System.in);
        String user;
        String pass;
        while (true) {
            System.out.print("\nUsername: ");
            user = input1.nextLine();
            System.out.print("Password: ");
            pass = input2.nextLine();
            ClientData credentials = ClientDataDao.checkCredentials(user, pass);
            if (credentials != null) {
                System.out.println("\033[0;34mLogin successful!\033[0m");
                break;
            } else {
                System.out.println("\033[0;31mUsername or password incorrect.\033[0m");
            }
        }
    }

    public static void register() {
        ClientData clientData = new ClientData();
        Scanner input1 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter a new Username: ");
            String username = input1.nextLine();
            if (ClientDataDao.findByUsername(username) == null) {
                clientData.setUsername(username);
                System.out.println("\033[0;34mUsername is available.\033[0m");
                break;
            } else {
                System.out.println("\033[0;31mUsername is already taken.\033[0m");
            }
        }
        Scanner input2 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter a password: ");
            String password = input2.nextLine();
            if (password.length() >= 5) {
                clientData.setPassword(password);
                break;
            } else {
                System.out.println("\033[0;31mPassword has to be at least 5 characters long.\033[0m");
            }
        }
        Scanner input3 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter your First Name: ");
            String firstName = input3.nextLine();
            clientData.setFirstName(firstName);
            break;
        }
        Scanner input4 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter your Last Name: ");
            String lastName = input4.nextLine();
            clientData.setLastName(lastName);
            break;
        }
        Scanner input5 = new Scanner(System.in);
        Long cnp;
        while (true) {
            System.out.print("\nPlease enter your CNP: ");
            try {
                cnp = input5.nextLong();
                int cnpLength = String.valueOf(cnp).length();
                if (cnpLength == 13 && (String.valueOf(cnp).startsWith("1") || String.valueOf(cnp).startsWith("2"))) {
                    clientData.setCnp(cnp);
                    break;
                } else {
                    System.out.println("\033[0;31mPlease enter a valid CNP.\033[0m");
                }
            } catch (java.util.InputMismatchException e) {
                input5.nextLine();
                System.out.println("\033[0;31mPlease enter a valid CNP.\033[0m");
            }
        }
        Scanner input6 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter your email address: ");
            String email = input6.nextLine();
            clientData.setEmail(email);
            break;
        }
        ClientDataDao.createNewClient(clientData);
        System.out.println("\n\033[0;34mNew user account created successfully! Please login with your new credentials.\033[0m");
        login();
    }
}
