package org.sda.banking_app.types;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sda.banking_app.config.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ClientDataDao {

    public static void createNewClient(ClientData clientData) {
        Transaction transaction = null;
        try (Session session = getSession()){
            transaction = session.beginTransaction();
            session.save(clientData);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public static boolean findByUsername(String username) {
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

    public static ClientData checkCredentials(String user, String pass) {
        ClientData result = null;
        try (Session session = getSession()) {
            String findByUsernameHql = "FROM ClientData p WHERE p.username = :username AND p.password = :password";
            Query<ClientData> query = session.createQuery(findByUsernameHql);
            query.setParameter("username", user);
            query.setParameter("password", pass);
            List<ClientData> foundPlayers = query.getResultList();
            if (foundPlayers.isEmpty()) {
                return result;
            } else {
                result = foundPlayers.get(0);
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}
