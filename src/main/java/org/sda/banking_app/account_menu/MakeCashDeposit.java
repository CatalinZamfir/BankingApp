package org.sda.banking_app.account_menu;

import org.sda.banking_app.types.Account;
import org.sda.banking_app.types.BankTransaction;
import org.sda.banking_app.types.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static org.sda.banking_app.account_menu.AccountMenu.*;
import static org.sda.banking_app.types.BankTransactionDao.createNewTransaction;

public class MakeCashDeposit {

    public static void makeCashDeposit(List<Account> accounts) {
        accountList = accounts;
        if (accounts.isEmpty()){
            System.out.println("\n\033[0;31mYou do not have any active accounts. Please create an account first.\033[0m");
        }
        else {
            System.out.print("\nPlease select the account (index) which you would like to replenish.\n");
            getIndexSelection();
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
            BankTransaction bankTransaction = new BankTransaction();
            bankTransaction.setTransactionType(TransactionType.INBOUND);
            bankTransaction.setAccountNo(accountList.get(accountIndex - 1).getAccountNo());
            bankTransaction.setTransferAmount(transferAmount);
            bankTransaction.setForeignAccount("Cash Deposit");
            bankTransaction.setDateAndTime(LocalDateTime.now());
            if (createNewTransaction(bankTransaction)) {
                System.out.print("\n\033[0;36mTransaction was successful.\033[0m\n");
            } else {
                System.out.println("\033[0;31mTransaction was unsuccessful.\033[0m");
            }
        }
        goBackToAccountMenu();
    }

}

