package sk.tuke.SensorWebApi.server.jpa.entities.core;


import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.MonthlyTeamAttendance;

import javax.persistence.*;

@Entity
@Table(name = " WeightTimeline")
public class WeightTimeline
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "monthlyTeamAttendance")
    private MonthlyTeamAttendance monthlyTeamAttendance;

    @Column (name = "day", nullable = false)
    private String day;

    @Column(name = "weights")
    private String weights;

    public WeightTimeline(MonthlyTeamAttendance monthlyTeamAttendance, String day, String weights) {
        this.monthlyTeamAttendance = monthlyTeamAttendance;
        this.day = day;
        this.weights = weights;
    }

    public WeightTimeline() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonthlyTeamAttendance getMonthlyTeamAttendance() {
        return monthlyTeamAttendance;
    }

    public void setMonthlyTeamAttendance(MonthlyTeamAttendance monthlyTeamAttendance) {
        this.monthlyTeamAttendance = monthlyTeamAttendance;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }
}
