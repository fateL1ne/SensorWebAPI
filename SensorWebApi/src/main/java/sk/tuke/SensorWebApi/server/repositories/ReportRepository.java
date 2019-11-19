package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
