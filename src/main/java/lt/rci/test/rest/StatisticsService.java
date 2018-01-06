package lt.rci.test.rest;

import lombok.extern.log4j.Log4j2;
import lt.rci.test.dao.TransactionDao;
import lt.rci.test.dao.domain.Transaction;
import lt.rci.test.rest.model.StatisticsDto;
import lt.rci.test.service.StatisticsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RestController
@RequestMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class StatisticsService {

    @Value("${rci.time.frame}")
    private int timeFrame;

    @Autowired
    private TransactionDao transactionDao;

    @RequestMapping(value = "transactions", method = RequestMethod.POST)
    public ResponseEntity<?> registerTransaction(@RequestBody Transaction transaction) {
        long now = Instant.now().toEpochMilli();

        HttpStatus status;
        if (transaction.getTimestamp() < (now - timeFrame)) {
            status = HttpStatus.NO_CONTENT;
        } else {
            transactionDao.saveTransaction(transaction, now);
            status = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(null, status);
    }

    @RequestMapping(value = "statistics", method = RequestMethod.GET, consumes = ALL_VALUE)
    @ResponseBody
    public StatisticsDto retrieveStatistics() {
        long now = Instant.now().toEpochMilli();
        long oldestTime = now - timeFrame;
        List<Transaction> transactions = transactionDao.getTransactions(now);

        StatisticsDto statistics = StatisticsCalculator.calculateStatistics(transactions, oldestTime);
        return statistics;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAnyException(RuntimeException ex) {
        log.error("Runtime exception", ex);

        ErrorResponse responseStatus = new ErrorResponse(ErrorCode.SYSTEM_ERROR.name(), ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(responseStatus, status);
    }
}
