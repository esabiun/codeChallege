package lt.rci.test.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto {
    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;
}
