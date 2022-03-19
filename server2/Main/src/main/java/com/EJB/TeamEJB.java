package com.EJB;

import com.JsonDTO.TeamDTO;
import jakarta.ejb.Local;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
@Remote
@Local
public interface TeamEJB {
    public String createTeam(String nameTeam, Set<Integer> idsHuman);
    public String test(String nameServer);
    public String changeMood(int id_team);
    public String getTeams();
}
