package sk.tuke.SensorWebApi.server.jpa.entities.reports.team;


import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.core.WeightTimeline;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "monthly_team_attendance")
public class MonthlyTeamAttendance
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @Column (name = "month", nullable =  false)
    private Date month;

    @OneToMany(mappedBy = "monthlyTeamAttendance")
    private List<WeightTimeline> weightTimelines = new ArrayList<>();

    public MonthlyTeamAttendance(Team team, Date month) {
        this.team = team;
        this.month = month;
    }

    public MonthlyTeamAttendance() {
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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public List<WeightTimeline> getWeightTimelines() {
        return weightTimelines;
    }

    public void setWeightTimelines(List<WeightTimeline> weightTimelines) {
        this.weightTimelines = weightTimelines;
    }
}
