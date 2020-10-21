package org.sda.banking_app.start_menu;

import static org.sda.banking_app.account_menu.AccountMenu.loadAccountMenu;
import static org.sda.banking_app.account_menu.AccountMenu.setActiveUser;

import org.sda.banking_app.types.ClientDataDao;

import java.util.Scanner;

public class Login {

    public static void login() {
        Scanner input1 = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        String username;
        String password;
        while (true) {
            System.out.print("\nUsername: ");
            username = input1.nextLine();
            System.out.print("Password: ");
            password = input2.nextLine();
            if (ClientDataDao.checkCredentials(username, password)) {
                System.out.println("\033[1;36mLogin successful!\033[0m");
                break;
            } else {
                System.out.println("\033[1;31mUsername or password incorrect.\033[0m");
            }
        }
        setActiveUser(username);
        loadAccountMenu();
    }

    private Login(){
        throw new IllegalStateException();
    }

}
