package sk.tuke.SensorWebApi.server.entities;

import javax.persistence.*;

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

    @Column(name = "average_occupation", nullable = false)
    private Float averageOccupation;

    public DailyReport() { }

    public DailyReport(Desk desk) { this.desk = desk; }

    public DailyReport(Desk desk, Float averageOccupation) {
        this.desk = desk;
        this.averageOccupation = averageOccupation;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Desk getDesk() { return desk; }

    public void setDesk(Desk desk) { this.desk = desk; }

    public Float getAverageOccupation() { return averageOccupation; }

    public void setAverageOccupation(Float averageOccupation) { this.averageOccupation = averageOccupation; }
}
