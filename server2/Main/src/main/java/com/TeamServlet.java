package com;

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
import sun.awt.X11.MWMConstants;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/tean")
@Logger
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamServlet implements Serializable {//java:global
   // TeamEJB ejb= (TeamEJB) FactoriesKt.getFromEJBPool("ejb:/two/TeamEJBImpl!com.EJB.TeamEJB");
   TeamEJB ejb= (TeamEJB) FactoriesKt.getFromEJBPool("java:global/two/TeamEJBImpl");
    @POST
    @Path("/create/id/name")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMsg(TeamDTO newTeam) {
        Message message=new Message();
        message.setCode(1);
        String result= ejb.createTeam(newTeam.getNameTeam(),newTeam.getIdhumans());
        message.setData(result);
        return Response.ok().entity(message).build();
    }

    @GET
    @Path("/test")
    public Response test()  {
        Message message=new Message();
        message.setCode(1);
        String nameServer="OK 1 ";
        String result= ejb.test(nameServer);
        message.setData(result);
        return Response.ok().entity(message).build();
    }

    @PUT
    @Path("/{team-id}/make-depressive")
    public Response changeMood(@PathParam("team-id")  int id_team){
        Message message = new Message();
        message.setCode(1);
        String result= ejb.changeMood(id_team);
        message.setData(result);
        return Response.ok().entity(message).build();
    }

    @GET
    @Path("/all")
    public  Response getTeams(){
        JSONObject head = new JSONObject();
        String jsonTeams= ejb.getTeams();
        JSONArray jsonObject=new JSONArray(jsonTeams);
        head.put("data",jsonObject);
        head.put("code",1);
        return Response.ok().entity(head.toString()).build();
    }
}
