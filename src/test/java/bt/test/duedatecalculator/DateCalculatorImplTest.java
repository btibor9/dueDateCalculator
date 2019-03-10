package bt.test.duedatecalculator;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateCalculatorImplTest extends DateCalculatorTest {
    @Override
    protected DateCalculator getInstance() {
        return DateCalculator.getInstance();
    }

    @Test
    public void testGetDayOfWeek(){
        Date date = new Date(1552291200000L);
        System.out.println(date);
        int day = DateCalculatorImpl.remainingDayFromWeek(date);
        assertEquals(5, day);
        date = new Date(1552464000000L);
        System.out.println(date);
        day = DateCalculatorImpl.remainingDayFromWeek(date);
        assertEquals(3,day);
    }

}
