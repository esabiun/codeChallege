package lt.rci.test.service;

import lt.rci.test.dao.domain.Transaction;
import lt.rci.test.rest.model.StatisticsDto;

import java.math.BigDecimal;
import java.util.List;

public class StatisticsCalculator {
    public static StatisticsDto calculateStatistics(List<Transaction> transactions, long oldestTime) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal avg = BigDecimal.ZERO;
        double max = 0;
        double min = 0;
        long count = 0;


        for (Transaction t : transactions) {
            if (t.getTimestamp() < oldestTime) {
                continue;
            }
            sum = sum.add(BigDecimal.valueOf(t.getAmount()));
            max = Math.max(max, t.getAmount());
            min = count > 0 ? Math.min(min, t.getAmount()): t.getAmount();
            count++;
        }
        if (count > 0) {
            avg = sum.divide(BigDecimal.valueOf(count), BigDecimal.ROUND_DOWN);
        }

        return new StatisticsDto(sum.doubleValue(), avg.doubleValue(), max, min, count);
    }

}
