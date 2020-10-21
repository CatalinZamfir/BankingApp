package org.sda.banking_app.account_menu;

import org.sda.banking_app.types.Account;
import org.sda.banking_app.types.BankTransaction;
import org.sda.banking_app.types.ClientDataDao;
import org.sda.banking_app.types.enums.Currency;
import org.sda.banking_app.types.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static org.sda.banking_app.account_menu.AccountMenu.*;
import static org.sda.banking_app.types.AccountDao.checkForAccount;
import static org.sda.banking_app.types.AccountDao.getAccountCurrency;
import static org.sda.banking_app.types.BankTransactionDao.createNewTransaction;

public class MakeTransaction {

    protected static double convertedAmount;

    public static void makeTransaction(List<Account> accounts) {
        accountList = accounts;
        System.out.print("\nPlease input the account (index) you would like to make a transfer from.\n");
        getIndexSelection();
        if (accounts.get(accountIndex - 1).getBalance() == 0) {
            System.out.printf("\n\033[0;31mThe selected account has a balance of 0.00 %s.\033[0m\n", accounts.get(accountIndex - 1).getCurrency());
        } else {
            String iban = getIBAN();
            String name = getName();
            double transferAmount = getTransferAmount();
            BankTransaction bankTransaction = new BankTransaction();
            bankTransaction.setTransactionType(TransactionType.OUTBOUND);
            bankTransaction.setAccountNo(accountList.get(accountIndex - 1).getAccountNo());
            bankTransaction.setTransferAmount(transferAmount);
            bankTransaction.setForeignAccount(iban);
            bankTransaction.setName(name);
            bankTransaction.setDateAndTime(LocalDateTime.now());
            if (bankTransaction.getForeignAccount().startsWith(IBANPREFIX)) {
                compareCurrencies(bankTransaction);
            } else {
                outBoundTransaction(bankTransaction);
            }
        }
        goBackToAccountMenu();
    }

    public static void outBoundTransaction(BankTransaction bankTransaction) {
        if (createNewTransaction(bankTransaction)) {
            inboundTransaction(bankTransaction);
            System.out.print("\n\033[0;36mTransaction was successful.\033[0m\n");
        } else {
            System.out.println("\033[0;31mTransaction was unsuccessful.\033[0m");
        }
    }

    public static void inboundTransaction(BankTransaction bankTransaction) {
        if (bankTransaction.getForeignAccount().startsWith(IBANPREFIX)) {
            String accNoTemp = bankTransaction.getForeignAccount().substring(16);
            int accountNo = Integer.parseInt(accNoTemp);
            if (checkForAccount(accountNo)) {
                BankTransaction bankTransactionInbound = new BankTransaction();
                bankTransactionInbound.setTransactionType(TransactionType.INBOUND);
                bankTransactionInbound.setAccountNo(accountNo);
                bankTransactionInbound.setTransferAmount(convertedAmount);
                bankTransactionInbound.setForeignAccount(IBANPREFIX + accountList.get(accountIndex - 1).getAccountNo());
                bankTransactionInbound.setName(ClientDataDao.getName(AccountMenu.activeUser));
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


    public static String getIBAN() {
        Scanner input = new Scanner(System.in);
        String iban;
        while (true) {
            System.out.print("\nPlease input the recipient IBAN: ");
            iban = input.nextLine();
            break;
        }
        return iban;
    }

    public static String getName() {
        Scanner input = new Scanner(System.in);
        String name;
        while (true) {
            System.out.print("\nPlease input the recipient name (first and last name): ");
            name = input.nextLine();
            break;
        }
        return name;
    }

    public static Double getTransferAmount() {
        double transferAmount;
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("\nPlease enter the amount you would like to transfer: ");
            try {
                transferAmount = input.nextDouble();
                if (transferAmount <= accountList.get(accountIndex - 1).getBalance() && transferAmount > 0) {
                    break;
                } else {
                    System.out.printf("\033[0;31mPlease enter a valid amount (between 0 and %.02f).\033[0m\n", accountList.get(accountIndex - 1).getBalance());
                }
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.printf("\033[0;31mPlease enter a valid amount (between 0 and %.02f).\033[0m\n", accountList.get(accountIndex - 1).getBalance());
            }
        }
        return transferAmount;
    }

    public static void compareCurrencies(BankTransaction bankTransaction) {
        int foreignAccountNo = Integer.parseInt(bankTransaction.getForeignAccount().substring(16));
        if (getAccountCurrency(foreignAccountNo) != getAccountCurrency(bankTransaction.getAccountNo())) {
            convertedAmount = convertCurrency(foreignAccountNo, bankTransaction.getTransferAmount());
            System.out.println("\nWould you like to continue [Y/N]?");
            Scanner yesOrNo = new Scanner(System.in);
            String typeChoice;
            do {
                System.out.print("\nInput: ");
                typeChoice = yesOrNo.nextLine();
                typeChoice = typeChoice.toUpperCase();
                switch (typeChoice) {
                    case "Y":
                        outBoundTransaction(bankTransaction);
                        break;
                    case "N":
                        break;
                    default:
                        System.out.println(AccountMenu.INVALIDMESSAGE);
                        break;
                }
            }
            while (!(typeChoice.equals("Y") || typeChoice.equals("N")));
        }
    }

    private MakeTransaction() {
        throw new IllegalStateException();
    }

}
