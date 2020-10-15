package org.sda.banking_app.types;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sda.banking_app.config.HibernateUtil;
import org.sda.banking_app.types.enums.TransactionType;

import java.util.ArrayList;
import java.util.List;

import static org.sda.banking_app.types.AccountDao.getAccountBalance;

public class BankTransactionDao {

    public static boolean createNewTransaction(BankTransaction bankTransaction) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(bankTransaction);
            Account accountUpdate = session.find(Account.class, bankTransaction.getAccountNo());
            if (bankTransaction.getTransactionType() == TransactionType.OUTBOUND) {
                accountUpdate.setBalance(getAccountBalance(bankTransaction.getAccountNo()) - bankTransaction.getTransferAmount());
            } else {
                accountUpdate.setBalance(getAccountBalance(bankTransaction.getAccountNo()) + bankTransaction.getTransferAmount());
            }
            session.update(accountUpdate);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                return false;
            }
        }
        return true;
    }

    public static List<BankTransaction> findBankTransactions(int accountNo) {
        List<BankTransaction> bankTransactions = new ArrayList<>();
        try (Session session = getSession()) {
            String findAccountsByAccountNoHql = "FROM BankTransaction p WHERE p.accountNo = :accountNo";
            Query<BankTransaction> query = session.createQuery(findAccountsByAccountNoHql);
            query.setParameter("accountNo", accountNo);
            bankTransactions = query.getResultList();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return bankTransactions;
    }

    private static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }


}
