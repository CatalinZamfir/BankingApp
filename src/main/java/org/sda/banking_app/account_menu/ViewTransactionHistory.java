package org.sda.banking_app.account_menu;

import org.sda.banking_app.types.Account;
import org.sda.banking_app.types.AccountDao;
import org.sda.banking_app.types.BankTransaction;
import org.sda.banking_app.types.enums.Currency;
import org.sda.banking_app.types.enums.TransactionType;

import java.util.List;
import java.util.Scanner;

import static org.sda.banking_app.account_menu.AccountMenu.goBackToAccountMenu;
import static org.sda.banking_app.account_menu.AccountMenu.loadAccountMenu;
import static org.sda.banking_app.types.AccountDao.*;
import static org.sda.banking_app.types.BankTransactionDao.findBankTransactions;

public class ViewTransactionHistory {

    public static void viewTransactionHistory(List<Account> accounts) {
        System.out.print("\nPlease select the account (index) of which you would like to see the transaction history.\n");
        Scanner input1 = new Scanner(System.in);
        int accountIndex;
        while (true) {
            System.out.print("\nInput: ");
            try {
                accountIndex = input1.nextInt();
                if (accountIndex <= accounts.size() && accountIndex > 0) {
                    break;
                } else {
                    System.out.println("\033[0;31mPlease enter a valid index number.\033[0m");
                }
            } catch (java.util.InputMismatchException e) {
                input1.nextLine();
                System.out.println("\033[0;31mPlease enter a valid index number.\033[0m");
            }
        }
        printTransactionList(accounts.get(accountIndex - 1).getAccountNo());
    }

    public static void printTransactionList(int accountNo) {
        List<BankTransaction> bankTransactions = findBankTransactions(accountNo);
        Currency currency = getAccountCurrency(accountNo);
        System.out.printf("\n\u001B[7m\033[1;36m Your Transactions%35s \u001B[0m\n", "RO04GRUP00009999" + accountNo);
        if (bankTransactions.isEmpty()) {
            System.out.println("No transactions on this account yet.");
        } else {
            System.out.print("------------------------------------------------------\n");
            for (BankTransaction bankTransaction : bankTransactions) {
                System.out.printf("%-20s%d\n", "Trans. id:", bankTransaction.getReferenceId());
                if (bankTransaction.getTransactionType() == TransactionType.INBOUND) {
                    System.out.printf("%-20s%s\n", "Received from:", bankTransaction.getForeignAccount());
                }else{
                    System.out.printf("%-20s%s\n", "Sent to:", bankTransaction.getForeignAccount());
                }
                if (bankTransaction.getTransactionType() == TransactionType.INBOUND) {
                    System.out.printf("%-20s\033[0;92m+%.2f %s\033[0m\n", "Amount:", bankTransaction.getTransferAmount(), currency);
                } else {
                    System.out.printf("%-20s\033[0;91m-%.2f %s\033[0m\n", "Amount:", bankTransaction.getTransferAmount(), currency);
                }

                System.out.printf("%-20s%s\n", "Date/Time:", bankTransaction.getDateAndTime());
                System.out.print("------------------------------------------------------\n");
            }
            System.out.printf("%-20s%.2f %s\n", "Balance:", AccountDao.getAccountBalance(accountNo), currency);
        }
        goBackToAccountMenu();
    }
}
