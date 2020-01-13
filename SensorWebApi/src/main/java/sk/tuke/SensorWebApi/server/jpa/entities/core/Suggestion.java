package sk.tuke.SensorWebApi.server.jpa.entities.core;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "suggestion")
public class Suggestion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "desk1", nullable = false)
    private Desk desk1;

    @Column (name = "desk2", nullable = false)
    private Desk desk2;

    @Column (name = "desk1_timeline", nullable = false)
    private long timeline1;

    @Column (name = "desk2_timeline", nullable = false)
    private long timeline2;

    @Column (name = "office", nullable = false)
    private Office office;

    @Column (name = "month", nullable =  false)
    private Date month;

    @Column (name = "day", nullable = false)
    private String day;

    public Suggestion(Desk desk1, Desk desk2, long timeline1, long timeline2, Office office, Date month, String day) {
        this.desk1 = desk1;
        this.desk2 = desk2;
        this.timeline1 = timeline1;
        this.timeline2 = timeline2;
        this.office = office;
        this.month = month;
        this.day = day;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Suggestion() { }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Desk getDesk1() {
        return desk1;
    }

    public void setDesk1(Desk desk1) {
        this.desk1 = desk1;
    }

    public Desk getDesk2() {
        return desk2;
    }

    public void setDesk2(Desk desk2) {
        this.desk2 = desk2;
    }

    public long getTimeline1() {
        return timeline1;
    }

    public void setTimeline1(long timeline1) {
        this.timeline1 = timeline1;
    }

    public long getTimeline2() {
        return timeline2;
    }

    public void setTimeline2(long timeline2) {
        this.timeline2 = timeline2;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
