package sk.tuke.SensorWebApi.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.repositories.DeskRepository;

@Service
public class TaskService
{
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private static final long DAY = 24 * 60 * 60 * 1000;

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private DailyReportService dailyReportService;

    @Scheduled(cron = "* 5 0 * * *", zone = "Europe/Bratislava")
    public void generateDailyReports() {
        logger.info(" just temporary smile :)))");
//        logger.info("Running daily reports task");
//
//        Date yesterday = new Date(System.currentTimeMillis() - DAY);
//        List<Desk> allDesks = deskRepository.findAll();
//        allDesks.forEach( desk -> dailyReportService.generateReport(desk, yesterday));
    }

}
