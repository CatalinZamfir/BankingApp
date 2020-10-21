package org.sda.banking_app.start_menu;

import org.sda.banking_app.types.ClientData;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.sda.banking_app.start_menu.Login.login;
import static org.sda.banking_app.start_menu.StartMenu.loadStartMenu;
import static org.sda.banking_app.types.ClientDataDao.*;

public class Register {

    public static void register() {
        ClientData clientData = new ClientData();
        clientData.setUsername(registerUsername());
        clientData.setPassword(registerPassword());
        clientData.setFirstName(registerFirstName());
        clientData.setLastName(registerLastName());
        clientData.setCnp(registerCNP());
        clientData.setEmail(registerEmail());
        if (createNewClient(clientData)) {
            System.out.println("\n\033[1;36mNew user account created successfully! Please login with your new credentials.\033[0m");
            login();
        } else {
            System.out.println("\033[0;31mThere was a problem with the account creation. Please try again.\033[0m");
            loadStartMenu();
        }
    }

    private static String registerUsername() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease choose a new username: ");
            String username = input.nextLine();
            if (!username.isEmpty()) {
                if (!checkForUsername(username)) {
                    System.out.println("\033[1;36mUsername is available.\033[0m");
                    return username;
                } else {
                    System.out.println("\033[0;31mUsername is already taken.\033[0m");
                }
            }
        }
    }

    private static String registerPassword() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease choose a password: ");
            String password = input.nextLine();
            if (checkPasswordCriteria(password)) {
                return password;
            } else {
                System.out.println("\033[0;31mPassword must be at least 8 characters long and must contain at least one digit, one lowercase letter, one uppercase letter, and one special character.\033[0m");
            }
        }
    }

    private static String registerFirstName() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter your first name: ");
            String firstName = input.nextLine();
            if (firstName.length() <= 30 && !firstName.isEmpty()) {
                return firstName;
            } else if (firstName.length() > 30) {
                System.out.println("\033[0;31mFirst name must not be over 30 characters long.\033[0m");
            }
        }
    }

    private static String registerLastName() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter your last name: ");
            String lastName = input.nextLine();
            if (lastName.length() <= 30 && !lastName.isEmpty()) {
                return lastName;
            } else if (lastName.length() > 30) {
                System.out.println("\033[0;31mLast name must not be over 30 characters long.\033[0m");
            }
        }
    }

    private static Long registerCNP() {
        Scanner input = new Scanner(System.in);
        Long cnp;
        while (true) {
            System.out.print("\nPlease enter your CNP: ");
            try {
                cnp = input.nextLong();
                int cnpLength = String.valueOf(cnp).length();
                if (cnpLength == 13 && (!String.valueOf(cnp).startsWith("9")) && !findByCNP(cnp)) {
                    return cnp;
                } else {
                    if (findByCNP(cnp)) {
                        System.out.println("\033[0;31mCNP is already in use.\033[0m");
                    } else {
                        System.out.println("\033[0;31mCNP is invalid. Please enter a valid CNP.\033[0m");
                    }
                }
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.println("\033[0;31mPlease enter a valid CNP.\033[0m");
            }
        }
    }

    private static String registerEmail() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter your email address: ");
            String email = input.nextLine();
            if (checkEmailCriteria(email) && !findByEmail(email)) {
                return email;
            } else if (!checkEmailCriteria(email)) {
                System.out.println("\033[0;31mPlease enter a valid email address.\033[0m");
            } else if (findByEmail(email)) {
                System.out.println("\033[0;31mE-Mail address already in use.\033[0m");
            }
        }
    }

    private static boolean checkPasswordCriteria(String password) {
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

    private static boolean checkEmailCriteria(String email) {
        Matcher valid = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$").matcher(email);
        return valid.matches();
    }

    private Register() {
        throw new IllegalStateException();
    }

}
