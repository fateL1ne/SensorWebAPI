package sk.tuke.SensorWebApi.server.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByTeamNameStartingWith(String team);
    Team findOneByTeamName(String name);
}
