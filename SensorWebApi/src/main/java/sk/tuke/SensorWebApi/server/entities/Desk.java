package sk.tuke.SensorWebApi.server.entities;


import javax.persistence.*;

@Entity
@Table  (name = "Desk")
public class Desk
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "office_name", length = 30, nullable = false)
    private String officeName;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "officeId")
    private Office office;

    @Override
    public String toString() { return ("Desk [id=" + id + ", office_name=" + officeName + "] "); }

    public Desk() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getOfficeName() { return officeName; }

    public void setOfficeName(String officeName) { this.officeName = officeName; }

}
