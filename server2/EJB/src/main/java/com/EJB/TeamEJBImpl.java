package com.EJB;

import com.DAO.TeamDAO;
import com.DAO.TeamHumanDAO;
import com.FactoriesKt;
import com.JsonDTO.TeamDTO;
import com.JsonDTO.UpdateRespDTO;
import com.Model.Team;
import com.Model.TeamHuman;
import com.utils.HibernateUtil;
import com.utils.Message;
import jakarta.ejb.Local;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;


import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Stateless
@Remote(TeamEJB.class)
@Local(TeamEJB.class)
public class TeamEJBImpl implements TeamEJB, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String URI_HUMAN
            = "http://localhost:8180/LABSOA1_war/humanBeings";
    TeamHumanDAO teamHumanDAO=new TeamHumanDAO();
    TeamDAO teamDAO=new TeamDAO();
    Client client= FactoriesKt.getHttpClient();
    @Override
    public String createTeam(String nameTeam, Set<Integer> idsHuman) {
        Message message=new Message();
        Team teamTable=new Team(nameTeam);
        teamDAO.save(teamTable);
        int i=0;
        for (Integer id_human:idsHuman) {
            String json = getHuman(Long.valueOf(id_human));
            JSONObject jObject = new JSONObject(json);
            int code=jObject.getInt("code");
            if (code == 0){
                String text = jObject.getString("data");
                boolean f= text.contains("Not found");
                if (f) {
                    i++;
                    continue;
                }
            }
            teamHumanDAO.addHumanTeam(teamTable, id_human);
        }
        if(idsHuman.size()==i)
            throw new NoSuchElementException();

        message.setCode(1);
        //message.setData(+ nameTeam );
        return "Create Team with name=";
    }

    public String updateHuman(Long id_human, String newValue, String nameField){
        UpdateRespDTO updateRespDTO =new UpdateRespDTO(id_human,nameField,newValue);
        Response response=client.target(URI_HUMAN).request(MediaType.APPLICATION_JSON).put(Entity.entity(updateRespDTO, "application/json"));
        return response.readEntity(String.class);
    }
    @Override
    public String changeMood(int id_team) {
        Message message=new Message();
        Session session= HibernateUtil.getSessionFactory().openSession();
        Team team=teamDAO.getById((long) id_team);
        if (team==null)
            throw new NoSuchElementException();
        List<TeamHuman> list= (List<TeamHuman>) session.
                createQuery("From TeamHuman").list();
        for (TeamHuman human : list) {
            if(human.getTeam()!=null && human.getTeam().getId()==id_team){
                updateHuman((long) human.getHuman_id(), "SORROW","mood");
            }
        }
        message.setCode(1);
        return "The mood change was successful";
    }

    @Override
    public String test(String nameServer) {
        return nameServer;
    }
    @Override
    public String getTeams() {
        JSONObject head = new JSONObject();
        JSONArray array = new JSONArray();

        List<Integer> idsHuman = teamHumanDAO.getAllHumanIds();
        for (Integer id : idsHuman) {
            String json = getHuman(Long.valueOf(id));
            JSONObject jObject = new JSONObject(json);
            int code =  jObject.getInt("code");
            if (code==0)
                continue;

            JSONObject jObject1   = (JSONObject) jObject.get("data");
            List<Team> teams = teamHumanDAO.getTeamsByHuman(id);
            JSONArray jTeam = new JSONArray(teams);
            jObject1.put("team", jTeam);
            array.put(jObject.get("data"));

        }
        head.put("data",array);
        head.put("code",1);
        return array.toString();
    }
    public String getHuman(Long id){
        Response response=client.target(URI_HUMAN+"/"+id).request().get();
        return  response.readEntity(String.class);
    }
}


