package org.sda.BankingApp;

import org.sda.BankingApp.types.ClientData;
import org.sda.BankingApp.types.ClientDataDao;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartMenu {

    public static void loadStartMenu() {
        System.out.print("\n\u001B[7m\033[1;33m Welcome to Group4 Bank!                              \033[0m\n");
        System.out.print("\u001B[7m\033[1;36m                                A bank you can trust. \033[0m\n\n");
        System.out.println("[\033[1;33mL\u001B[0m] Login");
        System.out.println("[\033[1;33mR\u001B[0m] Register\n");
        System.out.println("[\033[1;33mE\u001B[0m] Exit\n");
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
            } else if (choice.equals("E")) {
                System.exit(0);
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
        AccountMenu.loggedInWithUser = user;
        AccountMenu.loadAccountMenu();
    }

    public static void register() {
        ClientData clientData = new ClientData();
        Scanner input1 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease choose a new username: ");
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
            System.out.print("\nPlease choose a password: ");
            String password = input2.nextLine();
            if (checkPasswordCriteria(password)) {
                clientData.setPassword(password);
                break;
            } else {
                System.out.println("\033[0;31mPassword must be at least 8 characters long and must contain at least one digit, one lowercase letter, one uppercase letter, and one special character.\033[0m");
            }
        }
        Scanner input3 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter your first name: ");
            String firstName = input3.nextLine();
            if (firstName.length() < 30) {
                clientData.setFirstName(firstName);
                break;
            } else {
                System.out.println("First name must not be over 30 characters long.");
            }
        }
        Scanner input4 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter your last name: ");
            String lastName = input4.nextLine();
            if (lastName.length() < 30) {
                clientData.setLastName(lastName);
                break;
            } else {
                System.out.println("Last name must not be over 30 characters long.");
            }
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
            if (checkEmailCriteria(email)) {
                clientData.setEmail(email);
                break;
            } else {
                System.out.println("\033[0;31mPlease enter a valid email address.\033[0m");
            }
        }
        ClientDataDao.createNewClient(clientData);
        System.out.println("\n\033[0;34mNew user account created successfully! Please login with your new credentials.\033[0m");
        login();
    }

    public static boolean checkPasswordCriteria(String password) {
        if (password.length() >= 8) {
            Matcher hasUppercaseLetter = Pattern.compile("[A-Z]").matcher(password);
            Matcher hasLowercaseLetter = Pattern.compile("[a-z]").matcher(password);
            Matcher hasDigit = Pattern.compile("[0-9]").matcher(password);
            Matcher hasSpecial = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]").matcher(password);
            return hasLowercaseLetter.find() && hasUppercaseLetter.find() && hasDigit.find() && hasSpecial.find();
        } else {
            return false;
        }
    }

    public static boolean checkEmailCriteria(String email) {
        Matcher valid = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$").matcher(email);
        return valid.matches();
    }

}
