package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.GenericMonthReport;

public interface GenericMonthReportRepository extends JpaRepository<GenericMonthReport, Long> {
}
