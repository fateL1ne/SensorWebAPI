package sk.tuke.SensorWebApi.server.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Date;

/**
 * @Immutable
 *  mark table as read-only
 */

@Entity
@Immutable
@Table (name = "RegularReport")
public class Report
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="MY_ENTITY_SEQ")
    private Long id;

    @OneToOne
    @JoinColumn(name = "deskId")
    private Desk desk;

    @Column(name = "timestamp", nullable = true)
    private Date timestamp;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", desk=" + desk.toString() +
                ", timestamp=" + timestamp.toString() +
                ", occupied=" + occupied +
                '}';
    }

    public Report(Desk desk, Date timestamp, boolean occupied) {
        this.desk = desk;
        this.timestamp = timestamp;
        this.occupied = occupied;
    }

    public Report() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Desk getDesk() { return desk; }

    public void setDesk(Desk desk) { this.desk = desk; }

    public Date getTimestamp() { return timestamp; }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
