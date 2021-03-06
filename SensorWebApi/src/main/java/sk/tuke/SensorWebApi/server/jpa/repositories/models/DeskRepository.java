package sk.tuke.SensorWebApi.server.jpa.repositories.models;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Office;

import java.util.List;

public interface DeskRepository extends JpaRepository<Desk, Long> {
    List<Desk> findByOfficeId(Long id);
    List<Desk> findByTeamId(Long id);
    Desk findDeskByLabel(String label);
    List<Desk> findAllByOffice(Office office);

}
