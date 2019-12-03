package sk.tuke.SensorWebApi.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.Report;
import sk.tuke.SensorWebApi.server.repositories.DeskRepository;
import sk.tuke.SensorWebApi.server.repositories.ReportRepository;
import sk.tuke.SensorWebApi.server.request.ReportRequest;

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
