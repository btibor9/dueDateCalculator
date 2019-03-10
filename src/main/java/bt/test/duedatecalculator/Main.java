package bt.test.duedatecalculator;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException {
        Date dateInput = new Date(1552291200000L);
        int workhours = 16;
        Date date = DateCalculatorImpl.getInstance().dueDate(dateInput, workhours);
        System.out.println("New bug added at: " + dateInput + " with turnaround time: " + workhours + "h the due date is: " + date);
    }

}
