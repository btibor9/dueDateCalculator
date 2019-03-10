package bt.test.duedatecalculator;

import java.util.Calendar;
import java.util.Date;


public class DateCalculatorImpl extends DateCalculator {

    private static final long HOUR_IN_MILLIS = 60 * 60 * 1000;
    private static final long WORK_DAY = 8 * 60 * 60 * 1000;

    @Override
    public Date dueDate(Date reportDate, int workhours) throws ZeroWorktimeException, MissingReportDateException, WrongReportTimeException {
        System.out.println("Starting " + reportDate + " - " + workhours);
        if (reportDate == null) throw new MissingReportDateException();

        if (workhours <= 0) throw new ZeroWorktimeException();
        if (!isInAWorkday(reportDate)) {
            throw new WrongReportTimeException();
        }
        long finalMilliseconds = reportDate.getTime();
        long workHourInMs = workhours * HOUR_IN_MILLIS;
        //firstDay - calculate diff in first day work hours, and remove from the input.
        long firstDay = getRemainingDay(reportDate);
        if(workHourInMs - firstDay < 0){
            return new Date(finalMilliseconds + workHourInMs);
        }
        finalMilliseconds += firstDay;
        workHourInMs -= firstDay;
        //if no more workhours remaining after first day stop the calculation
        if (workHourInMs <= 0) {
            return new Date(finalMilliseconds);
        }
        //calculate remaining days after first day.
        long workDays = workHourInMs / WORK_DAY;
        long workTimeInLastDay = workHourInMs % WORK_DAY;
        finalMilliseconds += workDays * 24 * HOUR_IN_MILLIS;
        if (workTimeInLastDay != 0) {
            finalMilliseconds += 16*HOUR_IN_MILLIS;
            finalMilliseconds += workTimeInLastDay;
        }
        //weekends calculate the weekend count and add weekend * 48 h

        long correctWorkdays = (workDays + (workTimeInLastDay == 0 ? 0 : 1)) - remainingDayFromWeek(reportDate);
        if (correctWorkdays > 0) {
            long weekends = (correctWorkdays) / 5;
            weekends += correctWorkdays % 5 == 0 ? 0 : 1;
            finalMilliseconds += weekends * 48 * HOUR_IN_MILLIS;
        }
        return new Date(finalMilliseconds);
    }


    private static long getRemainingDay(Date day) {
        Calendar dayEnd = getDayEnd(day);
        return dayEnd.getTimeInMillis() - day.getTime();
    }

    static int remainingDayFromWeek(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        return 5 - (calendar.get(Calendar.DAY_OF_WEEK) - 2);
    }
    private static boolean isInAWorkday(Date date){
        return getDayStart(date).getTimeInMillis() <= date.getTime() && getDayEnd(date).getTimeInMillis() >= date.getTime();
    }

    private static Calendar getDayStart(Date day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY , 9);
        calendar.set(Calendar.MINUTE , 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    private static Calendar getDayEnd(Date day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY , 17);
        calendar.set(Calendar.MINUTE , 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

}
