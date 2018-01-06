package lt.rci.test.dao;

import lt.rci.test.dao.domain.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionDaoImpl implements TransactionDao {

    private static final List<Transaction> TX_STORAGE = Collections.synchronizedList(new LinkedList<>());

    @Value("${rci.time.frame}")
    private int timeFrame;

    @Override
    public void saveTransaction(Transaction transaction, long currentTime) {
        getTxStorage().add(transaction);
        Collections.sort(getTxStorage());

        long oldestTime = currentTime - timeFrame;
        storageCleanup(oldestTime);


    }

    /**
     * Storage cleanup, we are interested only txs falling into defined timeFrame - purge obsolete ones.
     *
     * @param oldestTime oldest supported transaction age.
     */
    private void storageCleanup(long oldestTime) {
        Iterator<Transaction> it = getTxStorage().iterator();
        while (it.hasNext()) {
            Transaction t = it.next();
            if (t.getTimestamp() < oldestTime) {
                // remove obsolete transactions from storage
                it.remove();
            } else {
                // we are good with rest of transactions
                break;
            }
        }
    }


    @Override
    public List<Transaction> getTransactions(long currentTime) {
        long oldestTime = currentTime - timeFrame;
        storageCleanup(oldestTime);
        // once cleaned - simply return all
        return getTxStorage();
    }

    /**
     * Used to set storage into initial state.
     */
    void reset() {
        getTxStorage().clear();
    }

    private List<Transaction> getTxStorage() {
        return TX_STORAGE;
    }

}