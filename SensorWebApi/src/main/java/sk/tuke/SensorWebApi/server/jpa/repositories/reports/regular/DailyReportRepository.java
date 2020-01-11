package sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;

import java.util.Date;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    List<DailyReport> findAllByDayBetweenAndDesk(Date startWeek, Date endWeek, Desk desk);
    List<DailyReport> findAllByDayBetweenAndDesk_Team(Date startWeek, Date endWeek, Team team);
    List<DailyReport> findAllByDay(Date day);
    List<DailyReport> findAllByDayAndDesk_Team(Date day, Team team);
    List<DailyReport> findAllByDayBetween(Date startWeek, Date endWeek);
    List<DailyReport> findAllByDayAndDeskTeamId(Date day, long teamId);
}
