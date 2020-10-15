package org.sda.banking_app.account_menu;

import org.sda.banking_app.types.Account;
import org.sda.banking_app.types.BankTransaction;
import org.sda.banking_app.types.BankTransactionDao;
import org.sda.banking_app.types.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class DepositCash {
    protected static List<Account> accountList;
    protected static int accountIndex;


    public static void makeDepositCash(List<Account> accounts, String loggedInWithUser) {
        accountList = accounts;

        System.out.print("\nPlease input the account (index) you would like to put the money.\n");
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
        double transferAmount;
        Scanner input3 = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter the amount you would like to transfer: ");
            try {
                transferAmount = input3.nextDouble();
                if (transferAmount > 0) {
                    break;
                } else {
                    System.out.print("\033[0;31mPlease enter a valid amount.\033[0m\n");
                }
            } catch (java.util.InputMismatchException e) {
                input3.nextLine();
                System.out.print("\033[0;31mPlease enter a valid amount.\033[0m\n");
            }
        }
        System.out.print("\n\033[0;36mTransaction was successful.\033[0m\n");
        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setTransactionType(TransactionType.INBOUND);
        bankTransaction.setAccountNo(accountList.get(accountIndex - 1).getAccountNo());
        bankTransaction.setTransferAmount(transferAmount);
        bankTransaction.setForeignAccount("ATM Cash Deposit");
        bankTransaction.setDateAndTime(LocalDateTime.now());
        BankTransactionDao.createNewTransaction(bankTransaction);
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

