package sk.tuke.SensorWebApi.server.jpa.entities.reports.regular;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// TODO: 1/4/20 SAVE THIS ONE???

@Entity
@Table(name = "interval_report")
public class IntervalReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<MonthlyReport> monthlyReports = new ArrayList<>();

    @Column(name = "average_occupation", nullable = false)
    private Float averageOccupation;

    public IntervalReport() { }

    public IntervalReport(List<MonthlyReport> monthlyReports, Float averageOccupation) {
        this.monthlyReports = monthlyReports;
        this.averageOccupation = averageOccupation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MonthlyReport> getMonthlyReports() {
        return monthlyReports;
    }

    public void setMonthlyReports(List<MonthlyReport> monthlyReports) {
        this.monthlyReports = monthlyReports;
    }

    public Float getAverageOccupation() {
        return averageOccupation;
    }

    public void setAverageOccupation(Float averageOccupation) {
        this.averageOccupation = averageOccupation;
    }
}
