package org.sda.banking_app.start_menu;

import static org.sda.banking_app.types.ClientDataDao.*;

import org.sda.banking_app.types.ClientData;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {

    public static void register() {
        ClientData clientData = new ClientData();
        Scanner input1 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease choose a new username: ");
            String username = input1.nextLine();
            if (!findByUsername(username)) {
                clientData.setUsername(username);
                System.out.println("\033[1;36mUsername is available.\033[0m");
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
                System.out.println("\033[0;31mFirst name must not be over 30 characters long.\033[0m");
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
                System.out.println("\033[0;31mLast name must not be over 30 characters long.\033[0m");
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
            if (checkEmailCriteria(email) && !findByEmail(email)) {
                clientData.setEmail(email);
                break;
            } else if (!checkEmailCriteria(email)) {
                System.out.println("\033[0;31mPlease enter a valid email address.\033[0m");
            } else if (!findByEmail(email)){
                System.out.println("\033[0;31mE-Mail address already in use.\033[0m");
            }
        }
        createNewClient(clientData);
        System.out.println("\n\033[1;36mNew user account created successfully! Please login with your new credentials.\033[0m");
        Login.login();
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
