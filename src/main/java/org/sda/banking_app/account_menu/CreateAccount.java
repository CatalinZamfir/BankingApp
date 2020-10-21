package org.sda.banking_app.account_menu;

import static org.sda.banking_app.account_menu.AccountMenu.*;
import static org.sda.banking_app.types.AccountDao.createNewAccount;

import org.sda.banking_app.types.Account;
import org.sda.banking_app.types.enums.AccountType;
import org.sda.banking_app.types.enums.Currency;

import java.util.Scanner;

public class CreateAccount {

    public static void createAccount() {
        System.out.print("\nPlease select the type of the new account.\n\n");
        System.out.println("[D] Debit   [C] Credit   [S] Savings");
        Scanner input1 = new Scanner(System.in);
        String typeChoice;
        AccountType accountType = null;
        do {
            System.out.print("\nInput: ");
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
                    System.out.println(AccountMenu.INVALIDMESSAGE);
                    break;
            }
        }
        while (!(typeChoice.equals("D") || typeChoice.equals("C") || typeChoice.equals("S")));
        System.out.print("\nPlease select the currency of your new account.\n\n");
        System.out.println("[R] RON   [E] EUR   [U] USD");
        Scanner input2 = new Scanner(System.in);
        String currencyChoice;
        Currency currency = null;
        do {
            System.out.print("\nInput: ");
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
                    System.out.println(AccountMenu.INVALIDMESSAGE);
                    break;
            }
        }
        while (!(currencyChoice.equals("R") || currencyChoice.equals("E") || currencyChoice.equals("U")));
        Account account = new Account();
        account.setUsername(activeUser);
        account.setAccountType(accountType);
        account.setCurrency(currency);
        createNewAccount(account);
        System.out.print("\n\033[0;34mYour new account was succesfully created.\033[0m\n");
        goBackToAccountMenu();
    }

    private CreateAccount(){
        throw new IllegalStateException();
    }

}
