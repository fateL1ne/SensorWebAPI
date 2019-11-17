package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
