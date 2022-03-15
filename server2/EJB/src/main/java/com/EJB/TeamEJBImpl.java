package com.EJB;

import com.FactoriesKt;
import com.JsonDTO.UpdateRespDTO;


import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless @Remote(TeamEJB.class)
public class TeamEJBImpl implements TeamEJB{
    private static final String URI_HUMAN
            = "https://localhost:8181/LABSOA1_war/humanBeings";
    Client client= FactoriesKt.getHttpClient();
    @Override
    public void createTeam() {

    }
    @Override
    public String updateHuman(Long id_human, String newValue, String nameField){
        UpdateRespDTO updateRespDTO =new UpdateRespDTO(id_human,nameField,newValue);
        Response response=client.target(URI_HUMAN).request(MediaType.APPLICATION_JSON).put(Entity.entity(updateRespDTO, "application/json"));
        return response.readEntity(String.class);
    }
    @Override
    public void changeMood() {

    }

    @Override
    public void getTeams() {

    }
    public String getHuman(Long id){
        Response response=client.target(URI_HUMAN+"/"+id).request().get();
        return  response.readEntity(String.class);
    }
}
