package sk.tuke.SensorWebApi.server.jpa.entities.reports.team;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "weekly_team_report")
public class WeeklyTeamReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week")
    private Date week;

    @Column(name = "averageOccupation")
    private float averageOccupation;



}
