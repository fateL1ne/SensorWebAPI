package sk.tuke.SensorWebApi.server.jpa.entities.reports.office;


import sk.tuke.SensorWebApi.server.jpa.entities.core.Office;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Suggestion;

import javax.persistence.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "monthly_office_suggestion")
public class MonthlyOfficeSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    @OneToMany(mappedBy = "monthlyOfficeSuggestion", cascade = CascadeType.ALL)
    private List<Suggestion> suggestions = new ArrayList<>();

    @Column(name = "month")
    private Date month;


    public MonthlyOfficeSuggestion() {
    }

    public MonthlyOfficeSuggestion(Office office, Date month) {
        this.office = office;
        this.month = month;
    }


    public MonthlyOfficeSuggestion(Office office, List<Suggestion> suggestions) {
        this.office = office;
        this.suggestions = suggestions;
    }

    public void addSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

    public void addSuggestions(List<Suggestion> suggestions) {
        suggestions.addAll(suggestions);
    }
    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }


}
