package org.sda.banking_app.start_menu;

import org.sda.banking_app.account_menu.AccountMenu;
import org.sda.banking_app.types.ClientData;
import org.sda.banking_app.types.ClientDataDao;

import java.util.Scanner;

public class Login {

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
        AccountMenu.loadAccountMenu(user);
    }

}
