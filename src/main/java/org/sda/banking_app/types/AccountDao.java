package org.sda.banking_app.types;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sda.banking_app.config.HibernateUtil;
import org.sda.banking_app.types.enums.Currency;

import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    public static void createNewAccount(Account account) {
        Transaction transaction = null;
        try (Session session = getSession()){
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public static List<Account> findAccounts(String username) {
        List<Account> accounts = new ArrayList<>();
        try (Session session = getSession()) {
            String findAccountsByUserHql = "FROM Account p WHERE p.username = :username";
            Query<Account> query = session.createQuery(findAccountsByUserHql);
            query.setParameter("username", username);
            accounts = query.getResultList();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public static boolean checkForAccount(int accountNo) {
        try (Session session = getSession()) {
            String findAccount = "FROM Account p WHERE p.accountNo = :accountNo";
            Query<Account> query = session.createQuery(findAccount);
            query.setParameter("accountNo", accountNo);
            List<Account> foundAccount = query.getResultList();
            if (foundAccount.isEmpty()) {
                return false;
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static double getAccountBalance(int accountNo) {
        try (Session session = getSession()) {
            String findAccount = "FROM Account p WHERE p.accountNo = :accountNo";
            Query<Account> query = session.createQuery(findAccount);
            query.setParameter("accountNo", accountNo);
            List<Account> foundAccount = query.getResultList();
            if (foundAccount.isEmpty()) {
                return 0;
            } else {
                return foundAccount.get(0).getBalance();
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static Currency getAccountCurrency(int accountNo) {
        try (Session session = getSession()) {
            String findAccount = "FROM Account p WHERE p.accountNo = :accountNo";
            Query<Account> query = session.createQuery(findAccount);
            query.setParameter("accountNo", accountNo);
            List<Account> foundAccount = query.getResultList();
            if (foundAccount.isEmpty()) {
                return null;
            } else {
                return foundAccount.get(0).getCurrency();
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}
