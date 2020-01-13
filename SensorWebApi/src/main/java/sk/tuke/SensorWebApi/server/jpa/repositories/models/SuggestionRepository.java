package sk.tuke.SensorWebApi.server.jpa.repositories.models;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Suggestion;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
