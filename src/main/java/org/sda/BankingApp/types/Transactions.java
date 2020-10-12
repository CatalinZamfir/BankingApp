package org.sda.BankingApp.types;

import org.sda.BankingApp.types.enums.TransactionType;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "Transactions")
@Table(name = "transactions")

public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reference_id")
    private int referenceId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "account_no")
    private int accountNo;

    @Column(name = "transfer_amount")
    private int transferAmount;

    @Column(name = "foreign_account")
    private String foreignAccount;

    @Column(name = "date_and_time")
    private Date dateAndTime;

    public Transactions() {
    }
    
}


