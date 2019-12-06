package sk.tuke.SensorWebApi.server.entities;


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

    @OneToMany(mappedBy = "weeklyReport")
    private List<DailyReport> dailyReportList = new ArrayList<>();

    @Column(name = "averageOccupation")
    private float averageOccupation;

    @Column(name = "week")
    private Date week;

    public WeeklyReport() {
    }

    public WeeklyReport(List<DailyReport> dailyReportList, float averageOccupation, Date week) {
        this.dailyReportList = dailyReportList;
        this.averageOccupation = averageOccupation;
        this.week = week;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DailyReport> getDailyReportList() {
        return dailyReportList;
    }

    public void setDailyReportList(List<DailyReport> dailyReportList) {
        this.dailyReportList = dailyReportList;
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
}