package com.DAO;

import com.Model.Team;
import com.Model.TeamHuman;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.utils.HibernateUtil;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Logger
public class TeamHumanDAO {

    public void  addHumanTeam(Team team, Integer human_id){
        Session session= HibernateUtil.getSessionFactory().openSession();

            List<TeamHuman> teamhumans=session.createQuery("From TeamHuman as obj where obj.human_id= :idTeam")
                .setParameter("idTeam",human_id).list();
                log.println("+++++++++++++++SIZE=="+teamhumans.size());

            if(teamhumans.size()!=0) {
                Transaction tx1 = session.beginTransaction();
                session.remove(teamhumans.get(0));
                tx1.commit();
            }
            Transaction tx2= session.beginTransaction();
            TeamHuman teamHuman=new TeamHuman();
            teamHuman.setTeam(team);
            teamHuman.setHuman_id(Math.toIntExact(human_id));
            session.saveOrUpdate(teamHuman);

            tx2.commit();
            session.close();

    }

    public List<Integer> getIdsByTeam(Long idTeam){
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

            List<Integer> idsHuman=session.createQuery("select obj.human_id From TeamHuman as obj where obj.team.id= :idTeam")
                                       .setParameter("idTeam",idTeam).list();
        tx1.commit();
        session.close();
        return idsHuman;
    }
    public List<Team> getTeamsByHuman(Integer idHuman){
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        List<Team> idsHuman=session.createQuery("select obj.team From TeamHuman as obj where obj.human_id= :idHuman")
                .setParameter("idHuman",idHuman).list();
        tx1.commit();
        session.close();
        return idsHuman;
    }
    public List<Integer> getAllHumanIds(){
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<Integer> idsHuman=session.createQuery("select obj.human_id From TeamHuman as obj").list();
        tx1.commit();
        session.close();
        return idsHuman;
    }

}
