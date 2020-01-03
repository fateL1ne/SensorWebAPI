package sk.tuke.SensorWebApi.server.jpa.entities.reports.regular;


import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "monthly_report")
public class MonthlyReport
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "averageOccupation")
    private float averageOccupation;

    @OneToMany(mappedBy = "monthlyReport", cascade = CascadeType.REMOVE)
    private List<WeeklyReport> weeklyReportList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "deskId")
    private Desk desk;

    @Column(name = "month")
    private Date month;

    public MonthlyReport() { }

    public MonthlyReport(float averageOccupation, List<WeeklyReport> weeklyReportList, Desk desk, Date month, Team team) {
        this.averageOccupation = averageOccupation;
        this.weeklyReportList = weeklyReportList;
        this.desk = desk;
        this.month = month;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getAverageOccupation() {
        return averageOccupation;
    }

    public void setAverageOccupation(float averageOccupation) {
        this.averageOccupation = averageOccupation;
    }

    public List<WeeklyReport> getWeeklyReportList() {
        return weeklyReportList;
    }

    public void setWeeklyReportList(List<WeeklyReport> weeklyReportList) {
        this.weeklyReportList = weeklyReportList;
    }

    public Desk getDesk() {
        return desk;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
