package sk.tuke.SensorWebApi.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sk.tuke.SensorWebApi.server.entities.Report;
import sk.tuke.SensorWebApi.server.repositories.DeskRepository;
import sk.tuke.SensorWebApi.server.repositories.ReportRepository;
import sk.tuke.SensorWebApi.server.request.ReportRequest;

import java.util.Date;


@RestController
@RequestMapping(value = "/reporting", consumes = "application/json", produces = "application/json")
public class ReportController
{

    private final Logger logger = LoggerFactory.getLogger(ReportController.class.getName());

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private DeskRepository deskRepository;


    @PostMapping(value = "/add")
    public HttpStatus newReport(@RequestBody ReportRequest incomingRequest)
    {
        Report newReport = new Report(deskRepository.getOne(incomingRequest.getDesk()), new Date(), incomingRequest.isOccupied());
        reportRepository.save(newReport);
        logger.info(HttpStatus.CREATED.toString() + newReport.toString());

        return HttpStatus.CREATED;
    }


}
