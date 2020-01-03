package sk.tuke.SensorWebApi.server.jpa.entities.reports.team;


import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.WeeklyReport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "monthlyReport")
    private List<WeeklyReport> weeklyReportList = new ArrayList<>();

    public MonthlyTeamReport() {
    }

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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public List<WeeklyReport> getWeeklyReportList() {
        return weeklyReportList;
    }

    public void setWeeklyReportList(List<WeeklyReport> weeklyReportList) {
        this.weeklyReportList = weeklyReportList;
    }
}
