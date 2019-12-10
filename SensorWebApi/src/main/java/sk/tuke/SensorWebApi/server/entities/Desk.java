package sk.tuke.SensorWebApi.server.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Desk")
public class Desk implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label", length = 16, nullable = false)
    private String label;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "officeId")
    private Office office;

    public Desk() { }

    public Desk(Team team, Office office) {
        this.team = team;
        this.office = office;
    }

    public Desk(String label, Team team, Office office) {
        this.label = label;
        this.team = team;
        this.office = office;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getLabel() {return label; }

    public void setLabel(String label) { this.label = label; }

    public Team getTeam() { return team; }

    public void setTeam(Team team) { this.team = team; }

    public Office getOffice() { return office; }

    public void setOffice(Office office) { this.office = office; }

    @Override
    public String toString() {
        return "Desk{" +
                "id=" + id +
                ", team=" + team.toString() +
                ", office=" + office.toString() +
                '}';
    }
}
