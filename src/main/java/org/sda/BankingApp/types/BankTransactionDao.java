package org.sda.BankingApp.types;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sda.BankingApp.AccountMenu;
import org.sda.BankingApp.config.HibernateUtil;
import org.sda.BankingApp.types.enums.TransactionType;

public class BankTransactionDao {

    public static void createNewTransaction(BankTransaction bankTransaction) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(bankTransaction);
            Account accountUpdate = session.find(Account.class, bankTransaction.getAccountNo());
            if (bankTransaction.getTransactionType() == TransactionType.OUTBOUND) {
                accountUpdate.setBalance(AccountMenu.accountList.get(AccountMenu.accountIndex - 1).getBalance() - bankTransaction.getTransferAmount());
            } else {
                accountUpdate.setBalance(AccountMenu.accountList.get(AccountMenu.accountIndex - 1).getBalance() + bankTransaction.getTransferAmount());
            }
            session.update(accountUpdate);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public static boolean checkForAccount(int accountNo) {
        try (Session session = getSession()) {
            String findAccount = "FROM Account p WHERE p.accountNo = :accountNo";
            Query<Account> query = session.createQuery(findAccount);
            query.setParameter("accountNo", accountNo);
            if (!findAccount.isEmpty()) {
                return true;
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}
