package sk.tuke.SensorWebApi.server.jpa.entities.reports.regular;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "generic_month_report")
public class GenericMonthReport
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_desks")
    private int desksSum;

    @Column(name = "number_of_teams")
    private int teamsSum;

    @Column(name = "number_of_offices")
    private int officesSum;

    @Column(name = "average_occupation")
    private Float averageOccupation;

    @Column(name = "month")
    private Date month;

    public GenericMonthReport() { }

    public GenericMonthReport(int desksSum, int teamsSum, int officesSum, Float averageOccupation) {
        this.desksSum = desksSum;
        this.teamsSum = teamsSum;
        this.officesSum = officesSum;
        this.averageOccupation = averageOccupation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDesksSum() {
        return desksSum;
    }

    public void setDesksSum(int desksSum) {
        this.desksSum = desksSum;
    }

    public int getTeamsSum() {
        return teamsSum;
    }

    public void setTeamsSum(int teamsSum) {
        this.teamsSum = teamsSum;
    }

    public int getOfficesSum() {
        return officesSum;
    }

    public void setOfficesSum(int officesSum) {
        this.officesSum = officesSum;
    }

    public Float getAverageOccupation() {
        return averageOccupation;
    }

    public void setAverageOccupation(Float averageOccupation) {
        this.averageOccupation = averageOccupation;
    }
}
