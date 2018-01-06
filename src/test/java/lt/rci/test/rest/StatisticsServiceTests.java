package lt.rci.test.rest;

import lt.rci.test.dao.domain.Transaction;
import lt.rci.test.rest.model.StatisticsDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("develop")
public class StatisticsServiceTests {

    @Autowired
    private StatisticsService rest;

    /**
     * Keep in mind for future that we might hit issues with data source consistency or time-frame from current time.
     * For that case this test potentially can become integration one.
     */
    @Test
    public void smokeTestSaveAndGet() {
        long now = Instant.now().toEpochMilli();
        rest.registerTransaction(new Transaction(4, now));
        rest.registerTransaction(new Transaction(8, now));

        StatisticsDto res = rest.retrieveStatistics();

        assertEquals(6, res.getAvg(), 0);
        assertEquals(2, res.getCount(), 0);
        assertEquals(8, res.getMax(), 0);
        assertEquals(4, res.getMin(), 0);
        assertEquals(12, res.getSum(), 0);
    }


}