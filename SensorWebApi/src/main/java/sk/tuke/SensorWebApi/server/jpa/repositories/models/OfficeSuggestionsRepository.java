package sk.tuke.SensorWebApi.server.jpa.repositories.models;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.office.MonthlyOfficeSuggestion;

public interface OfficeSuggestionsRepository extends JpaRepository<MonthlyOfficeSuggestion, Long> {
}
