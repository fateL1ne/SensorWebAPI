package sk.tuke.SensorWebApi.server.services.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.http.response.RecentActivityResponse;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.IntervalReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.MonthlyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.Report;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.DeskRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.ReportRepository;
import sk.tuke.SensorWebApi.server.http.request.ReportRequest;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.MonthlyReportRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ReportService
{
    @Autowired private ReportRepository reportRepository;
    @Autowired private DeskRepository deskRepository;
    @Autowired private MonthlyReportRepository monthlyReportRepository;

    ReportService () { }

    public void addReport(ReportRequest reportRequest) {
        Report newReport = new Report(deskRepository.getOne(reportRequest.getDesk()), new Date(), reportRequest.isOccupied());
        reportRepository.save(newReport);
    }


    public IntervalReport getIntervalReport(Date start, Date end) {
        List<MonthlyReport> monthlyReports = new ArrayList<>();

        long millis = start.getTime();
        return null;
    }

    public RecentActivityResponse getActivity() {
        return null;
    }



}
