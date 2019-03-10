package bt.test.duedatecalculator;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public abstract class DateCalculatorTest {

    private final Date WRONG_REPORT_TIME = new Date(1572764400000L); // 2019 - 03 - 11 8:00:00
    private final Date CORRECT_REPORT_TIME = new Date(1552291200000L); // 2019 - 03 - 11 9:00:00
    private final Date CORRECT_REPORT_TIME_MINUTES = new Date(1552310460000L); // 2019 - 03 - 11 14:21:00

    private final int WORK_HOURS_VERY_SMALL = 4;
    private final int WORK_HOURS_SMALL = 8;
    private final int WORK_HOURS_MEDIUM = 32;
    private final int WORK_HOURS_LONG = 64;

    protected abstract DateCalculator getInstance();

    @Test(expected = MissingReportDateException.class)
    public void testWithEmptyDate() throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException {
        getInstance().dueDate(null, 0);
    }

    @Test(expected = ZeroWorktimeException.class)
    public void testWithZeroWorktime() throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException {
        getInstance().dueDate(CORRECT_REPORT_TIME, 0);
    }

    @Test(expected = WrongReportTimeException.class)
    public void testWithWrongReportTime() throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException {
        getInstance().dueDate(WRONG_REPORT_TIME, 12);
    }

    @Test
    public void testWithCorrectReportAndShortWorktime() throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException {
        Date smallWorktime = getInstance().dueDate(CORRECT_REPORT_TIME, WORK_HOURS_SMALL);
        Date correctAnswer = new Date(1552320000000L);
        assertEquals(correctAnswer, smallWorktime);
    }

    @Test
    public void testWithCorrectReportAndMediumWorktime() throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException {
        Date smallWorktime = getInstance().dueDate(CORRECT_REPORT_TIME, WORK_HOURS_MEDIUM);
        Date correctAnswer = new Date(1552579200000L);
        assertEquals(correctAnswer, smallWorktime);
    }

    @Test
    public void testWithCorrectReportAndLongWorktime() throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException {
        Date smallWorktime = getInstance().dueDate(CORRECT_REPORT_TIME, WORK_HOURS_LONG);
        Date correctAnswer = new Date(1553097600000L);
        assertEquals(correctAnswer, smallWorktime);
    }

    @Test
    public void testWithCorrectReport2AndLongWorktime() throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException {
        Date smallWorktime = getInstance().dueDate(CORRECT_REPORT_TIME_MINUTES, WORK_HOURS_LONG);
        Date correctAnswer = new Date(1553174460000L);
        assertEquals(correctAnswer, smallWorktime);
    }

    @Test
    public void testWithCorrectReport2AndVerySmallWorktime() throws MissingReportDateException, WrongReportTimeException, ZeroWorktimeException {
        Date smallWorktime = getInstance().dueDate(CORRECT_REPORT_TIME, WORK_HOURS_VERY_SMALL);
        Date correctAnswer = new Date(1552305600000L);
        assertEquals(correctAnswer, smallWorktime);
    }

}