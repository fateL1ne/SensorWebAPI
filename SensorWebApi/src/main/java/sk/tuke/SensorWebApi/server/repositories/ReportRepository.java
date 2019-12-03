package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Desk;
import sk.tuke.SensorWebApi.server.entities.Report;

import java.util.Date;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByTimestampBetweenAndDesk(Date begin, Date end, Desk desk);
}
