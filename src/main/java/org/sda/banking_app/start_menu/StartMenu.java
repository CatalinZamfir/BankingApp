package org.sda.banking_app.start_menu;

import static org.sda.banking_app.start_menu.Login.login;
import static org.sda.banking_app.start_menu.Register.register;

import java.util.Scanner;

public class StartMenu {

    public static void loadStartMenu() {
        System.out.print("\n\u001B[7m\033[1;33m Welcome to Group4 Bank!                              \033[0m\n");
        System.out.print("\u001B[7m\033[1;36m                                A bank you can trust. \033[0m\n\n");
        System.out.println("[\033[1;33mL\u001B[0m] Login");
        System.out.println("[\033[1;33mR\u001B[0m] Register\n");
        System.out.println("[\033[1;33mE\u001B[0m] Exit\n");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Input: ");
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

    private StartMenu(){
        throw new IllegalStateException();
    }
}
