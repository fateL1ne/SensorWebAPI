package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Office;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {
    List<Office> findAllByOfficeNameStartingWith(String office);
    Office findOneByOfficeName(String name);
    List<Office> findAllById(Long id);
}
