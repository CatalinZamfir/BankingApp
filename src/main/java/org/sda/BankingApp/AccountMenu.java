package org.sda.BankingApp;

import org.sda.BankingApp.types.Account;
import org.sda.BankingApp.types.AccountDao;
import org.sda.BankingApp.types.enums.AccountType;
import org.sda.BankingApp.types.enums.Currency;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class AccountMenu {

    static String loggedInWithUser;

    static final String invalidMessage = "\033[0;31mInvalid choice.\033[0m\n";

    public static void loadAccountMenu() {
        System.out.print("\n\n\u001B[7m\033[1;33m ACCOUNT MENU             \u001B[0m\n\n");
        System.out.println("[V] View Accounts");
        System.out.println("[M] Make Transaction");
        System.out.println("[C] Create Account");
        System.out.println("[D] Deposit Cash\n");
        System.out.println("[L] Log Out\n");
        Scanner input = new Scanner(System.in);
        String choice;
        do {
            System.out.print("Choice: ");
            choice = input.nextLine();
            choice = choice.toUpperCase();
            switch (choice) {
                case "V":
                    viewAccounts();
                    break;
                case "M":
                    // method2();
                    break;
                case "C":
                    createAccount();
                    break;
                case "D":
                    // method4();
                    break;
                case "L":
                    loggedInWithUser = null;
                    System.out.println();
                    StartMenu.loadStartMenu();
                    break;
                default:
                    System.out.println(invalidMessage);
                    break;
            }
        }
        while (!(choice.equals("V") || choice.equals("M") || choice.equals("C") || choice.equals("D") || choice.equals("L")));
    }

    public static void createAccount() {
        System.out.print("\nPLease select the type of the account you would like to open.\n\n");
        System.out.println("[D] Debit   [C] Credit   [S] Savings");
        Scanner input1 = new Scanner(System.in);
        String typeChoice;
        AccountType accountType = null;
        do {
            System.out.print("\nChoice: ");
            typeChoice = input1.nextLine();
            typeChoice = typeChoice.toUpperCase();
            switch (typeChoice) {
                case "D":
                    accountType = AccountType.DEBIT;
                    break;
                case "C":
                    accountType = AccountType.CREDIT;
                    break;
                case "S":
                    accountType = AccountType.SAVINGS;
                    break;
                default:
                    System.out.println(invalidMessage);
                    break;
            }
        }
        while (!(typeChoice.equals("D") || typeChoice.equals("C") || typeChoice.equals("S")));
        System.out.print("\nPLease select the currency.\n\n");
        System.out.println("[R] RON   [E] EUR   [U] USD");
        Scanner input2 = new Scanner(System.in);
        String currencyChoice;
        Currency currency = null;
        do {
            System.out.print("\nChoice: ");
            currencyChoice = input2.nextLine();
            currencyChoice = currencyChoice.toUpperCase();
            switch (currencyChoice) {
                case "R":
                    currency = Currency.RON;
                    break;
                case "E":
                    currency = Currency.EUR;
                    break;
                case "U":
                    currency = Currency.USD;
                    break;
                default:
                    System.out.println(invalidMessage);
                    break;
            }
        }
        while (!(currencyChoice.equals("R") || currencyChoice.equals("E") || currencyChoice.equals("U")));
        Account account = new Account();
        account.setUsername(loggedInWithUser);
        account.setAccountType(accountType);
        account.setCurrency(currency);
        AccountDao.createNewAccount(account);
        System.out.print("\n\033[0;34mYour new account was succesfully created.\033[0m\n");
        loadAccountMenu();
    }

    public static void viewAccounts() {
        List<Account> accountList = AccountDao.findAccounts(loggedInWithUser);
        int no = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(date);
        System.out.printf("\n\u001B[7m\033[1;36m YOUR ACCOUNT'S BALANCES                    %s \u001B[0m\n", formattedDate);
        for (Account account : accountList) {
            no++;
            System.out.printf("[%d] ", no);
            System.out.printf("RO04GRUP00009999%d ", account.getAccountNo());
            System.out.printf("%-10s", account.getAccountType());
            System.out.printf("%10.2f ", account.getBalance());
            System.out.printf("(%s)", account.getCurrency());
            System.out.println();
        }
        System.out.print("\n[B] Back to Account Menu\n\n");
        Scanner input = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.print("Choice: ");
            choice = input.nextLine();
            choice = choice.toUpperCase();
            if (choice.equals("B")) {
                loadAccountMenu();
                break;
            } else {
                System.out.println(invalidMessage);
            }
        }
    }

}
