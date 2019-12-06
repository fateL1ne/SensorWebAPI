package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.WeeklyReport;

public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Long> {
}
