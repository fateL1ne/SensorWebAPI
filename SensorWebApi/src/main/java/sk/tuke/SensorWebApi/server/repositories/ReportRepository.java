package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.RegularReport;

public interface ReportRepository extends JpaRepository<RegularReport, Long> {
}
