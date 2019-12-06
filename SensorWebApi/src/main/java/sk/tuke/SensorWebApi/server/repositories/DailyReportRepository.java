package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.DailyReport;
import sk.tuke.SensorWebApi.server.entities.Desk;

import java.util.Date;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    List<DailyReport> findAllByDayBetweenAndDesk(Date startWeek, Date endWeek, Desk desk);

}
