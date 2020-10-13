package org.sda.BankingApp.types;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sda.BankingApp.config.HibernateUtil;

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

    private static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}
