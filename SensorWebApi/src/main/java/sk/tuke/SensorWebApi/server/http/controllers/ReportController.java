package sk.tuke.SensorWebApi.server.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tuke.SensorWebApi.server.http.request.ReportRequest;
import sk.tuke.SensorWebApi.server.services.report.ReportService;

import java.util.Date;


@RestController
@RequestMapping(value = "/reporting", consumes = "application/json", produces = "application/json")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(value = "/add")
    public ResponseEntity<HttpStatus> newReport(@RequestBody ReportRequest incomingRequest) {
        reportService.addReport(incomingRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);

    }
}
