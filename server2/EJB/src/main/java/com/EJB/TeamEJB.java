package com.EJB;


import com.JsonDTO.TeamDTO;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

public interface TeamEJB {
    public String createTeam(String nameTeam, Set<Integer> idsHuman);
    public String test(String nameServer);
    public String changeMood(int id_team);
    public String getTeams();
}
