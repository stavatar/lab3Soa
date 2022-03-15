package com.EJB;


public interface TeamEJB {
    public void createTeam();

    public String updateHuman(Long id_human, String newValue, String nameField);

    public void changeMood();

    public void getTeams();

    public String getHuman(Long id);
}
