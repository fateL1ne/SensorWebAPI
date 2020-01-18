package sk.tuke.SensorWebApi.server.jpa.entities.core;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Office")
public class Office
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="office_name", length = 30, nullable = false)
    private String officeName;

    @Column(name = "floor", nullable = false)
    private int floor;

    @OneToMany(mappedBy = "office")
    private List<Desk> desks = new ArrayList<>();

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", officeName='" + officeName + '\'' +
                ", floor=" + floor +
                ", desks=" + desks.size() +
                '}';
    }

    public Office() { };

    public Office(String officeName, int floor) {
        this.officeName = officeName;
        this.floor = floor;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getOfficeName() { return officeName; }

    public void setOfficeName(String officeName) { this.officeName = officeName; }

    public int getFloor() { return floor; }

    public void setFloor(int floor) { this.floor = floor; }

    public List<Desk> getDesks() { return desks; }

    public void setDesks(List<Desk> desks) { this.desks = desks; }
}
