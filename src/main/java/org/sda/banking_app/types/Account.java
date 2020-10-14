package org.sda.banking_app.types;

import org.sda.banking_app.types.enums.AccountType;
import org.sda.banking_app.types.enums.Currency;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Account")
@Table(name = "account")

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_no")
    private int accountNo;

    @Column(name = "username")
    private String username;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "balance")
    private double balance;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "currency")
    private Currency currency;

    @OneToMany
    private List<BankTransaction> bankTransactions = new ArrayList<>();

    public Account() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

}