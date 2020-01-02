package sk.tuke.SensorWebApi.server.entities;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "monthly_team_report")
public class MonthlyTeamReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @Column(name = "avarage_occuption", nullable = false)
    private Float averageOccupation;

    @Column(name = "month")
    private Date month;

    public MonthlyTeamReport(Team team, Float averageOccupation, Date month) {
        this.team = team;
        this.averageOccupation = averageOccupation;
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Float getAverageOccupation() {
        return averageOccupation;
    }

    public void setAverageOccupation(Float averageOccupation) {
        this.averageOccupation = averageOccupation;
    }
}
