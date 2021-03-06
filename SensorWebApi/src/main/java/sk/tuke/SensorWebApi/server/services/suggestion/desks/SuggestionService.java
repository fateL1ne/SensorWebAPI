package sk.tuke.SensorWebApi.server.services.suggestion.desks;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.http.response.OfficeSuggestionsResponse;
import sk.tuke.SensorWebApi.server.http.response.SuggestionResponse;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Office;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Suggestion;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.office.MonthlyOfficeSuggestion;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.DeskRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.OfficeRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.OfficeSuggestionsRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.SuggestionRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;

import java.util.*;

@Service
public class SuggestionService
{
    @Autowired private DeskRepository deskRepository;
    @Autowired private DailyReportRepository dailyReportRepository;
    @Autowired private TimelineService timelineService;
    @Autowired private SuggestionRepository suggestionRepository;
    @Autowired private OfficeRepository officeRepository;
    @Autowired private OfficeSuggestionsRepository officeSuggestionsRepository;

    private final long DAY = 24 * 60 * 60 * 1000;
    private final long WEEK = DAY * 7;
    private final int TOTAL_DAYS = 7;


    public List<SuggestionResponse> getSuggestionsByMonthAndDay(Date month, String day) {
        List<SuggestionResponse> suggestionResponses = new ArrayList<>();
        List<Suggestion> suggestions = suggestionRepository.findAllByMonthAndDay(month, day);

        suggestions.forEach( suggestion -> suggestionResponses.add(new SuggestionResponse(
                suggestion.getDesk1(),
                suggestion.getTimeline1(),
                suggestion.getDesk2(),
                suggestion.getTimeline2(),
                suggestion.getDay(),
                suggestion.getMonth(),
                suggestion.getScore()
        )));

        return suggestionResponses;
    }


    public OfficeSuggestionsResponse getOfficesSuggestions(Date month)
    {

        List<OfficeSuggestionsResponse> officeSuggestionsResponses = new ArrayList<>();
        List<Office> allOffices = officeRepository.findAll();

        return null;

    }


    public void generateOfficeSuggestion(Office office, Date startOfMonth, Date endOfMonth)
    {
        MonthlyOfficeSuggestion monthlyOfficeSuggestion = new MonthlyOfficeSuggestion(office, startOfMonth);
        officeSuggestionsRepository.save(monthlyOfficeSuggestion);
        List<Desk> desksPerOffice = deskRepository.findAllByOffice(office);
        Day[] days = { Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY };

        long actualDayMillis = startOfMonth.getTime();

        for (int day = 0; day < TOTAL_DAYS; day++)
        {
            List <Suggestion> suggestions = generateSuggestions(monthlyOfficeSuggestion, office, startOfMonth, endOfMonth, new Date(actualDayMillis), days[day], desksPerOffice);
            monthlyOfficeSuggestion.addSuggestions(suggestions);
            actualDayMillis += DAY;
        }

        if (monthlyOfficeSuggestion.getSuggestions().size() == 0) {
            officeSuggestionsRepository.delete(monthlyOfficeSuggestion);
        } else {
            officeSuggestionsRepository.save(monthlyOfficeSuggestion);

        }

    }

    private List<Suggestion> generateSuggestions(MonthlyOfficeSuggestion monthlyOfficeSuggestion, Office office, Date startOfMonth, Date endOfMonth, Date dayDate, Day day, List<Desk> desks)
    {
        List<Suggestion> suggestions = new ArrayList<>();
        Graph<GraphVertex> graph = generateDailyGraph(dayDate, endOfMonth, desks);
        Edge<GraphVertex> edge;

        while( (edge = graph.popEdge()) != null )
        {
            Suggestion suggestion = new Suggestion(edge.getWeight(),
                edge.getSource().getDesk(),
                edge.getDestination().getDesk(),
                edge.getSource().getAverageTimeline(),
                edge.getDestination().getAverageTimeline(),
                office,
                startOfMonth,
                day.getName(),
                monthlyOfficeSuggestion);

            suggestions.add(suggestion);
        }
        Collections.sort(suggestions);

        for(int i =0; i < suggestions.size() / 2; i++) {
            monthlyOfficeSuggestion.addSuggestion(suggestions.get(i));
            suggestionRepository.save(suggestions.get(i));
        }

        return suggestions;
    }

    private Graph<GraphVertex> generateDailyGraph(Date day, Date endOfMonth, List<Desk> desks)
    {
        Graph<GraphVertex> graph = new Graph<>();
        Map<Desk, GraphVertex> deskGraphVertexMap = new HashMap<>();

        desks.forEach( desk -> {
            GraphVertex graphVertex = generateGraphVertex(day, endOfMonth, desk);
            deskGraphVertexMap.put(desk, graphVertex);
            graph.addVertex(graphVertex);
        });

        int desks_size = desks.size();

        for (int i = 0; i < desks_size; i++)
        {
            for(int j = i+1; j < desks_size; j++)
            {
                GraphVertex source = deskGraphVertexMap.get(desks.get(i));
                GraphVertex destination = deskGraphVertexMap.get(desks.get(j));
                int weight = timelineService.gaps(source.getAverageTimeline(), destination.getAverageTimeline());

                graph.addEdge(source, destination, weight);
            }
        }

        return graph;
    }

    private GraphVertex generateGraphVertex(Date day, Date endOfMonth, Desk desk)
    {
        List<DailyReport> dailyReportsPerDesk = new ArrayList<>();

        long currentMillis = day.getTime();
        long endMillis = endOfMonth.getTime();

        while(currentMillis < endMillis)
        {
            dailyReportsPerDesk.add(dailyReportRepository.findByDayAndDesk(new Date(currentMillis), desk));
            currentMillis += WEEK;
        }

        long averageTimeline = timelineService.getAverageDailyTimeline(dailyReportsPerDesk);

        return new GraphVertex(desk, averageTimeline);
    }

}
