package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.DailyReport;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
}
