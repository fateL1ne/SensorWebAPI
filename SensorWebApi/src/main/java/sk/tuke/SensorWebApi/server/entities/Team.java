package sk.tuke.SensorWebApi.server.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Team")
public class Team
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teamName", length = 30, nullable = false)
    private String teamName;

    @Column(name = "leader", length = 30, nullable = false)
    private String leader;

    @OneToMany(mappedBy = "team")
    private List<Desk> desks = new ArrayList<>();

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", leader='" + leader + '\'' +
                ", desks=" + desks.size() +
                '}';
    }

    public Team() { }

    public Team(String teamName, String leader) {
        this.teamName = teamName;
        this.leader = leader;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTeamName() { return teamName; }

    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getLeader() { return leader; }

    public void setLeader(String leader) { this.leader = leader; }

    public List<Desk> getDesks() { return desks; }

    public void setDesks(List<Desk> desks) { this.desks = desks; }
}
