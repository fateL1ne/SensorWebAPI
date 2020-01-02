package sk.tuke.SensorWebApi.server.services.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Report;
import sk.tuke.SensorWebApi.server.jpa.repositories.DeskRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.ReportRepository;
import sk.tuke.SensorWebApi.server.http.request.ReportRequest;

import java.util.Date;


@Service
public class ReportService
{
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private DeskRepository deskRepository;

    ReportService () { }

    public void addReport(ReportRequest reportRequest) {
        Report newReport = new Report(deskRepository.getOne(reportRequest.getDesk()), new Date(), reportRequest.isOccupied());
        reportRepository.save(newReport);
    }
}
