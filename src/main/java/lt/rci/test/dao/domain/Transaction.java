package lt.rci.test.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Comparable<Transaction> {
    private double amount;
    private long timestamp;

    @Override
    public int compareTo(Transaction t) {
        return Long.compare(timestamp, t.getTimestamp());
    }
}
