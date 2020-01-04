package sk.tuke.SensorWebApi.server.jpa.entities.reports.team;


import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "weekly_team_report")
public class WeeklyTeamReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week")
    private Date week;

    @Column(name = "averageOccupation")
    private float averageOccupation;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "monthlyTeamReport")
    private MonthlyTeamReport monthlyTeamReport;

    @OneToMany(mappedBy = "weeklyTeamReport", cascade = CascadeType.REMOVE)
    private List<DailyTeamReport> dailyTeamReportList = new ArrayList<>();


    public WeeklyTeamReport(Date week, float averageOccupation, Team team, List<DailyTeamReport> dailyTeamReportList) {
        this.week = week;
        this.averageOccupation = averageOccupation;
        this.team = team;
        this.dailyTeamReportList = dailyTeamReportList;
    }

    public WeeklyTeamReport() { }

    public WeeklyTeamReport(Date week, float averageOccupation) {
        this.week = week;
        this.averageOccupation = averageOccupation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getWeek() {
        return week;
    }

    public void setWeek(Date week) {
        this.week = week;
    }

    public float getAverageOccupation() {
        return averageOccupation;
    }

    public void setAverageOccupation(float averageOccupation) {
        this.averageOccupation = averageOccupation;
    }

    public List<DailyTeamReport> getDailyTeamReportList() {
        return dailyTeamReportList;
    }

    public void setDailyTeamReportList(List<DailyTeamReport> dailyTeamReportList) {
        this.dailyTeamReportList = dailyTeamReportList;
    }

    public MonthlyTeamReport getMonthlyTeamReport() {
        return monthlyTeamReport;
    }

    public void setMonthlyTeamReport(MonthlyTeamReport monthlyTeamReport) {
        this.monthlyTeamReport = monthlyTeamReport;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }


}
