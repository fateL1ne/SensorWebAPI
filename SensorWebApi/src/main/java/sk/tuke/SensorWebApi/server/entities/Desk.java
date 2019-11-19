package sk.tuke.SensorWebApi.server.entities;


import javax.persistence.*;

@Entity
@Table  (name = "Desk")
public class Desk
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "officeId")
    private Office office;

    public Desk() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @Override
    public String toString() {
        return "Desk{" +
                "id=" + id +
                ", team=" + team.toString() +
                ", office=" + office.toString() +
                '}';
    }
}
