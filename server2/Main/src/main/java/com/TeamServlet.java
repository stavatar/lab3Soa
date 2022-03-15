package com;

import com.DAO.TeamDAO;
import com.DAO.TeamHumanDAO;
import com.EJB.TeamEJB;
import com.JsonDTO.TeamDTO;
import com.Model.Team;
import com.Model.TeamHuman;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.hibernate.Session;
import org.hibernate.exception.JDBCConnectionException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.utils.HibernateUtil;
import com.utils.Message;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;

@Path("/tean")
@Logger
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamServlet {
    TeamDAO teamDAO=new TeamDAO();
    TeamHumanDAO teamHumanDAO=new TeamHumanDAO();
    TeamEJB ejb= (TeamEJB) FactoriesKt.getFromEJBPool("ejb:/two/TeamEJBImpl!com.EJB.TeamEJB");
    @POST
    @Path("/create/id/name")
    public Response getMsg(TeamDTO newTeam) {
        Message message=new Message();
        try {
                Team teamTable=new Team(newTeam.getNameTeam());
                teamDAO.save(teamTable);
                int i=0;
                for (Integer id_human:newTeam.getIdhumans()) {
                    String json = ejb.getHuman(Long.valueOf(id_human));
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
                if(newTeam.getIdhumans().size()==i)
                throw new NoSuchElementException();

                    message.setCode(1);
                    message.setData("Create Team with name="+ newTeam.getNameTeam() );
                    return Response.status(200).entity(message).build();
                }catch(NoSuchElementException e){
                    message.setCode(0);
                    message.setData("Not found all ids human");
                    return Response.status(404).entity(message).build();
                 }catch (JDBCConnectionException e){
                    message.setCode(0);
                    message.setData("Error! No database connection ");
                    return Response.status(503).entity(message).build();
                 }
                catch (Exception e){
                    message.setCode(0);
                    message.setData("failed to create team with name "+ e.getClass()+" message="+e.getMessage() + "STACK = " + Arrays.toString(e.getStackTrace()));
                    return Response.status(500).entity(message).build();
                }

    }

    @GET
    @Path("/test")
    public Response test() throws NamingException {
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.close();
       /* final Hashtable jndiProperties = new Hashtable();

        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,  "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL,"http://localhost:8080");
        final Context ctx = new InitialContext(jndiProperties);
        TeamEJB TeamEJB = (TeamEJB) ctx.lookup("ejb:/two/TeamEJBImpl!com.EJB.TeamEJB");*/

        return Response.ok().entity("OK 2").build();
        //return Response.status(200).entity("test test").build();
    }

    @PUT
    @Path("/{team-id}/make-depressive")
    public Response changeMood(@PathParam("team-id")  int id_team){
        Message message=new Message();

       try {
        Session session= HibernateUtil.getSessionFactory().openSession();
        Team team=teamDAO.getById((long) id_team);
        if (team==null)
            throw new NoSuchElementException();
        List<TeamHuman> list= (List<TeamHuman>) session.
                createQuery("From TeamHuman").list();
        for (TeamHuman human : list) {
            if(human.getTeam()!=null && human.getTeam().getId()==id_team){
                ejb.updateHuman((long) human.getHuman_id(), "SORROW","mood");
            }
        }
            message.setCode(1);
            message.setData("The mood change was successful ");
        return Response.status(200).entity("RESULT").build();
        } catch (ProcessingException e){
           message.setCode(0);
           message.setData("Server number 1 is unvaliable");
           return Response.status(504).entity(message).build();
       } catch(NoSuchElementException e){
           message.setCode(0);
           message.setData("Not found team with id "+ id_team);
           return Response.status(404).entity(message).build();
       }catch (Exception e){
            message.setCode(0);
            message.setData("Failed to The mood change was successful " + e.getCause());
            return Response.status(500).entity(message).build();
        }
    }




    @GET
    @Path("/all")
    public  Response getTeams(){
        JSONObject head = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<Integer> idsHuman = teamHumanDAO.getAllHumanIds();
            for (Integer id : idsHuman) {
                String json = ejb.getHuman(Long.valueOf(id));
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
        }catch (ProcessingException e){
            head.put("code",0);
            head.put("data","Server number 1 is unvaliable");
            return Response.status(504).entity(head.toString()).build();
        }catch (JDBCConnectionException e){
            head.put("code",0);
            head.put("data","Error! No database connection ");
            return Response.status(503).entity(head.toString()).build();
        }
        catch (Exception e){
            head.put("code",0);
            head.put("data"," NAME="+ e.getClass()+ "  "+e.getMessage() );
            return Response.status(500).entity(head.toString()).build();
        }
        head.put("data",array);
        head.put("code",1);
        return Response.status(200).entity(head.toString()).build();
    }
}
