package org.sda.BankingApp;

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
    }

}
