package org.sda.BankingApp;

import org.sda.BankingApp.types.Account;
import org.sda.BankingApp.types.AccountDao;
import org.sda.BankingApp.types.enums.AccountType;
import org.sda.BankingApp.types.enums.Currency;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AccountMenu {

    static final String invalidMessage = "\033[0;31mInvalid choice.\033[0m\n";

    public static String loggedInWithUser;
    public static List<Account> accountList;

    public static void loadAccountMenu() {
        System.out.printf("\n\n\u001B[7m\033[1;33m ACCOUNT MENU %39s \u001B[0m\n", loggedInWithUser);
        accountList = AccountDao.findAccounts(loggedInWithUser);
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
            System.out.print("Choice: ");
            choice = input.nextLine();
            choice = choice.toUpperCase();
            switch (choice) {
                case "M":
                    makeTransaction();
                    break;
                case "V":
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
        while (!(choice.equals("M") || choice.equals("V") || choice.equals("C") || choice.equals("D") || choice.equals("L")));
    }

    public static void makeTransaction() {
        System.out.print("\nPlease input the account (index) you would like to make a transfer from.\n");
        Scanner input1 = new Scanner(System.in);
        int accountIndex;
        while (true) {
            System.out.print("\nChoice: ");
            try {
                accountIndex = input1.nextInt();
                if (accountIndex <= accountList.size() && accountIndex > 0) {
                    break;
                } else {
                    System.out.println("\033[0;31mPlease enter a valid index number.\033[0m");
                }
            } catch (java.util.InputMismatchException e) {
                input1.nextLine();
                System.out.println("\033[0;31mPlease enter a valid index number.\033[0m");
            }
        }
        Scanner input2 = new Scanner(System.in);
        String IBAN;
        while (true) {
            System.out.print("\nPlease input the recipient IBAN: ");
            IBAN = input2.nextLine();
            break;
        }
        double transferAmount;
        Scanner input3 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter the amount you would like to transfer: ");
            try {
                transferAmount = input3.nextDouble();
                if (transferAmount <= accountList.get(accountIndex - 1).getBalance() && transferAmount > 0) {
                    break;
                } else {
                    System.out.printf("\033[0;31mPlease enter a valid amount (between 0 and %.02f).\033[0m\n", accountList.get(accountIndex - 1).getBalance());
                }
            } catch (java.util.InputMismatchException e) {
                input3.nextLine();
                System.out.printf("\033[0;31mPlease enter a valid amount (between 0 and %.02f).\033[0m\n", accountList.get(accountIndex - 1).getBalance());
            }
        }
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

}
