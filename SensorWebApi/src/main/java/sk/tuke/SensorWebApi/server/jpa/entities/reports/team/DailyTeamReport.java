package sk.tuke.SensorWebApi.server.jpa.entities.reports.team;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "daily_team_report")
public class DailyTeamReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @Column(name = "day", nullable = false)
    private Date day;

    @Column(name = "avarage_occuption", nullable = false)
    private Float averageOccupation;

    @ManyToOne
    @JoinColumn(name = "weeklyTeamReport")
    private WeeklyTeamReport weeklyTeamReport;

    public DailyTeamReport(Team team, Date day, Float averageOccupation) {
        this.team = team;
        this.day = day;
        this.averageOccupation = averageOccupation;
    }

    public DailyTeamReport() { }

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

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Float getAverageOccupation() {
        return averageOccupation;
    }

    public void setAverageOccupation(Float averageOccupation) {
        this.averageOccupation = averageOccupation;
    }

    public WeeklyTeamReport getWeeklyTeamReport() {
        return weeklyTeamReport;
    }

    public void setWeeklyTeamReport(WeeklyTeamReport weeklyTeamReport) {
        this.weeklyTeamReport = weeklyTeamReport;
    }


}
