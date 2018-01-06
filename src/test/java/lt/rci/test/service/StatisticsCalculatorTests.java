package lt.rci.test.service;

import lt.rci.test.dao.domain.Transaction;
import lt.rci.test.rest.model.StatisticsDto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StatisticsCalculatorTests {
    @Test
    public void testCalculateStatisticsEmpty() {
        List<Transaction> transactions = new ArrayList<>();
        long oldestTime = Instant.now().toEpochMilli();

        StatisticsDto res = StatisticsCalculator.calculateStatistics(transactions, oldestTime);

        assertEquals(0, res.getAvg(), 0);
        assertEquals(0, res.getCount(), 0);
        assertEquals(0, res.getMax(), 0);
        assertEquals(0, res.getMin(), 0);
        assertEquals(0, res.getSum(), 0);

    }

    @Test
    public void testCalculateStatistics() {
        long oldestTime = Instant.now().toEpochMilli();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(4, oldestTime));
        transactions.add(new Transaction(8, oldestTime));

        StatisticsDto res = StatisticsCalculator.calculateStatistics(transactions, oldestTime);

        assertEquals(6, res.getAvg(), 0);
        assertEquals(2, res.getCount(), 0);
        assertEquals(8, res.getMax(), 0);
        assertEquals(4, res.getMin(), 0);
        assertEquals(12, res.getSum(), 0);
    }

}
