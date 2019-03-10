package bt.test.duedatecalculator;

import java.util.Date;

public abstract class DateCalculator {

    public static DateCalculator getInstance(){
        return new DateCalculatorImpl();
    }


    public abstract Date dueDate(Date reportDate, int workhours) throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException;


}
