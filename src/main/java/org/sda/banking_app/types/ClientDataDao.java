package org.sda.banking_app.types;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sda.banking_app.config.HibernateUtil;

import java.util.List;

public class ClientDataDao {

    public static boolean createNewClient(ClientData clientData) {
        Transaction transaction = null;
        try (Session session = getSession()){
            transaction = session.beginTransaction();
            session.save(clientData);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return false;
    }

    public static boolean checkForUsername(String username) {
        try (Session session = getSession()) {
            String findByUsernameHql = "FROM ClientData p WHERE p.username = :username";
            Query<ClientData> query = session.createQuery(findByUsernameHql);
            query.setParameter("username", username);
            List<ClientData> foundAccount = query.getResultList();
            if (foundAccount.isEmpty()) {
                return false;
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean findByCNP(Long cnp) {
        try (Session session = getSession()) {
            String findByUsernameHql = "FROM ClientData p WHERE p.cnp = :cnp";
            Query<ClientData> query = session.createQuery(findByUsernameHql);
            query.setParameter("cnp", cnp);
            List<ClientData> foundAccount = query.getResultList();
            if (foundAccount.isEmpty()) {
                return false;
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static String getName(String username) {
        String name = null;
        try (Session session = getSession()) {
            String findByUsernameHql = "FROM ClientData p WHERE p.username = :username";
            Query<ClientData> query = session.createQuery(findByUsernameHql);
            query.setParameter("username", username);
            List<ClientData> foundAccount = query.getResultList();
            name = foundAccount.get(0).getFirstName() + " " + foundAccount.get(0).getLastName();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return name;
    }

    public static boolean findByEmail(String email) {
        try (Session session = getSession()) {
            String findByUsernameHql = "FROM ClientData p WHERE p.email = :email";
            Query<ClientData> query = session.createQuery(findByUsernameHql);
            query.setParameter("email", email);
            List<ClientData> foundAccount = query.getResultList();
            if (foundAccount.isEmpty()) {
                return false;
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean checkCredentials(String username, String password) {
        try (Session session = getSession()) {
            String findByUsernameHql = "FROM ClientData p WHERE p.username = :username AND p.password = :password";
            Query<ClientData> query = session.createQuery(findByUsernameHql);
            query.setParameter("username", username);
            query.setParameter("password", password);
            if (query.getResultList().isEmpty()) {
                return false;
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}
