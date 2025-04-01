package com.karazin.blog.dao.impl;

import com.karazin.blog.dao.FollowDAO;
import com.karazin.blog.model.Follow;
import com.karazin.blog.model.FollowId;
import com.karazin.blog.model.User;
import com.karazin.blog.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FollowDAOImpl implements FollowDAO {

    @Override
    public void save(Follow follow) throws Exception {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(follow);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(FollowId followId) throws Exception {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Follow follow = session.get(Follow.class, followId);
            if (follow != null) {
                session.delete(follow);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public List<User> findFollowedUsersByUser(User user) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT f.followedUser FROM Follow f WHERE f.followingUser = :user", User.class)
                    .setParameter("user", user)
                    .list();
        }
    }

    @Override
    public List<User> findFollowersByUser(User user) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT f.followingUser FROM Follow f WHERE f.followedUser = :user", User.class)
                    .setParameter("user", user)
                    .list();
        }
    }
}