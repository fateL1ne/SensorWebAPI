package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
