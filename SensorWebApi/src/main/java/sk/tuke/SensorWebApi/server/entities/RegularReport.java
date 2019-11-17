package sk.tuke.SensorWebApi.server.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Date;

/**
 *  mark table as read-only with @Immutable
 */

@Entity
@Immutable
@Table (name = "RegularReport")
public class RegularReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "deskId")
    private Desk desk;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    public RegularReport() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Desk getDesk() { return desk; }

    public void setDesk(Desk desk) { this.desk = desk; }

    public Date getTimestamp() { return timestamp; }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
