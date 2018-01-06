package lt.rci.test.dao;

import lt.rci.test.dao.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TransactionDao {

    /**
     *
     * @param transaction transaction to persist to storage.
     * @param currentTime current time in time in epoch in millis in UTC time zone.
     */
    void saveTransaction(Transaction transaction, long currentTime);

    List<Transaction> getTransactions(long currentTime);

}