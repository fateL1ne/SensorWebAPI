package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Desk;

import java.util.List;

public interface DeskRepository extends JpaRepository<Desk, Long> {
    List<Desk> findByOfficeId(Long id);
    List<Desk> findByTeamId(Long id);
    Desk findDeskByLabel(String label);
}
