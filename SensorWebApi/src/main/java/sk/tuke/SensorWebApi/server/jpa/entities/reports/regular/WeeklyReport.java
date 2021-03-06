package sk.tuke.SensorWebApi.server.jpa.entities.reports.regular;


import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "week_report")
public class WeeklyReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "averageOccupation")
    private float averageOccupation;

    @ManyToOne
    @JoinColumn(name = "deskId")
    private Desk desk;

    @Column(name = "week")
    private Date week;

    @OneToMany(mappedBy = "weeklyReport", cascade = CascadeType.REMOVE)
    private List<DailyReport> dailyReports = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "monthlyReport")
    private MonthlyReport monthlyReport;

    public WeeklyReport() { }

    public WeeklyReport(float averageOccupation, Desk desk, Date week, List<DailyReport> dailyReports) {
        this.averageOccupation = averageOccupation;
        this.desk = desk;
        this.week = week;
        this.dailyReports = dailyReports;
    }

    public List<DailyReport> getDailyReports() {
        return dailyReports;
    }

    public void setDailyReports(List<DailyReport> dailyReports) {
        this.dailyReports = dailyReports;
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

    public Date getWeek() {
        return week;
    }

    public void setWeek(Date week) {
        this.week = week;
    }

    public Desk getDesk() {
        return desk;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    public MonthlyReport getMonthlyReport() {
        return monthlyReport;
    }

    public void setMonthlyReport(MonthlyReport monthlyReport) {
        this.monthlyReport = monthlyReport;
    }
}
