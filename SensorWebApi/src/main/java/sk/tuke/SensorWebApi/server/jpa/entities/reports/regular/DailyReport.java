package sk.tuke.SensorWebApi.server.jpa.entities.reports.regular;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "daily_report")
public class DailyReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deskId")
    private Desk desk;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weeklyReportId")
    private WeeklyReport weeklyReport;

    @Column(name = "average_occupation", nullable = false)
    private Float averageOccupation;

    @Column(name = "day")
    private Date day;

    @Column(name = "timeline", nullable = true)
    private long timeline;


    public DailyReport() { }

    public DailyReport(Desk desk) { this.desk = desk; }


    public DailyReport(Desk desk, Float averageOccupation, Date day, long timeline) {
        this.desk = desk;
        this.averageOccupation = averageOccupation;
        this.day = day;
        this.timeline = timeline;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Desk getDesk() { return desk; }

    public void setDesk(Desk desk) { this.desk = desk; }

    public Float getAverageOccupation() { return averageOccupation; }

    public void setAverageOccupation(Float averageOccupation) { this.averageOccupation = averageOccupation; }

    public long getTimeline() { return timeline; }

    public void setTimeline(long timeline) { this.timeline = timeline; }

    public Date getDay() { return day; }

    public void setDay(Date day) { this.day = day; }

    public WeeklyReport getWeeklyReport() { return weeklyReport; }

    public void setWeeklyReport(WeeklyReport weeklyReport) { this.weeklyReport = weeklyReport; }
}
