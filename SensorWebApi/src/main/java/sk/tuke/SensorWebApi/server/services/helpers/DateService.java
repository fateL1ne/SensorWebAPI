package sk.tuke.SensorWebApi.server.services.helpers;


import com.github.rkumsher.date.DateUtils;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.services.suggestion.desks.Day;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class DateService {

    private final Day[] days = { Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY };
    private final long DAY = 24 * 60 * 60 * 1000;
    private final Calendar calendar = Calendar.getInstance(new Locale("en","UK"));

    public DateService() { };

    public Date getStartOfNextMonth() {
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);

        Date startOfNextMonth = calendar.getTime();

        return DateUtils.atStartOfDay(startOfNextMonth);
    }

    public Date getYesterday() {
        return DateUtils.atStartOfDay( new Date( System.currentTimeMillis() - DAY) );
    }

    public Date getStartOfLastWeek() {
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);

        Date startOfLastWeek = calendar.getTime();
        calendar.setTime(new Date(System.currentTimeMillis()));

        return DateUtils.atStartOfDay(startOfLastWeek);
    }


    public Date getFirstDayDate(Date month, int day) {
        calendar.setTime(month);
        calendar.set(Calendar.WEEK_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK, day);

        return calendar.getTime();
    }

    public Day getDay(Date date) {
        calendar.setTime(date);

        return days[calendar.get(Calendar.DAY_OF_WEEK)];
    }

    public Date getEndOfLastWeek() {
        calendar.set(Calendar.DAY_OF_WEEK, 2);

        Date endOfLastWeek = calendar.getTime();
        calendar.setTime(new Date(System.currentTimeMillis()));

        return DateUtils.atStartOfDay(endOfLastWeek);
    }

    public Date getStartOfLastMonth() {
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Date startOfMonth = calendar.getTime();
        calendar.setTime(new Date( System.currentTimeMillis()));

        return DateUtils.atStartOfDay(startOfMonth);
    }

    public Date getEndOfLastMonth() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Date startOfMonth = calendar.getTime();
        calendar.setTime(new Date( System.currentTimeMillis()));

        return DateUtils.atStartOfDay(startOfMonth);
    }

    /**
     *
     *  ---> FOR TESTING/MOCKING PURPOSES
     *
     * @param year ##
     * @param month ##
     * @param day ##
     * @return specific day in date format
     *
     */

    public Date getSpecificDay(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        Date dayDate = calendar.getTime();

        calendar.setTime(new Date(System.currentTimeMillis()));

        return DateUtils.atStartOfDay(dayDate);
    }
}
