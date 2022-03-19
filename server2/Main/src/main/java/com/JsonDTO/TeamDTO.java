package com.JsonDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement
public class TeamDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String nameTeam;
    private Set<Integer> idhumans;

    public Set<Integer> getIdhumans() {
        return idhumans;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public void setIdhumans(Set<Integer> idhumans) {
        this.idhumans = idhumans;
    }

    @Override
    public String toString() {
        return "\n TeamDTO{" +
                "nameTeam='" + nameTeam + '\'' +
                ", idhumans=" + idhumans +
                '}';
    }
}
