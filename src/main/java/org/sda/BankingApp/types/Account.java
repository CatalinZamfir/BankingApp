package org.sda.BankingApp.types;

import org.sda.BankingApp.types.enums.AccountType;
import org.sda.BankingApp.types.enums.Currency;

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
    private int balance;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "currency")
    private Currency currency;

    @OneToMany
    private List<Transactions> transactions = new ArrayList<>();

    public Account() {
    }



}