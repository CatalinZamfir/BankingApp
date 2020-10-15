package org.sda.banking_app.types;

import org.sda.banking_app.types.enums.TransactionType;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity(name = "BankTransaction")
@Table(name = "transactions")

public class BankTransaction {

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
    private double transferAmount;

    @Column(name = "foreign_account")
    private String foreignAccount;

    @Column(name = "date_and_time")
    private LocalDateTime dateAndTime;

    public BankTransaction() {
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public void setForeignAccount(String foreignAccount) {
        this.foreignAccount = foreignAccount;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getForeignAccount() {
        return foreignAccount;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }
}


