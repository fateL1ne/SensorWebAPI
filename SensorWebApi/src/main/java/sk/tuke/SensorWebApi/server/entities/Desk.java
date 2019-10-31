package sk.tuke.SensorWebApi.server.entities;

/**
 * Created by IntelliJ IDEA.
 *
 * @project SensorWebApi
 * @author jabu
 * @date 10/31/19
 */

import javax.persistence.*;

@Entity
@Table  (name = "DESK")
public class Desk
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "office_name",
            length = 30,
            nullable = false
    )   private String officeName;

    @Column(
            name ="occupied",
            nullable = false
    )   private boolean occupied;

    @Override
    public String toString() { return ("Desk [id=" + id + ", office_name=" + officeName + ", occupied=" + occupied + "] "); }

    public Desk() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOfficeName() { return officeName; }
    public void setOfficeName(String officeName) { this.officeName = officeName; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
}
