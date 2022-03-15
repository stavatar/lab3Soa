package com.Model;

import javax.persistence.*;

@Entity
@Table
public class TeamHuman {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "human_id")
    private int human_id;

    @ManyToOne(cascade = CascadeType.ALL,optional = true)
    @JoinColumn (name="team_id")
    private Team team;

    public TeamHuman() {
    }

    public TeamHuman(int human_id, Team team) {
        this.human_id = human_id;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHuman_id() {
        return human_id;
    }

    public void setHuman_id(int human_id) {
        this.human_id = human_id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
