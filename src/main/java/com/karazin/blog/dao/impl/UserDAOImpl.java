package com.karazin.blog.dao.impl;

import com.karazin.blog.dao.UserDAO;
import com.karazin.blog.model.User;
import com.karazin.blog.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    @Override
    public void save(User user) throws Exception {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            System.out.println("Saving user: " + user.getUsername());
            session.saveOrUpdate(user);
            tx.commit();
            System.out.println("User saved successfully: " + user.getUsername());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error saving user: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<User> findById(Long id) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        }
    }

    @Override
    public Optional<User> findByUsername(String username) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM users WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResultOptional();
        }
    }
}