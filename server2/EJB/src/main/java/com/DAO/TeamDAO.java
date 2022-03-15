package com.DAO;

import com.Model.Team;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.utils.HibernateUtil;

import java.util.List;

public class TeamDAO {

        public void save(Team newTeam){

            Session session= HibernateUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(newTeam);
            tx1.commit();
            session.close();
        }

        public  Team getById(Long id){
            Session session= HibernateUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            Team human= session.get(Team.class, id);
            tx1.commit();
            session.close();
            return human;
        }
        public List<Team> getAll(){
            Session session= HibernateUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            List<Team> human= session.createQuery("from Team").list();
            tx1.commit();
            session.close();
            return human;
        }
}
