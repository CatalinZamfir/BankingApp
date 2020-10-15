package org.sda.banking_app.account_menu;

import static org.sda.banking_app.account_menu.AccountMenu.goBackToAccountMenu;
import static org.sda.banking_app.types.AccountDao.checkForAccount;
import static org.sda.banking_app.types.AccountDao.getAccountCurrency;
import static org.sda.banking_app.types.BankTransactionDao.*;

import org.sda.banking_app.types.Account;
import org.sda.banking_app.types.BankTransaction;
import org.sda.banking_app.types.enums.Currency;
import org.sda.banking_app.types.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MakeTransaction {

    protected static List<Account> accountList;
    protected static int accountIndex;

    protected static final double RON_EUR = 0.205096;
    protected static final double RON_USD = 0.240900;
    protected static final double EUR_USD = 1.174700;

    public static void makeOutboundTransaction(List<Account> accounts) {
        accountList = accounts;
        System.out.print("\nPlease input the account (index) you would like to make a transfer from.\n");
        Scanner input1 = new Scanner(System.in);
        while (true) {
            System.out.print("\nInput: ");
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
        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setTransactionType(TransactionType.OUTBOUND);
        bankTransaction.setAccountNo(accountList.get(accountIndex - 1).getAccountNo());
        bankTransaction.setTransferAmount(transferAmount);
        bankTransaction.setForeignAccount(IBAN);
        bankTransaction.setDateAndTime(LocalDateTime.now());
        if (createNewTransaction(bankTransaction)) {
            makeInboundTransaction(bankTransaction);
            System.out.print("\n\033[0;36mTransaction was successful.\033[0m\n");
        } else {
            System.out.println("\033[0;31mTransaction was unsuccessful.\033[0m");
        }
        goBackToAccountMenu();
    }

    public static void makeInboundTransaction(BankTransaction bankTransaction) {
        if (bankTransaction.getForeignAccount().startsWith("RO04GRUP00009999")) {
            String accNoTemp = bankTransaction.getForeignAccount().substring(16);
            int accountNo = Integer.parseInt(accNoTemp);
            if (checkForAccount(accountNo)) {
                BankTransaction bankTransactionInbound = new BankTransaction();
                bankTransactionInbound.setTransactionType(TransactionType.INBOUND);
                bankTransactionInbound.setAccountNo(accountNo);
                bankTransactionInbound.setTransferAmount(convertCurrency(accountNo, bankTransaction.getTransferAmount()));
                bankTransactionInbound.setForeignAccount("RO04GRUP00009999" + accountList.get(accountIndex - 1).getAccountNo());
                bankTransactionInbound.setDateAndTime(LocalDateTime.now());
                createNewTransaction(bankTransactionInbound);
            }
        }
    }

    public static double convertCurrency(int accountNo, double transferAmount) {
        Currency receiver = getAccountCurrency(accountNo);
        Currency sender = accountList.get(accountIndex - 1).getCurrency();
        double convertedAmount = transferAmount;
        String info = "\n\033[0;33mAttention! Receiver and sender accounts have different currencies!\n";
        if (receiver == Currency.RON && sender == Currency.EUR) {
            convertedAmount /= RON_EUR;
            System.out.printf("%s%.2f EUR will be converted to %.2f RON at a rate of %f RON/EUR.", info, transferAmount, convertedAmount, 1 / RON_EUR);
        } else if (receiver == Currency.EUR && sender == Currency.RON) {
            convertedAmount *= RON_EUR;
            System.out.printf("%s%.2f RON will be converted to %.2f EUR at a rate of %f EUR/RON.", info, transferAmount, convertedAmount, RON_EUR);
        } else if (receiver == Currency.RON && sender == Currency.USD) {
            convertedAmount /= RON_USD;
            System.out.printf("%s%.2f USD will be converted to %.2f RON at a rate of %f RON/USD.", info, transferAmount, convertedAmount, 1 / RON_USD);
        } else if (receiver == Currency.USD && sender == Currency.RON) {
            convertedAmount *= RON_USD;
            System.out.printf("%s%.2f RON will be converted to %.2f USD at a rate of %f USD/RON.", info, transferAmount, convertedAmount, RON_USD);
        } else if (receiver == Currency.EUR && sender == Currency.USD) {
            convertedAmount /= EUR_USD;
            System.out.printf("%s%.2f USD will be converted to %.2f EUR at a rate of %f EUR/USD.", info, transferAmount, convertedAmount, 1 / EUR_USD);
        } else if (receiver == Currency.USD && sender == Currency.EUR) {
            convertedAmount *= EUR_USD;
            System.out.printf("%s%.2f EUR will be converted to %.2f USD at a rate of %f USD/EUR.", info, transferAmount, convertedAmount, EUR_USD);
        }
        System.out.print("\n\u001B[0m");
        return convertedAmount;
    }

}
