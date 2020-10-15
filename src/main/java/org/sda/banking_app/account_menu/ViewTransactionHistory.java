package org.sda.banking_app.account_menu;

import org.sda.banking_app.types.Account;
import org.sda.banking_app.types.BankTransaction;

import static org.sda.banking_app.types.BankTransactionDao.findBankTransactions;

import java.util.List;
import java.util.Scanner;

public class ViewTransactionHistory {

    protected static List<Account> accountList;
    protected static int accountIndex;

    public static void viewTransactionHistory(List<Account> accounts, String loggedInWithUser) {
        accountList = accounts;
        System.out.print("\nPlease input the account (index) you would like to make a transfer from.\n");
        Scanner input1 = new Scanner(System.in);
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
        List<BankTransaction> bankTransactions = findBankTransactions(accountList.get(accountIndex - 1).getAccountNo());
        System.out.printf("\n\u001B[7m\033[1;36m Your Transactions%36s\u001B[0m\n", "RO04GRUP00009999" + accountList.get(accountIndex-1).getAccountNo());
        if (accountList.isEmpty()) {
            System.out.println("You do not have any transaction on this account yet.");
        } else {
            for (BankTransaction bankTransaction : bankTransactions) {
                System.out.printf("[%d] ", bankTransaction.getReferenceId());
                System.out.printf("%s ", bankTransaction.getTransactionType());
                System.out.printf("%s ", bankTransaction.getForeignAccount());
                System.out.printf("%.2f ", bankTransaction.getTransferAmount());
                System.out.printf("%s", bankTransaction.getDateAndTime());
                System.out.println();
            }
        }
        Scanner input = new Scanner(System.in);
        String back = null;
        System.out.println("\n[\033[1;33mB\u001B[0m] Back to account menu \n");
        do {
            System.out.print("Choice: ");
            back = input.nextLine();
            back = back.toUpperCase();
            if(!back.equals("B")){
                System.out.println("\033[0;31mInvalid choice.\033[0m");
            }
        }
         while(!back.equals("B"));
            AccountMenu.loadAccountMenu(loggedInWithUser);
}
}
