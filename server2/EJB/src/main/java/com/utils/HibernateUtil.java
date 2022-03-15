package com.utils;


import com.Model.Team;
import com.Model.TeamHuman;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil
{
    private HibernateUtil() {}
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory  = new Configuration().configure()
                                                 .addAnnotatedClass(Team.class)
                                                 .addAnnotatedClass(TeamHuman.class)
                                                 .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}