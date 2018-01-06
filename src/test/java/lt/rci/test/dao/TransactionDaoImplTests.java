package lt.rci.test.dao;

import lt.rci.test.dao.domain.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("develop")
public class TransactionDaoImplTests {

    @Autowired
    private TransactionDaoImpl dao;
    @Value("${rci.time.frame}")
    private int timeFrame;

    @Before
    public void setUp() {
        dao.reset();
    }

    @After
    public void tearDown() {
        dao.reset();
    }

    @Test
    public void testSaveAndGet() {
        long now = Instant.now().toEpochMilli();

        assertEquals(0, dao.getTransactions(now).size());

        dao.saveTransaction(new Transaction(2.5, now), now);
        List<Transaction> res = dao.getTransactions(now);

        assertEquals(1, res.size());
        assertEquals(2.5, res.get(0).getAmount(), 0);
    }

    @Test
    public void testSaveOlder() {
        long now = Instant.now().toEpochMilli();

        assertEquals(0, dao.getTransactions(now).size());

        dao.saveTransaction(new Transaction(2.5, now), now);
        List<Transaction> res = dao.getTransactions(now);

        assertEquals(1, res.size());

        // that should invalidate previos one
        now = now + timeFrame + 1;
        dao.saveTransaction(new Transaction(5, now), now);
        res = dao.getTransactions(now);

        assertEquals(1, res.size());
        assertEquals(5, res.get(0).getAmount(), 0);
    }

}