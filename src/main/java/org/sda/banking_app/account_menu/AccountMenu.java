package org.sda.banking_app.account_menu;

import static org.sda.banking_app.account_menu.CreateAccount.createAccount;
import static org.sda.banking_app.account_menu.MakeCashDeposit.makeCashDeposit;
import static org.sda.banking_app.account_menu.MakeTransaction.makeOutboundTransaction;
import static org.sda.banking_app.account_menu.ViewTransactionHistory.viewTransactionHistory;
import static org.sda.banking_app.start_menu.StartMenu.loadStartMenu;
import static org.sda.banking_app.types.AccountDao.findAccounts;

import org.sda.banking_app.types.Account;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AccountMenu {

    static final String INVALIDMESSAGE = "\033[0;31mInvalid choice.\033[0m\n";
    static String activeUser;

    public static void loadAccountMenu() {
        System.out.printf("\n\n\u001B[7m\033[1;33m ACCOUNT MENU %39s \u001B[0m\n", activeUser);
        List<Account> accountList = findAccounts(activeUser);
        int no = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(date);
        System.out.printf("\n\u001B[7m\033[1;36m Your Accounts%39s \u001B[0m\n", formattedDate);
        if (accountList.isEmpty()) {
            System.out.println("You do not have any open accounts yet.");
        } else {
            for (Account account : accountList) {
                no++;
                System.out.printf("[%d] ", no);
                System.out.printf("RO04GRUP00009999%d ", account.getAccountNo());
                System.out.printf("%-9s", account.getAccountType());
                System.out.printf("%10.2f ", account.getBalance());
                System.out.printf("(%s)", account.getCurrency());
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("[\033[1;33mM\u001B[0m] Make Transaction");
        System.out.println("[\033[1;33mV\u001B[0m] View Transaction History");
        System.out.println("[\033[1;33mC\u001B[0m] Create Account");
        System.out.println("[\033[1;33mD\u001B[0m] Deposit Cash\n");
        System.out.println("[\033[1;33mL\u001B[0m] Log Out\n");
        Scanner input = new Scanner(System.in);
        String choice;
        do {
            System.out.print("Input: ");
            choice = input.nextLine();
            choice = choice.toUpperCase();
            switch (choice) {
                case "M":
                    makeOutboundTransaction(accountList);
                    loadAccountMenu();
                    break;
                case "V":
                    viewTransactionHistory(accountList);
                    break;
                case "C":
                    createAccount();
                    break;
                case "D":
                    makeCashDeposit(accountList);
                    break;
                case "L":
                    System.out.println();
                    setActiveUser(null);
                    loadStartMenu();
                    break;
                default:
                    System.out.println(INVALIDMESSAGE);
                    break;
            }
        }
        while (!(choice.equals("M") || choice.equals("V") || choice.equals("C") || choice.equals("D") || choice.equals("L")));
    }

    public static void setActiveUser(String username) {
        activeUser = username;
    }

    public static void goBackToAccountMenu(){
        System.out.print("\nPress [Enter] to go back to the Account Menu.");
        Scanner input = new Scanner(System.in);
        String enter = input.nextLine();
        loadAccountMenu();
    }

}
